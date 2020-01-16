import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax. swing.JOptionPane;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
/**
 * @author Lars Bosker
 */     
public class PlayMusic {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        playMusic("escape-copy\\\\music\\\\RELOADING.wav");
    }
    
    public static void playMusic(String filepath) {
        InputStream music;
        try
        {
            music = new FileInputStream(new File(filepath));
            AudioStream audios = new AudioStream(music);
            AudioPlayer.player.start(audios);
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }
}

