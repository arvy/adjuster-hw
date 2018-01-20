package homework;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;

@Data
@JsonDeserialize(using = CampaignCustomDeserializer.class)
public class Campaign {
	Integer id;
	String name;
	LocalDate startDate;
	BigDecimal cpm;
}
