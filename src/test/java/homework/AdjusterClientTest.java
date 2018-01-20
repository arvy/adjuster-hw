package homework;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class AdjusterClientTest {
	
	@Test public void testCampaigns() {
        AdjusterClient client = new AdjusterClient();
        client.restTemplate = new RestTemplate();
        List<Campaign> campaigns = client.retrieveCampaigns();
        assertEquals("Should return 2 records", 2, campaigns.size());
        
        Campaign expectedCampaign = new Campaign();
        expectedCampaign.id = 13125;
        expectedCampaign.name = "zemindary, eichwaldite";
        expectedCampaign.cpm = new BigDecimal("33.00");
        expectedCampaign.startDate = LocalDate.of(2018, Month.JANUARY, 4);
        assertEquals("First campaign should be correctly parsed", expectedCampaign, campaigns.get(0));
        
        
        
    }

}
