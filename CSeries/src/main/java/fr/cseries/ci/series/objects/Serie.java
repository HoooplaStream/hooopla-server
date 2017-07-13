package fr.cseries.ci.series.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
public class Serie {

	@Getter @Setter private Integer      id;
	@Getter @Setter private String       name;
	@Getter @Setter private String       description;
	@Getter @Setter private String       background;
	@Getter @Setter private String       cover;
	@Getter @Setter private Float        star;
	@Getter @Setter private List<Season> seasons;

}
