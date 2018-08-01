package pl.krasnowski.DigitalKitchen.model.domains;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@ToString
@EqualsAndHashCode
public class Diet {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private long diet_id;

	@Column
	private String dietName;

	@Column
	private int dailyKCal,dailyCarbohydrates,dailyProteins,dailyFat;


	@JoinTable(
			name = "diet_diet_days",
			joinColumns = {@JoinColumn(name = "diet_id")},
			inverseJoinColumns = {@JoinColumn(name = "dietDaysID")}
	)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<DietDay> dietDays;


}