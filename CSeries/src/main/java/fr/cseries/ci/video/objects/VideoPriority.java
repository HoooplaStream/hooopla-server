package fr.cseries.ci.video.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public enum  VideoPriority {

	LOW(1), NORMAL(2), HIGH(3), VERY_HIGH(4);

	@Getter @Setter
	private Integer priority;
}
