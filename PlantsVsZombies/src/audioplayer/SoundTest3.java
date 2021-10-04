package audioplayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class SoundTest3 {

	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		
		System.out.println("sample.au is playing...");
        Sound.play("resources/audio/sample.au");

        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        // play mp3 audio using JavaLayer jl driver, need to add jl-1.0.1.jar to library
		try {
			FileInputStream fileInputStream = new FileInputStream("resources/audio/song.mp3");
			Player mp3player = new Player(fileInputStream);
			System.out.println("Song.mp3 is playing...");
			mp3player.play();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
