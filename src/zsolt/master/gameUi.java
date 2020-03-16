package zsolt.master;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import sample.Settings;

public class gameUi extends Canvas {

    GraphicsContext gc;

    public gameUi(){

        // Set UI size
        super(Settings.width, Settings.height * Settings.uiHeight);

        // get graphics context
        gc = getGraphicsContext2D();

        // set UI position
        setLayoutX(0);
        setLayoutY(Settings.height * (1.0 - Settings.uiHeight));

        // Draw UI every 10 millis.
        Timeline t = new Timeline(new KeyFrame(Duration.millis(10), e->draw()));
        t.setCycleCount(Animation.INDEFINITE);
        t.play();

        // Time handler (every 1 sec)
        Timeline t2 = new Timeline(new KeyFrame(Duration.millis(1000), e->timeHandler()));
        t2.setCycleCount(Animation.INDEFINITE);
        t2.play();
    }

    void timeHandler(){
        if(!Settings.isPaused)
            Settings.time++;
    }

    String getTime(){
        int time = Settings.time;
        String finalTime = "", sec = "", min="";

        if(time < 10){
            sec = "0" + time;
            min = "00";
        }else if(time < 60){
            sec = time + "";
            min = "00";
        }else if(time < 600){
            if(time % 60 < 10){
                sec = "0" + time % 60;
            }else{
                sec = time % 60 + "";
            }
            min = "0" + (int)(time / 60);
        }else{
            if(time % 60 < 10){
                sec = "0" + time % 60;
            }else{
                sec = time % 60 + "";
            }
            min = (int)(time / 60) + "";
        }

        finalTime = min + ":" + sec;

        return finalTime;
    }

    void draw(){
        gc.clearRect(0, 0, getWidth(), getHeight());

        // Set font
        gc.setFont( new Font("Dimbo", 12 + (Settings.width * 0.02) ) );
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(Color.WHITE);

        // Score -> LEFT
        gc.fillText("Score: " + Settings.score, getWidth() * 0.1, getHeight() / 2);
        gc.strokeText("Score: " + Settings.score, getWidth() * 0.1, getHeight() / 2);

        // Game time -> CENTER
        gc.fillText(getTime(), getWidth() * 0.5, getHeight() / 2);
        gc.strokeText(getTime(), getWidth() * 0.5, getHeight() / 2);

        // Bullets -> RIGHT
        // TODO: Show current bullets
    }

}
