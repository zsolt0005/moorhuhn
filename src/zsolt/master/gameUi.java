package zsolt.master;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import sample.Settings;

public class gameUi extends Canvas {

    GraphicsContext gc;
    Image bullet = new Image("file:img/bullets/bullet.png", 0, getHeight() * 0.8, true, true);

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
        if(Settings.isPaused)
            return;

        // Increase time
        Settings.time++;

        // Check for timeout
        if(Settings.time >= Settings.maxTime)
            sceneHandler.gameOver();

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

        // Score -> LEFT (Adding bullet width for the same spacing as bullets)
        gc.fillText("Score: " + Settings.score, (getWidth() * 0.05) + (bullet.getWidth() / 2), getHeight() / 2);
        gc.strokeText("Score: " + Settings.score, (getWidth() * 0.05) + (bullet.getWidth() / 2), getHeight() / 2);

        // Game time -> CENTER
        if(Settings.time <= Settings.maxTime / 2)
            gc.setFill(Color.GREEN);
        if(Settings.time > Settings.maxTime / 2 && Settings.time < Settings.maxTime * 0.75)
            gc.setFill(Color.ORANGE);
        if(Settings.time > Settings.maxTime * 0.75)
            gc.setFill(Color.RED);

        gc.fillText(getTime(), getWidth() * 0.5, getHeight() / 2);
        gc.strokeText(getTime(), getWidth() * 0.5, getHeight() / 2);

        // Bullets -> RIGHT
        double posY = (getHeight() / 2) - (bullet.getHeight() / 2);
        double posX = getWidth() * 0.95;
        int c = Settings.currentBullets;

        for(int i = 0; i < c; i++){
            gc.drawImage(bullet, posX - (i * bullet.getWidth()), posY);
        }
    }

}
