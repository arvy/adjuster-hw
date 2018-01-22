package homework;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
@SpringBootApplication
public class App {
	
	private static final Logger log = LoggerFactory.getLogger(App.class);
	
	@Autowired
	AdjusterClient apiClient;
	
	@Autowired
	private CampaignRepository campaignRepository;
	
	@Autowired
	private CreativeRepository creativeRepository;
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
    public static void main(String[] args) {
    		SpringApplication.run(App.class);
    }
    
    @Bean
    public CommandLineRunner run(AdjusterClient apiClient) throws Exception {
		return args -> {
			List<Campaign> campaigns = apiClient.retrieveCampaigns();
			campaigns.stream().forEach(c ->
			{
					System.out.println(c.toString());
					campaignRepository.save(c);
			}
			);
			List<Creative> creatives = apiClient.retrieveCreatives();
			creatives.stream().forEach(cr ->
			{
				System.out.println(cr.toString());
				creativeRepository.save(cr);
			}
					);
		};
	}
}
