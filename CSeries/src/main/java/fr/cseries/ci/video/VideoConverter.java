package fr.cseries.ci.video;

import fr.cseries.ci.Config;
import fr.cseries.ci.mongodb.collections.MongoConversions;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFmpegUtils;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.builder.FFmpegOutputBuilder;
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

			FFmpegBuilder builder = new FFmpegBuilder().setInput(file.getAbsolutePath()).overrideOutputFiles(true).addOutput(file.getParentFile().getAbsolutePath() + File.separator + file.getName().substring(0, idx) + ".mp4").setFormat("mp4").done();

			FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

			System.out.println("[-->] Conversion du fichier " + file.getName() + ", démarrage...");

			FFmpegJob job = executor.createJob(builder, new ProgressListener() {
				final double duration_ns = in.getFormat().duration * TimeUnit.SECONDS.toNanos(1);

				@Override
				public void progress(Progress progress) {
					progressConverting = progress.out_time_ms / duration_ns;
					System.out.println(String.format(
							"[%.0f%%] progress:%s frame:%d time:%s ms fps:%.0f speed:%.2fx",
							progressConverting * 100,
							progress.progress,
							progress.frame,
							FFmpegUtils.millisecondsToString(progress.out_time_ms),
							progress.fps.doubleValue(),
							progress.speed
					));
					/*MongoConversions.update();
					if(progressConverting >= 100){
						onFinish();
						System.out.println("[-->] Conversion terminé : " + file.getName());
					}*/
				}
			});

			job.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Abstract method

	public abstract void onProgress(Double percent);
	public abstract void onFinish();

}
