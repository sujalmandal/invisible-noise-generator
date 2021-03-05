package utility.invisible_noise_generator;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;

public class SoundUtils {

	private static int sampleRate = 8000;
	private static boolean play = true;

	public static void play() {
		new Thread(() -> {
			play = true;
			System.out.println("started");
			while (play) {
				final AudioFormat af = new AudioFormat(sampleRate, 16, 1, true, true);
				try {
					SourceDataLine line = AudioSystem.getSourceDataLine(af);
					line.open(af);
					FloatControl volume = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
					line.start();
					if (volume != null) {
						volume.setValue((float) (-60.0));
					}
					play(line, generateSineWavefreq(200, 1));
					line.drain();
					line.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println("stoppped.");
		}).start();
	}

	public static void stop() {
		play = false;
	}

	public static boolean isPlaying() {return play;}
	
	private static byte[] generateSineWavefreq(int frequencyOfSignal, int seconds) {
		byte[] sin = new byte[seconds * sampleRate];
		double samplingInterval = (sampleRate / (double)frequencyOfSignal);
		for (int i = 0; i < sin.length; i++) {
			double angle = (2.0 * Math.PI * i) / samplingInterval;
			sin[i] = (byte) (Math.sin(angle) * 127);
		}
		return sin;
	}

	private static void play(SourceDataLine line, byte[] array) {
		line.write(array, 0, array.length);
	}
}
