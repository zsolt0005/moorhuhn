package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

// My imports
import zsolt.master.sceneHandler;

public class Main extends Application {

    // <editor-fold desc="Variables">

    // Group element & window
    public static Stage window;

    // Scenes
    public static Scene mainMenu; // Main menu (lauch game, mute audio and music, check heighScore)
    public static Scene launchMenu; // Game settings and name
    public static Scene gameScene; // Actual game

    // Game masters
    public static sceneHandler sh = new sceneHandler();

    // </editor-fold>

    @Override
    public void start(Stage stage) throws Exception{
        Font.loadFont("file:fonts/DimboRegular.ttf", 12);

        // To have access from elsewhere to stage
        window = stage;

        // Setup stage
        setupWindow();
    }

    // Main
    public static void main(String[] args) {
        launch(args);
    }

    // Set up window
    void setupWindow(){
        // Base settings
        window.setTitle("Moorhuhn");
        window.setResizable(false);
        window.centerOnScreen();
        window.getIcons().add(new Image("file:img/icons/titleIcon.png"));
        window.show();

        // Setup scenes
        sh.setupLauchMenu();
    }
}