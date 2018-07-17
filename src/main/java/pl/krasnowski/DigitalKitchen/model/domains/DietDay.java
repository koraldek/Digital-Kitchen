package pl.krasnowski.DigitalKitchen.model.domains;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString
public class DietDay {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long dayDietID;
	@Column
	private int proteins;
	@Column
	private int fat;
	@Column
	private int carbohydrates;
	@Column
	private String dayName;

}