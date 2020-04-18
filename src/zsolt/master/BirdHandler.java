package zsolt.master;

import javafx.scene.Group;
import sample.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BirdHandler {

    static List<Bird> birds = new ArrayList<>();
    public static Group g;

    public BirdHandler(Group g){

        // Set the group
        this.g = g;
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

    public static void clearBirds(){
        if(g != null)
            g.getChildren().removeAll();
        for(Bird b : birds){
            b.t.stop();
            b.t2.stop();
        }
        birds.clear();
    }
    public static void destroy(Bird b){
        // Delete from view
        g.getChildren().remove(b);

        // Remove from list
            // Get object
        int i =  birds.indexOf(b);
        Bird bird = birds.get(i);

            // Remove object from list
        birds.remove(i);
            // Destroy object
        bird = null;
    }
}
