package homework;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class AdjusterClientTest {
	
	private static MockRestServiceServer mockServer;
	private static RestTemplate restTemplate;
	
	@Before
    public void setUp() {
		restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }
	
	@Test public void testCampaignParsing() throws IOException, URISyntaxException {
		
		String fakeJson = readFileContents("/campaigns.json");
		mockServer.expect(requestTo("http://homework.ad-juster.com/api/campaigns"))
         .andExpect(method(HttpMethod.GET))
         .andRespond(withSuccess(fakeJson, MediaType.APPLICATION_JSON_UTF8));
		
        AdjusterClient client = new AdjusterClient(restTemplate);
        List<Campaign> campaigns = client.retrieveCampaigns();
        
        mockServer.verify();
        
        assertEquals("Should return 2 records", 2, campaigns.size());
        
        Campaign expectedCampaign = new Campaign();
        expectedCampaign.id = 13125;
        expectedCampaign.name = "zemindary, eichwaldite";
        expectedCampaign.cpm = new BigDecimal("33.00");
        expectedCampaign.startDate = LocalDate.of(2018, Month.JANUARY, 4);
        assertEquals("First campaign should be correctly parsed", expectedCampaign, campaigns.get(0));
    }
	
	@Test public void testCreativeParsing() throws IOException, URISyntaxException {
		
		String fakeJson = readFileContents("/creatives.json");
		mockServer.expect(requestTo("http://homework.ad-juster.com/api/creatives"))
         .andExpect(method(HttpMethod.GET))
         .andRespond(withSuccess(fakeJson, MediaType.APPLICATION_JSON_UTF8));
		
        AdjusterClient client = new AdjusterClient(restTemplate);
        List<Creative> creatives = client.retrieveCreatives();
        
        mockServer.verify();
        
        assertEquals("Should return 1 records", 1, creatives.size());
        
        Creative expectedCreative = new Creative();
        expectedCreative.id = 14789;
        expectedCreative.parentId = 13125;
        expectedCreative.clicks = 2113L;
        expectedCreative.views = 9126L;
        assertEquals("First and only creative should be correctly parsed", expectedCreative, creatives.get(0));
    }
	
	
	private String readFileContents(String classpath) throws IOException, URISyntaxException {
		return new String (Files.readAllBytes(Paths.get(this.getClass().getResource(classpath).toURI())),Charset.forName("UTF-8"));
	}

}
