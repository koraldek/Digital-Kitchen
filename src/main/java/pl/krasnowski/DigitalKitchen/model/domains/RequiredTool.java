package pl.krasnowski.DigitalKitchen.model.domains;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString
public class RequiredTool {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private long requiredToolID;

	@ManyToOne(cascade = CascadeType.ALL)
	private KitchensTool kitchenTool;

	/**
	 * Estimated required time in minutes of using tool to prepare dish.
	 */
	@Column
	private int requiredTime;

}