import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 * Deze klasse speelt mp3 bestanden af, wanneer ze in een andere pagina worden aangeroepen
 * 
 * @author Rick Johannes, Teijmen van der Ploeg, Lars Bosker
 * @version 2020.01.22
 */
public class SoundEffects
{
    // De huidige speler
    private AdvancedPlayer player;
    
    /**
     * Constructor voor objecten van de klasse SoundEffects
     */
    public SoundEffects()
    {
        player = null;
    }
    
    /**
     * Speelt het gegeven mp3-bestand af.
     * De methode retourneert als de speler is gestart.
     * @param filename Het bestand wat afgespeeld wordt.
     */
    public void startPlaying(final String filename)
    {
        try {
            setupPlayer(filename);
            Thread playerThread = new Thread() {
                public void run()
                {
                    try {
                        player.play(5000);
                    }
                    catch(JavaLayerException e) {
                        reportProblem(filename);
                    }
                    finally {
                        killPlayer();
                    }
                }
            };
            playerThread.start();
        }
        catch (Exception ex) {
            reportProblem(filename);
        }
    }
    
    public void stop()
    {
        killPlayer();
    }
    
    /**
     * Maakt de speler klaas om een mp3-bestand af te spelen.
     * @param filename Naam van het bestand wat afgespeelt gaat worden.
     */
    private void setupPlayer(String filename)
    {
        try {
            InputStream is = getInputStream(filename);
            player = new AdvancedPlayer(is, createAudioDevice());
        }
        catch (IOException e) {
            reportProblem(filename);
            killPlayer();
        }
        catch(JavaLayerException e) {
            reportProblem(filename);
            killPlayer();
        }
    }

    /**
     * Retourneert een inputstream voor het gegeven bestand.
     * @param filename Bestand wat geopend wordt.
     * @throws IOException Als het bestand niet kan worden geopend.
     * @return An input stream for the file.
     */
    private InputStream getInputStream(String filename)
        throws IOException
    {
        return new BufferedInputStream(
                    new FileInputStream(filename));
    }

    /**
     * Maakt een audio-apparaat aan.
     * @throws JavaLayerException als het apparaat niet aangemaakt kan worden.
     * @return een audio-apparaat.
     */
    private AudioDevice createAudioDevice()
        throws JavaLayerException
    {
        return FactoryRegistry.systemRegistry().createAudioDevice();
    }

    /**
     * Beëindig de speler als er een is.
     */
    private void killPlayer()
    {
        synchronized(this) {
            if(player != null) {
                player.stop();
                player = null;
            }
        }
    }
    
    /**
     * Report a problem playing the given file.
     * @param filename The file being played.
     */
    private void reportProblem(String filename)
    {
        System.out.println("There was a problem playing: " + filename);
    }

}

