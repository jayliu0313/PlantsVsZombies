package audioplayer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *this class is used to create a music player, with
 * play, stop, pause, loop, functionality, this class
 * must be used to put background music to a level
 * NOTE: the files to play must be in resources folder
 * @author pavulzavala
 * http://www.java-gaming.org/index.php?topic=38366.0
 */
public class MP3Player 
{
    
    private InputStream is;
    
    private Player player;
    private boolean repeat; 
    private boolean paused; 
    private long pauseLocation;
    private long totalSongLength;
    private String musicFilePath;
    
    //@TODO consider add a counter to play a song
    // X number of times

    /**
     * this method is used to play a song, if u want to
     * repeat this song,  set Repeat to true before 
     * call this method
     * NOTE: the files to play must be in resources folder
     * @param musicFilePath
     * @throws FileNotFoundException
     * @throws JavaLayerException
     * @throws IOException 
     * @throws java.net.URISyntaxException 
     */
    public void play( String musicFilePath ) throws FileNotFoundException, JavaLayerException, IOException, URISyntaxException
    {
        
   this.musicFilePath = musicFilePath;
   
   is = this.getClass().getResourceAsStream( musicFilePath );
   System.out.println(System.getProperty("user.dir")+" "+is+" "+musicFilePath);
   totalSongLength =  is.available();
   
    player = new Player( is );
    
        new Thread()
        {
            @Override
            public void run()
            {
                try 
                {
                    player.play();
                    
                    if( player.isComplete() && repeat )
                    {
                    play( musicFilePath );
                    }
                    
                    
                }
                catch ( JavaLayerException | IOException ex) 
                {
                    System.err.println("::: there was an error to play " + musicFilePath );
                } catch (URISyntaxException ex) {
                    Logger.getLogger(MP3Player.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }////
        }.start();///
    
    }//
    
    /**
     * use this method to remuse current paused song
     * @throws FileNotFoundException
     * @throws JavaLayerException
     * @throws IOException 
     * @throws java.net.URISyntaxException 
     */
    public void resume( ) throws FileNotFoundException, JavaLayerException, IOException, URISyntaxException
    {
    
        paused = false;
        
    is = this.getClass().getResourceAsStream( musicFilePath );
    
    is.skip( totalSongLength - pauseLocation );
    
    player = new Player( is );
    
        new Thread()
        {
            @Override
            public void run()
            {
                try 
                {
                    player.play();
                }
                catch (JavaLayerException ex) 
                {
                    System.err.println("::: there was an error to play " + musicFilePath );
                }
            }////
        }.start();///
    
    }//
    
    
    
    /**
     * use this method to stop current song that is being 
     * played
     */
    public void stop()
    {
    paused = false;
        
        if( null != player)
        {
        player.close();
        
        totalSongLength = 0;
        pauseLocation = 0;
        }///
        
    }//
    
    /**
     * use this method to pause current played song
     */
    public void pause()
    {
    
        paused = true;
        if( null != player)
        {
            try 
            {
                pauseLocation = is.available();
                player.close();
            } 
            catch (IOException ex) 
            {
                System.out.println("::: error when song is paused");
            }
        
        }///
        
    }//

    /**
     * 
     * @return true if the song i will start once is done,
     * false if not
     */
    public boolean isRepeat() {
        return repeat;
    }

    /**
     * set if the song will start once is done
     * @param repeat 
     */
    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
    
    public void close() { 
    	if (player != null) player.close(); 
    }

    // test client
    public static void main(String[] args) {
    	String filename = "resources/audio/level1.mp3";
        MP3Player mp3 = new MP3Player();
        
		try {
			mp3.play(filename);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JavaLayerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        // do whatever computation you like, while music plays
        int N = 4000;
        double sum = 0.0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum += Math.sin(i + j);
            }
        }
        System.out.println(sum);

        // when the computation is done, stop playing it
        mp3.close();

        // play from the beginning
        mp3 = new MP3Player();
        try {
			mp3.play(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    
}//class