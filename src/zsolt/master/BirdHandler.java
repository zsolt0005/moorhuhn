package zsolt.master;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class BirdHandler {

    List<Bird> birds = new ArrayList<>();
    Group g;

    public BirdHandler(Group g){

        // Set the group
        this.g = g;

        // Spawn birds (Spawn new bird each time there is less than the maximal amount)
        Timeline tSpawn = new Timeline(new KeyFrame(Duration.millis(500), e->spawn()));
        tSpawn.setCycleCount(Animation.INDEFINITE);
        tSpawn.play();
    }

    void spawn(){
        // TODO: Replace 1 with Settings.maxBirdsSpawned
        if(birds.size() < 1){
            // Spawn and setup bird
            Bird b = new Bird(1,1,1);
            sceneHandler.onMouseEnemy(b);
            birds.add(b);
            g.getChildren().add(birds.get(birds.size() - 1));
        }
    }

}
