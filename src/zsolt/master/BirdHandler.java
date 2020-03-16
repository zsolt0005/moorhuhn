package zsolt.master;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.util.Duration;
import sample.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BirdHandler {

    static List<Bird> birds = new ArrayList<>();
    static Group g;

    public BirdHandler(Group g){

        // Set the group
        this.g = g;

        // Spawn birds (Spawn new bird each time there is less than the maximal amount)
        Timeline tSpawn = new Timeline(new KeyFrame(Duration.millis(Settings.birdSpawnTime), e->spawn()));
        tSpawn.setCycleCount(Animation.INDEFINITE);
        tSpawn.play();
    }

    void spawn(){
        if(Settings.isPaused)
            return;

        if(birds.size() < Settings.maxBirdsSpawned){

            // Bird size
            Random rnd = new Random();
            double sizeOfBird = rnd.nextDouble() + Settings.birdRandomizedSize;

            // Create bird
            Bird b = new Bird(sizeOfBird);

            // Set UI functionality
            sceneHandler.onMouseEnemy(b);

            // Add bird to live birds
            birds.add(b);

            // Display bird
            g.getChildren().add(birds.get(birds.size() - 1));

        }
    }

    public static void destroy(Bird b){
        // Get speed and size to determinate points to be added
        double speed = b.originalSpeed;
        double size = b.size;
        double maxSize = b.maxSize;
        double pointMultiplier = 10;

        // Add points
        int points = (int)(pointMultiplier * (speed + ( maxSize - size)));
        Settings.score += points;
        Settings.bulletsHit++;

        // TODO: Remove debug
        System.out.println("Points: " + points + " | Shots: " + Settings.bulletsShot + " | Hits: " + Settings.bulletsHit);

        // Delete from view
        g.getChildren().remove(b);

        // Remove from list
        birds.remove(b);

    }
}
