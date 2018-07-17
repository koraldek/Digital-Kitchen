package pl.krasnowski.DigitalKitchen.model.domains;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@ToString
public class Diet {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private long diet_id=0;

	@Column
	private String dietName;

	@Column
	private int dailyCalories = 0;

	@Column
	private int dailyProteins;

	@Column
	private int dailyFat;


	@JoinTable(
			name = "diet_diet_days",
			joinColumns = {@JoinColumn(name = "diet_id")},
			inverseJoinColumns = {@JoinColumn(name = "dietDaysID")}
	)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<DietDay> dietDays;


}