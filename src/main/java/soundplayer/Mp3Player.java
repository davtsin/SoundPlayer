/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundplayer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.UnsupportedAudioFileException;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

/**
 *
 * @author denis
 */
public class Mp3Player {

    private BasicPlayer player = new BasicPlayer();
    private BasicController control
        = (BasicController) player;
    private MpegAudioFileReader mar
        = new MpegAudioFileReader();
    private File currentFile;

    public void play(File file) {
        try {
            if (player.getStatus() == BasicPlayer.PAUSED
                && file.equals(currentFile)) {
                control.resume();
                return;
            }
            InputStream is = mar.getAudioInputStream(file);
            control.open(is);
            control.play();
            currentFile = file;
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(SoundPlayerGUI.class.getName()).
                log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SoundPlayerGUI.class.getName()).
                log(Level.SEVERE, null, ex);
        } catch (BasicPlayerException ex) {
            Logger.getLogger(SoundPlayerGUI.class.getName()).
                log(Level.SEVERE, null, ex);
        }
    }

    public void pause() {
        try {
            control.pause();
        } catch (BasicPlayerException ex) {
            Logger.getLogger(Mp3Player.class.getName()).
                log(Level.SEVERE, null, ex);
        }
    }

    public void stop() {
        try {
            control.stop();
        } catch (BasicPlayerException ex) {
            Logger.getLogger(Mp3Player.class.getName()).
                log(Level.SEVERE, null, ex);
        }
    }
}
