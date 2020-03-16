package zsolt.master;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import sample.Settings;

import java.io.File;
import java.util.Random;

public class audio {

    // Background music
    static AudioClip bg1 = new AudioClip(new File("sounds/bg1.mp3").toURI().toString());
    static AudioClip bg2 = new AudioClip(new File("sounds/bg2.mp3").toURI().toString());
    static AudioClip bg3 = new AudioClip(new File("sounds/bg3.mp3").toURI().toString());
    int lastPlayed = 0;
    boolean soundState = true;

    // Sound effects
    static AudioClip uiEffect = new AudioClip(new File("sounds/uiEffect.mp3").toURI().toString());
    static AudioClip hit = new AudioClip(new File("sounds/hit.mp3").toURI().toString());
    static AudioClip reload = new AudioClip(new File("sounds/reload.mp3").toURI().toString());
    static AudioClip shot = new AudioClip(new File("sounds/shot.mp3").toURI().toString());

    // Initialize audio
    public audio(){

        // <editor-fold desc="Set audio volume">

        bg1.setVolume(Settings.musicVolume);
        bg2.setVolume(Settings.musicVolume);
        bg3.setVolume(Settings.musicVolume);
        uiEffect.setVolume(Settings.soundVolume);
        hit.setVolume(Settings.soundVolume);
        reload.setVolume(Settings.soundVolume);
        shot.setVolume(Settings.soundVolume);

        // </editor-fold>

        // <editor-fold desc="Start first background music">

        Random rnd = new Random();
        int bg = rnd.nextInt((3 - 1) + 1) + 1;

        if(bg == 1){
            bg1.play();
            lastPlayed = 1;
        }
        else if(bg == 2){
            bg2.play();
            lastPlayed = 2;
        }
        else if(bg == 3){
            bg3.play();
            lastPlayed = 3;
        }

        // </editor-fold>

        // Play after each music ends other
        Timeline t = new Timeline(new KeyFrame(Duration.millis(1000), e->{
            // Check for end of bg and play another
            if(!bg1.isPlaying() && !bg2.isPlaying() && !bg3.isPlaying() && Settings.music){
                // random play next song
                int newbg = rnd.nextInt((3 - 1) + 1) + 1;
                while (newbg == lastPlayed){
                    newbg = rnd.nextInt((3 - 1) + 1) + 1;
                }
                // Play song
                if(newbg == 1){
                    bg1.play();
                    lastPlayed = 1;
                }
                else if(newbg == 2){
                    bg2.play();
                    lastPlayed = 2;
                }
                else if(newbg == 3){
                    bg3.play();
                    lastPlayed = 3;
                }
            }

            // Check if sound state is changed
            if(soundState != Settings.music){

                // Save state
                soundState = Settings.music;

                if(Settings.music)
                    return;

                // Stop music
                bg1.stop();
                bg2.stop();
                bg3.stop();
            }

        }));
        t.setCycleCount(Animation.INDEFINITE);
        t.play();

    }

    // Play effects
    static public void playUiEffects(){ if(Settings.sound) uiEffect.play(); }
    static public void playHit(){ if(Settings.sound) hit.play(); }
    static public void playReload(){ if(Settings.sound) reload.play(); }
    static public void playShot(){ if(Settings.sound) shot.play(); }

}
