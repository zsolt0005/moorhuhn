package zsolt.master;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;
import sample.Settings;

import java.util.ArrayList;
import java.util.List;

public class Bird extends Canvas {

    GraphicsContext gc;
    List<Image> idle = new ArrayList<>();
    List<Image> dead = new ArrayList<>();

    // Animation
    Image toDraw;
    boolean isDead = false;
    int animCycle = 0;
    int animSpeed = 0;

    // Bird parameters
    double speed = 0;
    double verticalSpeed = 0;

    public Bird(double speed, double verticalSpeed, double size){
        // Set bird size
        /*
            CALCULATION OF THE SIZE (Not perfect not terrible :D )
            Actual PNG Size * wanted bird size in % * size multiplier on spawn + resolution scale
         */
        super(
                ( (420 * Settings.birdSize) * size) + Settings.width * 0.02,
                ( (360 * Settings.birdSize) * size) + Settings.width * 0.02
        );

        // Set basic location
        setLayoutX(0);
        setLayoutY(0);

        // Get graphics context
        gc = getGraphicsContext2D();

        // Set parameters
        this.speed = speed;
        this.verticalSpeed = verticalSpeed;
        this.animSpeed = (int)(100 * speed); // Differentiate speed with animation as well

        // Initialize resources
        init();

        // Animation
        Timeline t = new Timeline(new KeyFrame(Duration.millis(animSpeed), e->{
            // Set new image
            animation();

            // Call draw function
            draw();
        }));
        t.setCycleCount(Animation.INDEFINITE);
        t.play();

        // TODO: Move the bird

    }

    void animation(){
        // idle
        if(!isDead){
            toDraw = idle.get(animCycle);
            animCycle++;
            if(animCycle > idle.size() - 1)
                animCycle = 0;
        }
        // Dead
        else{
            toDraw = dead.get(animCycle);
            animCycle++;
            if(animCycle > dead.size() - 1)
                animCycle = 0;
        }
    }
    void init(){
        // Load all images for bird
            // IDLE
        idle.add(new Image("file:img/bird/idle/Idle_000.png"));
        idle.add(new Image("file:img/bird/idle/Idle_001.png"));
        idle.add(new Image("file:img/bird/idle/Idle_002.png"));
        idle.add(new Image("file:img/bird/idle/Idle_003.png"));
        idle.add(new Image("file:img/bird/idle/Idle_004.png"));
        idle.add(new Image("file:img/bird/idle/Idle_005.png"));
        idle.add(new Image("file:img/bird/idle/Idle_006.png"));
        idle.add(new Image("file:img/bird/idle/Idle_007.png"));
        idle.add(new Image("file:img/bird/idle/Idle_008.png"));
        idle.add(new Image("file:img/bird/idle/Idle_009.png"));
        idle.add(new Image("file:img/bird/idle/Idle_010.png"));
        idle.add(new Image("file:img/bird/idle/Idle_011.png"));
            // DEAD
        dead.add(new Image("file:img/bird/dead/Smoke_000.png"));
        dead.add(new Image("file:img/bird/dead/Smoke_001.png"));
        dead.add(new Image("file:img/bird/dead/Smoke_002.png"));
        dead.add(new Image("file:img/bird/dead/Smoke_003.png"));
        dead.add(new Image("file:img/bird/dead/Smoke_004.png"));
        dead.add(new Image("file:img/bird/dead/Smoke_005.png"));
        dead.add(new Image("file:img/bird/dead/Smoke_006.png"));
        dead.add(new Image("file:img/bird/dead/Smoke_007.png"));
        dead.add(new Image("file:img/bird/dead/Smoke_008.png"));
        dead.add(new Image("file:img/bird/dead/Smoke_009.png"));
    }
    void draw(){
        gc.clearRect(0,0,getWidth(),getHeight());

        gc.drawImage(toDraw, 0, 0, getWidth(), getHeight());
    }
}
