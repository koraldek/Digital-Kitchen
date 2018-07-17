package pl.krasnowski.DigitalKitchen.model.domains;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@Data
@ToString
@Entity
public class KitchensTool {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private long kitchensToolID;

	@Column
	private String name;

	/**
	 * Power of tool in Watts. If tool dont use electricity power=0.
	 */
	@Column
	private int power = 0;

	/**
	 * Describes estimated conservation time in minutes of use. For example estimate conservation time for knife is 120 minutes.
	 */
	@Column
	private int conservationTime;

	/**
	 * Brand of tool. Can faster complainment process.
	 */
	@Column
	private String brand;

	/**
	 * Describes how many minutes tool was already used.
	 */
	@Column
	private int minUsed;

	/**
	 * Sets to disable when tool is indisposed - crashed or conservationTime is reached.
	 */
	@Column
	private boolean isProficient = true;

}