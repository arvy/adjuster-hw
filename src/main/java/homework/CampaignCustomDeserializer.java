package homework;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;

public class CampaignCustomDeserializer extends StdDeserializer<Campaign> {

	public CampaignCustomDeserializer(Class<?> vc) {
		super(vc);
	}
	
	public CampaignCustomDeserializer() { 
        this(null); 
    } 
 
	

	@Override
	public Campaign deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		Campaign c = new Campaign();
		
		JsonNode node = jp.getCodec().readTree(jp);
		c.id = (Integer) ((IntNode) node.get("id")).numberValue();
        c.name = node.get("name").asText();
        String cpmWithDollarSign = node.get("cpm").asText();
        c.cpm = new BigDecimal(cpmWithDollarSign.replace("$", ""));
        c.startDate = LocalDate.parse(node.get("startDate").asText());
        return c;
	}

}
