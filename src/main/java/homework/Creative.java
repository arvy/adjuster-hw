package homework;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Creative {
	@Id
	Integer id;
	Integer parentId;
	Long clicks;
	Long views;
}
