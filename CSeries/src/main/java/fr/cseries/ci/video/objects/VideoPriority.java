package fr.cseries.ci.video.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public enum  VideoPriority {

	LOW(1, "Faible"), NORMAL(2, "Normal"), HIGH(3, "Importante"), VERY_HIGH(4, "Très importante");

	@Getter @Setter
	private Integer priority;
	@Getter @Setter
	private String name;

}
