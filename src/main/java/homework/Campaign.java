package homework;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;

@Data
@JsonDeserialize(using = CampaignCustomDeserializer.class)
@Entity
public class Campaign {
	@Id
	Integer id;
	String name;
	LocalDate startDate;
	BigDecimal cpm;
}
