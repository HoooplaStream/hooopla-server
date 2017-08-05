package fr.cseries.ci.series.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Episode {

	@Getter @Setter
	private Integer number;
	@Getter @Setter
	private String name;
	@Getter @Setter
	private String description;
	@Getter @Setter
	private String url;
	@Getter @Setter
	private Float rating;
	@Getter @Setter
	private Boolean ready;

}
