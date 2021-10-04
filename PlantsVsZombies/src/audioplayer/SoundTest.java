package audioplayer;
import java.io.FileNotFoundException;

public class SoundTest {

	public static void main(String[] args){
		
		System.out.println(System.getProperty("user.dir"));
		
		System.out.println("sample.au is playing...");
        Sound.play("resources/audio/sample.au");
        
	}

}
