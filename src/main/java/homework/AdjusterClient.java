package homework;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AdjusterClient {
	
	@Autowired
	public RestTemplate restTemplate;
	
	public List<Campaign> retrieveCampaigns(){
		ResponseEntity<List<Campaign>> campaignsResponse =
		        restTemplate.exchange("http://homework.ad-juster.com/api/campaigns",
		                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Campaign>>() {
		            });
		return campaignsResponse.getBody();

	}
	
	public List<Creative> retrieveCreatives(){
		return null;
	}

}
