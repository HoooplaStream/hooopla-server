package fr.cseries.ci.video.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.File;

@AllArgsConstructor
public class Video {

	@Getter
	@Setter
	@NonNull
	private File          videoFile;
	@Setter
	@Getter
	@NonNull
	private VideoPriority priority;
	@Setter
	@Getter
	@NonNull
	private double percent = 0;

}
