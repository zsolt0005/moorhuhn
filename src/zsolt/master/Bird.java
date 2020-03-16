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
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Bird extends Canvas {

    // Passive birds
    boolean isPassive = false; // If true cant be destroyed (For menus creen only)

    GraphicsContext gc;
    List<Image> idle = new ArrayList<>();
    List<Image> dead = new ArrayList<>();

    // Animation
    Image toDraw;
    boolean isDead = false;
    boolean finishedDie = false;
    int animCycle = 0;
    int animSpeed = 0;

    // Bird parameters
    double speed = 0;
    double originalSpeed = 0;
    double maxSize = 0;
    double size = 0;
    double verticalSpeed = 0;
    double verticalMove;
    double startPosY;

    // Movement
    double direction = 1;
    double verticalDirection = 1;

    public Bird(double size){
        // Set bird size
        /*
            CALCULATION OF THE SIZE (Not perfect not terrible :D )
            Actual PNG Size * wanted bird size in % * size multiplier on spawn + resolution scale
         */
        super(
                ( (420 * Settings.birdSize) * size) + (Settings.width * 0.02),
                ( (360 * Settings.birdSize) * size) + (Settings.width * 0.02)
        );

        // Spawn
        setLayoutX(ThreadLocalRandom.current().nextInt(0, Settings.width));
        setLayoutY(ThreadLocalRandom.current().nextInt(0, (int)(Settings.height * 0.2) ));

        // Get graphics context
        gc = getGraphicsContext2D();

        // Set parameters
        this.size = size + (Settings.width * 0.02);
        this.maxSize = 1.0 + Settings.birdRandomizedSize + (Settings.width * 0.02);
        this.speed = ThreadLocalRandom.current().nextDouble(Settings.minBirdsSpeed, Settings.maxBirdsSpeed);
        this.speed = this.speed + (Settings.time * 0.003); // Faster as time going
        this.originalSpeed = this.speed; // Before destroying the bird its speed is set to 0 so the original speed of the bird has to be saved
        this.verticalSpeed = ThreadLocalRandom.current().nextDouble(Settings.minBirdsVerticalSpeed, Settings.maxBirdsVerticalSpeed);
        this.verticalMove = ThreadLocalRandom.current().nextDouble(Settings.minBirdsVerticalMove, Settings.maxBirdsVerticalMove);

        // Set move direction
        Random rnd = new Random();
        int r = ThreadLocalRandom.current().nextInt(0, 1);
        if(r == 0) direction = 1;
        else direction = -1;

        // Animation speed
        this.animSpeed = (int)(400 * ((Settings.maxBirdsSpeed + 0.1) - this.speed)); // Differentiate speed with animation as well

        // Save start PosY
        this.startPosY = getLayoutY();

        // Initialize resources
        init();

        // Animation
        Timeline t = new Timeline(new KeyFrame(Duration.millis(animSpeed), e->{
            if(Settings.isPaused || finishedDie)
                return;

            // Set new image
            animation();

            // Call draw function
            draw();
        }));
        t.setCycleCount(Animation.INDEFINITE);
        t.play();

        // Move
        Timeline t2 = new Timeline(new KeyFrame(Duration.millis(1), e->{
            if(Settings.isPaused || finishedDie)
                return;

            // Move
            move();

        }));
        t2.setCycleCount(Animation.INDEFINITE);
        t2.play();

        // Set on click
        setOnMouseClicked(e->{
            if(isDead || Settings.isPaused || isPassive || Settings.currentBullets -1 < 0)
                return;

            // Set dead state
            this.isDead = true;
            // Set fixed animation speed for die animation
            this.animSpeed = 300;
            // Start animation frame at 0
            this.animCycle = 0;
            // Stop movement
            this.speed = 0;
            this.verticalSpeed = 0;

            // Play sound effect
            audio.playHit();

            // Set cursor to neutral
            sceneHandler.onMouseEnemyDie(this);

        });
    }

    void die(){
        BirdHandler.destroy(this);
    }
    void move(){
        // Move vertically
        if( (getLayoutY() + this.verticalSpeed ) > startPosY + verticalMove && verticalDirection > 0)
            verticalDirection = -verticalDirection;

        if( (getLayoutY() - this.verticalSpeed ) < startPosY && verticalDirection < 0)
            verticalDirection = -verticalDirection;

        setLayoutY( getLayoutY() + (this.verticalSpeed * verticalDirection) );

        // Move horizontally
        if( (getLayoutX() + this.speed ) + getWidth()  > Settings.width && direction > 0)
        {
            direction = -direction;
            setScaleX(-1);
        }

        if( (getLayoutX() - this.speed ) < 0 && direction < 0)
        {
            direction = -direction;
            setScaleX(1);
        }

        setLayoutX( getLayoutX() + (this.speed * direction) );

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
            if(animCycle > dead.size() - 1){
                this.finishedDie = true;
                die();
            }
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
