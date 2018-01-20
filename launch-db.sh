#!/bin/sh
docker pull mysql:8
docker run -d --name mysql \
  -v $PWD/schema.sql:/root/schema.sql \
  --env MYSQL_ALLOW_EMPTY_PASSWORD=true \
  mysql:8

echo "Waiting for DB to start.."
sleep 5

docker exec -t -i mysql /bin/sh -c 'mysql < /root/schema.sql'
