package fr.cseries.ci.video;

import fr.cseries.ci.Config;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFmpegUtils;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.ProgressListener;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public abstract class VideoConverter {

	@NonNull @Getter @Setter
	private File file;
	@Getter @Setter
	private Double progressConverting = 0.0;

	/**
	 * Permet de convertir un fichier
	 *
	 * @param file Le fichier vidéo
	 */
	public VideoConverter(File file) {
		this.file = file;
	}

	public void start() {
		try {
			FFmpeg ffmpeg = new FFmpeg(Config.FFMPEG_PATH);
			FFprobe ffprobe = new FFprobe(Config.FFMPEG_FFPROBE);
			FFmpegProbeResult in = ffprobe.probe(file.getAbsolutePath());

			int idx = file.getName().lastIndexOf('.');

			FFmpegBuilder builder = new FFmpegBuilder().setVerbosity(FFmpegBuilder.Verbosity.ERROR).setInput(file.getAbsolutePath()).overrideOutputFiles(true).addOutput(file.getParentFile().getAbsolutePath() + File.separator + file.getName().substring(0, idx) + ".mp4").setFormat("mp4").done();

			FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

			System.out.println("[-->] Conversion du fichier " + file.getName() + ", démarrage...");

			FFmpegJob job = executor.createJob(builder, new ProgressListener() {
				final double duration_s = in.getFormat().duration;

				@Override
				public void progress(Progress progress) {
					double percentage = (progress.out_time_ms * 0.001) / duration_s;
					onProgress(percentage * 100);
				}
			});
			job.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Abstract method

	public abstract void onProgress(double percent);

	public abstract void onFinish();

}
