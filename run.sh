#!/bin/sh
set -xe
docker pull mysql:8
docker run -d --name mysql \
  -v $PWD:/workdir \
  -v $PWD/mysql-custom-configs:/etc/mysql/conf.d \
  --env MYSQL_ALLOW_EMPTY_PASSWORD=true \
    -p 3306:3306 \
  mysql:8

echo "Waiting for DB to start.."
sleep 20

echo "Applying initial schema.."
docker exec -t -i mysql /bin/sh -c 'mysql < /workdir/schema.sql'

echo "Importing data..."
./gradlew bootRun

echo "Running query..."
docker exec -t -i mysql mysql -D adjuster -e 'SELECT c.name, SUM(cr.clicks) AS total_clicks, SUM(cr.views) AS total_views, count(*) AS num_creatives FROM campaign c JOIN creative cr on c.id = cr.parent_id GROUP BY c.name;'

echo "Exporting to CSV.."
docker exec -t -i mysql mysql -D adjuster \
-e "SELECT 'campaign', 'total_clicks', 'total_views', 'revenue'
UNION ALL
SELECT c.name, SUM(cr.clicks) AS total_clicks, SUM(cr.views) AS total_views, SUM(cr.views*c.cpm/1000) AS revenue
FROM campaign c JOIN creative cr on c.id = cr.parent_id
GROUP BY c.name
INTO OUTFILE '/workdir/clicks-and-views-by-campaign.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '\"'
LINES TERMINATED BY '\n';"

echo "Done. Results exported to clicks-and-views-by-campaign.csv"

echo "Destroying mysql container"
docker rm -f mysql
