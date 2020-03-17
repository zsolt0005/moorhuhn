package zsolt.master;

// javaFX
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.util.Duration;
import sample.Main;
import sample.Settings;

// AWT
import java.awt.*;
import java.util.Random;

// My imports

public class sceneHandler {

    // <editor-fold desc="Variables">

    // Audio
    public static audio audio = new audio();

    // Reloading
    static Timeline tReload = new Timeline(new KeyFrame(Duration.millis(1), e->{
        doReloading();
    }));
    public static boolean waitForRelodIsplaying = false;

    // Cursors
    static Image cursor0 = new Image("file:img/crosshair/crosshair0.png");
    static Image cursor1 = new Image("file:img/crosshair/crosshair2.png");
    static Image cursor2 = new Image("file:img/crosshair/crosshair1.png");
    static ImageCursor ic0 = new ImageCursor(cursor0, cursor0.getWidth() / 2, cursor0.getHeight() / 2);
    static ImageCursor ic1 = new ImageCursor(cursor1, cursor0.getWidth() / 2, cursor0.getHeight() / 2);
    static ImageCursor ic2 = new ImageCursor(cursor2, cursor0.getWidth() / 2, cursor0.getHeight() / 2);

    // Temporary
    static Group gGameMenu;
    Group gPauseMenu;
    static Group gGameOver;
    BirdHandler bh;
    gameUi gui;

    // </editor-fold>

    // LAUNCH SCENE TODO: DEVELOPER SETTINGS SET
    public void setupLaunchMenu(){
        // Get menu
        Group g = new Group();
        StackPane root = new StackPane();

        // Create the scene itself
        Scene m = new Scene(g, Settings.launchWidth, Settings.launchHeight);
        m.getStylesheets().add("file:css/global.css");

        // <editor-fold desc="background image">
        Image bg = new Image("file:img/bg/launch.png");
        ImageView iv_bg = new ImageView(bg);
        iv_bg.setFitHeight(Settings.launchHeight);
        iv_bg.setFitWidth(Settings.launchWidth);
        // </editor-fold>

        // <editor-fold desc="Logo">
        Image logo = new Image("file:img/logo/logo.png");
        ImageView iv_logo = new ImageView(logo);
        iv_logo.setPreserveRatio(true);
        iv_logo.setFitWidth(Settings.launchWidth * 0.9);
        // </editor-fold>

        // <editor-fold desc="Enter username">
        HBox hb_username = new HBox();
        hb_username.setSpacing(5.0);

        // Label
        Label lb_enterUsername = new Label("Enter your username: ");

        // Text field
        TextField tf_username = new TextField();

        hb_username.getChildren().addAll(lb_enterUsername, tf_username);
        // </editor-fold>

        // <editor-fold desc="Cursor settings">
        HBox hb_useCursor = new HBox();
        hb_useCursor.setSpacing(5.0);

        // Label
        Label lb_useCursor = new Label("Default cursor ");

        // CheckBox
        CheckBox chb_useCursor = new CheckBox();
        onMouseUI(chb_useCursor);

        chb_useCursor.setOnMouseClicked(e->{
            Settings.defaultCursor = !Settings.defaultCursor;
            if(Settings.defaultCursor) {
                Main.launchMenu.setCursor(Cursor.DEFAULT);
            }
            else {
                Main.launchMenu.setCursor(ic0);
            }
        });
        hb_useCursor.getChildren().addAll(lb_useCursor, chb_useCursor);
        // </editor-fold>

        // <editor-fold desc="Resolution">
        VBox vb_resolution = new VBox();
        HBox hb_resolution = new HBox();
        hb_resolution.setSpacing(20);

        // Label
        Label lb_resolution = new Label("Select resolution");
        Label lb_resolution_note = new Label("Note: If your SCREEN resolution matches the selected \n resolution, the game will launch in FULL SCREEN mode!");
        lb_resolution_note.setStyle("-fx-font-size: 14; -fx-text-fill: red;");

        // ComboBox
        ComboBox cb_resolution = new ComboBox();
        onMouseUI(cb_resolution);
        cb_resolution.getItems().addAll(
                "3840 x 2160",
                "1920 x 1080",
                "1280 x 720",
                "1024 x 576"
        );

        hb_resolution.getChildren().addAll(lb_resolution, cb_resolution);
        vb_resolution.getChildren().addAll(hb_resolution, lb_resolution_note);
        // </editor-fold>

        // <editor-fold desc="Load basic resolution settings">

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();
        Settings.resolutionX = screenWidth;
        Settings.resolutionY = screenHeight;

        if(screenWidth <= 3840) {
            if(screenHeight <= 2160) {
                cb_resolution.getSelectionModel().select(0);
            }
        }

        if(screenWidth <= 1920) {
            if(screenHeight <= 1080) {
                cb_resolution.getSelectionModel().select(1);
            }
        }

        if(screenWidth <= 1280) {
            if(screenHeight <= 720) {
                cb_resolution.getSelectionModel().select(2);
            }
        }

        if(screenWidth <= 1024) {
            if(screenHeight <= 576) {
                cb_resolution.getSelectionModel().select(3);
            }
        }

        // </editor-fold>

        // <editor-fold desc="Save game settings">

        Image img_play = new Image("file:img/btn/play.png");
        ImageView iv_play = new ImageView(img_play);
        onMouseUI(iv_play);
        iv_play.setFitHeight(Settings.launchWidth * 0.2);
        iv_play.setFitWidth(Settings.launchWidth * 0.2);

        // On click event
        iv_play.setOnMouseClicked(e->{
            int resW = 0;
            int resH = 0;
            if(cb_resolution.getValue() == "3840 x 2160"){
                resW = 3840;
                resH = 2160;
            }
            else if(cb_resolution.getValue() == "1920 x 1080"){
                resW = 1920;
                resH = 1080;
            }
            else if(cb_resolution.getValue() == "1280 x 720"){
                resW = 1280;
                resH = 720;
            }
            else if(cb_resolution.getValue() == "1024 x 576"){
                resW = 1024;
                resH = 576;
            }
            else{
                resW = 0;
                resH = 0;
            }

            saveSettings(tf_username.getText(), chb_useCursor.isSelected(), resW, resH);
        });

        // </editor-fold>

        // <editor-fold desc="Set all options under each other">

        VBox vb = new VBox();
        vb.setSpacing(10);
        vb.setAlignment(Pos.TOP_CENTER);

        // Set margins
        vb.setMargin(iv_logo, new Insets(30, 0, 20, 0));
        vb.setMargin(hb_username, new Insets(0, 0, 0, 60));
        vb.setMargin(hb_useCursor, new Insets(0, 0, 0, 60));
        vb.setMargin(vb_resolution, new Insets(0, 0, 0, 60));
        vb.setMargin(iv_play, new Insets(60, 0, 0, 0));

        vb.getChildren().addAll(iv_logo, hb_username, hb_useCursor, vb_resolution, iv_play);

        // </editor-fold>

        // Add all to scene
        root.getChildren().addAll(iv_bg, vb);
        g.getChildren().addAll(root);

        // Set menu
        Main.launchMenu = m;

        // Setup basic scenes for other scenes (Needed for changeCursor function)
        Main.gameScene = new Scene(new Group(), Settings.width, Settings.height);
        Main.mainMenu = new Scene(new Group(), Settings.width, Settings.height);

        // Set base cursor type
        changeCursor(0);

        // TODO: DEVELOPER SETTIGNS

        // Show this scene
        // changeScene(Main.launchMenu);
        ///*
        Settings.username = "Developer build";
        Settings.width = 1280;
        Settings.height = 720;
        setupMainMenu();
        Main.mainMenu.setCursor(ic0);
        changeScene(Main.mainMenu);
        //*/
    }

    // MAIN SCENE
    void setupMainMenu(){
        // Get menu
        Group g = new Group();
        StackPane root = new StackPane();

        // Create scene and apply global css
        Scene m = new Scene(g, Settings.width, Settings.height);
        m.getStylesheets().add("file:css/global.css");

        // <editor-fold desc="Background">

        // Generate random background each time the scene is created (Only when the application is started)

        Random rnd = new Random();

        int bgType = rnd.nextInt((4 - 1) + 1) + 1;

        Image bg = new Image("file:img/bg/BG_0" + bgType + ".png");
        ImageView iv_bg = new ImageView(bg);
        iv_bg.setFitHeight(Settings.height);
        iv_bg.setFitWidth(Settings.width);

        // </editor-fold>

        // <editor-fold desc="Flying birds">

        Group gBG = new Group();
        for(int i = 0; i < Settings.mainMenuBirdsCount; i++){
            // Bird size
            double sizeOfBird = rnd.nextDouble() + Settings.birdRandomizedSize;

            // Create bird
            Bird b = new Bird(sizeOfBird);
            b.isPassive = true;
            gBG.getChildren().add(b);
        }

        // </editor-fold>

        // <editor-fold desc="Logo">

        Image logo = new Image("file:img/logo/logo.png");
        ImageView iv_logo = new ImageView(logo);
        iv_logo.setPreserveRatio(true);
        iv_logo.setFitWidth(Settings.width * 0.4);
        iv_logo.setLayoutX( (Settings.width / 2) - (iv_logo.getFitWidth() / 2) );
        iv_logo.setLayoutY( Settings.height * 0.05 );

        // </editor-fold>

        // <editor-fold desc="Set VBox for MENU">

        // Box for the Menu items
        VBox vb_menu = new VBox();
        vb_menu.setPrefWidth(Settings.width);

        // Center horizontally
        vb_menu.setAlignment(Pos.TOP_CENTER);
        // Start Y os under logo (get logo height + 15% of game screen height)
        vb_menu.setLayoutY(iv_logo.boundsInLocalProperty().getValue().getHeight() + Settings.height * 0.1);

        // </editor-fold>

        // <editor-fold desc="BUTTON Play">
        Image img_play = new Image("file:img/btn/play.png");
        ImageView iv_play = new ImageView(img_play);
        iv_play.setPreserveRatio(true);
        iv_play.setFitWidth(Settings.width * 0.08);

        Button btn_play = new Button();
        btn_play.setGraphic(iv_play);
        onMouseUI(btn_play);
        btn_play.setOnMouseClicked(e->startGame());

        // </editor-fold>

        // <editor-fold desc="BUTTON Mute/Unmute">

        HBox hb_audio = new HBox();
        hb_audio.setAlignment(Pos.TOP_CENTER);

        // Load images
        Image img_musicOn = new Image("file:img/btn/musicOn.png");
        Image img_musicOff = new Image("file:img/btn/musicOff.png");
        Image img_soundOff = new Image("file:img/btn/soundOff.png");
        Image img_soundOn = new Image("file:img/btn/soundOn.png");

        // Base image view
        ImageView iv_music = new ImageView(img_musicOn);
        ImageView iv_sound = new ImageView(img_soundOn);

        // Setting up imageView
        iv_sound.setPreserveRatio(true);
        iv_sound.setFitWidth(Settings.width * 0.04);
        iv_music.setPreserveRatio(true);
        iv_music.setFitWidth(Settings.width * 0.04);

        // Buttons
        Button btn_music = new Button();
        btn_music.setGraphic(iv_music);
        onMouseUI(btn_music);

        Button btn_sound = new Button();
        btn_sound.setGraphic(iv_sound);
        onMouseUI(btn_sound);

        // Events
        btn_music.setOnMouseClicked(e->{
            // Toggle settings
            if(Settings.music)
                iv_music.setImage(img_musicOff);
            else
                iv_music.setImage(img_musicOn);

            Settings.music = !Settings.music;
        });
        btn_sound.setOnMouseClicked(e->{
            // Toggle settings
            if(Settings.sound)
                iv_sound.setImage(img_soundOff);
            else
                iv_sound.setImage(img_soundOn);

            Settings.sound = !Settings.sound;
        });

        hb_audio.getChildren().addAll(btn_music, btn_sound);
        // </editor-fold>

        // TODO: Highscore
        // <editor-fold desc="BUTTON Highscore">

        Image img_highScore = new Image("file:img/btn/highScore.png");
        ImageView iv_highScore = new ImageView(img_highScore);
        iv_highScore.setPreserveRatio(true);
        iv_highScore.setFitWidth(Settings.width * 0.15);

        Button btn_highScore = new Button();
        btn_highScore.setGraphic(iv_highScore);
        onMouseUI(btn_highScore);

        // </editor-fold>

        // <editor-fold desc="BUTTON Exit">

        Image img_exit = new Image("file:img/btn/exit.png");
        ImageView iv_exit = new ImageView(img_exit);
        iv_exit.setPreserveRatio(true);
        iv_exit.setFitWidth(Settings.width * 0.15);

        Button btn_exit = new Button();
        btn_exit.setGraphic(iv_exit);
        onMouseUI(btn_exit);

        btn_exit.setOnMouseClicked(e->{
            System.exit(0);
        });

        // </editor-fold>

        // Add all elements to scene
        vb_menu.getChildren().addAll(btn_play, hb_audio, btn_highScore, btn_exit);
        g.getChildren().addAll(iv_bg, gBG, iv_logo, vb_menu);

        // Set menu
        Main.mainMenu = m;
    }

    // GAME SCENE
    void setupGameScene(){
        // Reset settings
        resetSettings();

        // Get menu
        gGameMenu = new Group();
        StackPane root = new StackPane();

        Scene m = new Scene(gGameMenu, Settings.width, Settings.height);
        m.getStylesheets().add("file:css/global.css");

        // <editor-fold desc="Background">

        // Generate random background each time the scene is created (Only when the application is started)

        Random rnd = new Random();

        int bgType = rnd.nextInt((4 - 1) + 1) + 1;

        Image bg = new Image("file:img/bg/BG_0" + bgType + ".png");
        ImageView iv_bg = new ImageView(bg);
        iv_bg.setFitHeight(Settings.height);
        iv_bg.setFitWidth(Settings.width);

        // </editor-fold>

        // <editor-fold desc="Game UI">

        if(gui == null)
            gui = new gameUi();

        // </editor-fold>

        // <editor-fold desc="Bird handler">

        bh = new BirdHandler(gGameMenu);

        // </editor-fold>

        // <editor-fold desc="Detect actions">

        // On click
        m.setOnMousePressed(e->shot(e));

        // On keyboard pressed
        m.setOnKeyPressed(e->{

            if(Settings.time >= Settings.maxTime)
                return;

            // (R) -> reload
            if(e.getCode() == KeyCode.R)
                reload();

            // (ESC) -> pause
            if(e.getCode() == KeyCode.ESCAPE){
                Settings.isPaused = !Settings.isPaused;

                if(Settings.isPaused){
                    showPauseMenu();
                    Settings.music = false;
                }
                else{
                    hidePauseMenu();
                    Settings.music = true;
                }
            }
        });

        // </editor-fold>

        // Add all elements to scene
        gGameMenu.getChildren().addAll(iv_bg, gui);

        // Set menu
        Main.gameScene = m;
    }

    // PAUSE
    void showPauseMenu(){
        gPauseMenu = new Group();

        // <editor-fold desc="Setup menu">

            // Prepare size
        int prefWidth = (int)(Settings.width * 0.3);
        int prefHeight = (int)(Settings.height * 0.7);

            // Set size
        gPauseMenu.prefWidth(prefWidth);
        gPauseMenu.prefHeight(prefHeight);

            // Set location
        gPauseMenu.setLayoutX((Settings.width / 2) - (prefWidth / 2));
        gPauseMenu.setLayoutY((Settings.height / 2) - (prefHeight / 2));

        // </editor-fold>

        // <editor-fold desc="Background">

        Image bg = new Image("file:img/menu/table.png");
        ImageView iv_bg = new ImageView(bg);
        iv_bg.setFitWidth(prefWidth);
        iv_bg.setFitHeight(prefHeight);

        // </editor-fold>

        // <editor-fold desc="Pause logo">

        Image pauseLogo = new Image("file:img/menu/pause-logo.png");
        ImageView iv_pauseLogo = new ImageView(pauseLogo);
        iv_pauseLogo.setPreserveRatio(true);
        iv_pauseLogo.setFitWidth(prefWidth * 0.8);
        iv_pauseLogo.setLayoutX( (prefWidth / 2) - (iv_pauseLogo.getFitWidth() / 2) );

        // </editor-fold>

        // Box for resume menu
        VBox vbResume = new VBox();

        double prefBTNWidth = prefWidth * 0.5;

        // <editor-fold desc="BUTTON Resume">

        Image imgResume = new Image("file:img/btn/resume.png");
        ImageView iv_resume = new ImageView(imgResume);
        iv_resume.setPreserveRatio(true);
        iv_resume.setFitWidth(prefBTNWidth);
        onMouseUI(iv_resume);
        iv_resume.setOnMouseClicked(e->{
            hidePauseMenu();
            Settings.music = true;
            Settings.isPaused = false;
        });

        // </editor-fold>

        // <editor-fold desc="BUTTON Exit">

        Image img_exit = new Image("file:img/btn/exit.png");
        ImageView iv_exit = new ImageView(img_exit);
        iv_exit.setPreserveRatio(true);
        iv_exit.setFitWidth(prefBTNWidth);
        onMouseUI(iv_exit);
        iv_exit.setOnMouseClicked(e->{
            changeScene(Main.mainMenu);
            Settings.music = true;
            Settings.isPaused = false;
            tReload.stop();
        });

        // </editor-fold>

        // Setup VBox
        vbResume.setPrefWidth(prefWidth);
        vbResume.setPrefHeight(prefHeight);
        vbResume.setAlignment(Pos.CENTER);

        // Add elements
        vbResume.getChildren().addAll(iv_resume, iv_exit);
        gPauseMenu.getChildren().addAll(iv_bg, iv_pauseLogo, vbResume);

        // Show menu
        gGameMenu.getChildren().add(gPauseMenu);
    }
    void hidePauseMenu() {
        // Hide menu
        gGameMenu.getChildren().remove(gPauseMenu);
    }

    // GAME OVER
    public static void gameOver(){

        // Stop game
        Settings.isPaused = true;

        gGameOver = new Group();

        // <editor-fold desc="Setup menu">

        // Prepare size
        int prefWidth = (int)(Settings.width * 0.3);
        int prefHeight = (int)(Settings.height * 0.7);

        // Set size
        gGameOver.prefWidth(prefWidth);
        gGameOver.prefHeight(prefHeight);

        // Set location
        gGameOver.setLayoutX((Settings.width / 2) - (prefWidth / 2));
        gGameOver.setLayoutY((Settings.height / 2) - (prefHeight / 2));

        // </editor-fold>

        // <editor-fold desc="Background">

        Image bg = new Image("file:img/menu/table.png");
        ImageView iv_bg = new ImageView(bg);
        iv_bg.setFitWidth(prefWidth);
        iv_bg.setFitHeight(prefHeight);

        // </editor-fold>

        // <editor-fold desc="Game over logo">

        Image pauseLogo = new Image("file:img/menu/gameOver-logo.png");
        ImageView iv_pauseLogo = new ImageView(pauseLogo);
        iv_pauseLogo.setPreserveRatio(true);
        iv_pauseLogo.setFitWidth(prefWidth * 0.8);
        iv_pauseLogo.setLayoutX( (prefWidth / 2) - (iv_pauseLogo.getFitWidth() / 2) );

        // </editor-fold>

        // <editor-fold desc="Statistics">
        VBox vbStats = new VBox();

        // HBox SCORE
        HBox hbScore = new HBox();
        Label lb_text1 = new Label("Score: ");
        Label lb_Score = new Label(Settings.score + "");
        lb_text1.setId("GameOverLabel");
        lb_Score.setId("Highlight");
        hbScore.getChildren().addAll(lb_text1, lb_Score);
        hbScore.setPrefWidth(prefWidth);
        hbScore.setAlignment(Pos.CENTER);

        // HBox Bullets
        HBox hbBullets = new HBox();
        Label lb_text2 = new Label("Bullets shot: ");
        Label lb_BulletsShot = new Label(Settings.bulletsShot + "");
        lb_text2.setId("GameOverLabel");
        lb_BulletsShot.setId("Highlight");
        hbBullets.getChildren().addAll(lb_text2, lb_BulletsShot);
        hbBullets.setPrefWidth(prefWidth);
        hbBullets.setAlignment(Pos.CENTER);

        // HBox Bullets Hit
        HBox hbBulletsHit = new HBox();
        Label lb_text3 = new Label("Bullets hit: ");
        Label lb_BulletsShotHit = new Label(Settings.bulletsHit + "");
        lb_text3.setId("GameOverLabel");
        lb_BulletsShotHit.setId("Highlight");
        hbBulletsHit.getChildren().addAll(lb_text3, lb_BulletsShotHit);
        hbBulletsHit.setPrefWidth(prefWidth);
        hbBulletsHit.setAlignment(Pos.CENTER);

        // HBox Bullets Missed
        HBox hbBulletsMiss = new HBox();
        Label lb_text4 = new Label("Bullets missed: ");
        Label lb_BulletsShotMiss = new Label((Settings.bulletsShot - Settings.bulletsHit) + "");
        lb_text4.setId("GameOverLabel");
        lb_BulletsShotMiss.setId("Highlight");
        hbBulletsMiss.getChildren().addAll(lb_text4, lb_BulletsShotMiss);
        hbBulletsMiss.setPrefWidth(prefWidth);
        hbBulletsMiss.setAlignment(Pos.CENTER);

        // HBox Accuracy
        double accuracy = 0;
        if(Settings.bulletsHit != 0)
            accuracy = (double)(Settings.bulletsHit * 100) / (double)Settings.bulletsShot;

        HBox hbAccuracy = new HBox();
        Label lb_text5 = new Label("Accuracy: ");
        Label lb_Accuracy = new Label(accuracy + "%");
        lb_text5.setId("GameOverLabel");
        lb_Accuracy.setId("Highlight");
        hbAccuracy.getChildren().addAll(lb_text5, lb_Accuracy);
        hbAccuracy.setPrefWidth(prefWidth);
        hbAccuracy.setAlignment(Pos.CENTER);

        // </editor-fold>

        // TODO: Add functionality to buttons, scale stat text
        // <editor-fold desc="Buttons">

        // Box for GAME OVER menu
        HBox hbmenu = new HBox();

        double prefBTNWidth = prefWidth * 0.2;

        // <editor-fold desc="BUTTON Again">

        Image imgAgain = new Image("file:img/btn/button_restart.png");
        ImageView iv_Again = new ImageView(imgAgain);
        iv_Again.setPreserveRatio(true);
        iv_Again.setFitWidth(prefBTNWidth);
        onMouseUI(iv_Again);
        iv_Again.setOnMouseClicked(e->{
            System.out.println("Again");
        });

        // </editor-fold>

        // <editor-fold desc="BUTTON Close">

        Image imgClose = new Image("file:img/btn/button_close.png");
        ImageView iv_Close = new ImageView(imgClose);
        iv_Close.setPreserveRatio(true);
        iv_Close.setFitWidth(prefBTNWidth);
        onMouseUI(iv_Close);
        iv_Close.setOnMouseClicked(e->{
            System.out.println("Close");
        });

        // </editor-fold>

        // Setup VBox
        hbmenu.setPrefWidth(prefWidth);
        hbmenu.setSpacing(prefWidth * 0.1);
        hbmenu.setAlignment(Pos.BOTTOM_CENTER);

        // </editor-fold>

        // Setup VBox for stats
        vbStats.setPrefWidth(prefWidth);
        vbStats.setPrefHeight(prefHeight);
        vbStats.setSpacing(prefHeight * 0.02);
        vbStats.setMargin(hbScore, new Insets(prefHeight * 0.15, 0, 0, 0));

        // Add elements
        hbmenu.getChildren().addAll(iv_Again, iv_Close);
        vbStats.getChildren().addAll(hbScore, hbBullets, hbBulletsHit, hbBulletsMiss, hbAccuracy, hbmenu);
        gGameOver.getChildren().addAll(iv_bg, iv_pauseLogo, vbStats);

        // Show menu
        gGameMenu.getChildren().add(gGameOver);

    }

    // Set back settings
    public static void resetSettings(){
        Settings.isPaused = false;
        Settings.score = 0;
        Settings.time = 0;
        Settings.maxTime = 2; // TODO: change back to 90
        // Bullets
        Settings.reloading = false;
        Settings.reloadTime = 0.6; // Seconds
        Settings.currentReloadTime = 0;
        Settings.maxBullets = 6; // Max bullets player can have loaded
        Settings.currentBullets = 6; // Players bullets loaded
        Settings.bulletsShot = 0; // How many times player shot a bullet
        Settings.bulletsHit = 0; // How many birds player hit
        // Birds
        Settings.birdSpawnTime = 400;
        Settings.birdSize = 0.1;
        Settings.maxBirdsSpawned = 6; // Max birds can be on the screen at the same time
        Settings.maxBirdsSpeed = 0.5;
        Settings.minBirdsSpeed = 0.1;
        Settings.maxBirdsVerticalSpeed = 0.08;
        Settings.minBirdsVerticalSpeed = 0.03;
        Settings.maxBirdsVerticalMove = Settings.height * 0.06;
        Settings.minBirdsVerticalMove = Settings.height * 0.03;
        Settings.birdRandomizedSize = 0.5; // Adds to random size (0.0 - 1.0) this number

        // Clear birds (Because birds is a static field)
        BirdHandler.clearBirds();
    }

    // Save settings and launch game
    void saveSettings(String username, boolean defaultCursor, int resW, int resH){
        if(username.length() < 4) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Username should contain at least 4 cahracters", ButtonType.OK);
            alert.show();
            return;
        }
        if(resW < 1024 || resH < 576) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Minimal resolution: 1024 x 576", ButtonType.OK);
            alert.show();
            return;
        }

        // Check for full screen
        if(Settings.resolutionX == resW && Settings.resolutionY == resH)
            Settings.fullScreenMode = true;

        // Save into settings
        Settings.username = username;
        Settings.defaultCursor = defaultCursor;
        Settings.width = resW;
        Settings.height = resH;

        // Call setup scenes
        setupMainMenu();
        setupGameScene();

        // Set default cursor type
        changeCursor(0);

        // Change scene to default
        changeScene(Main.mainMenu);
    }

    // Scene changer
    public void changeScene(Scene scene){
        Main.window.setScene(scene);
        centerScreen(scene);
    }

    // Cursor changer
    public static void changeCursor(int type){

        // Basic cursor
        if(type == 0){
            if(Settings.defaultCursor){
                Main.launchMenu.setCursor(Cursor.DEFAULT);
                Main.mainMenu.setCursor(Cursor.DEFAULT);
                Main.gameScene.setCursor(Cursor.DEFAULT);
            }else{
                Main.launchMenu.setCursor(ic0);
                Main.mainMenu.setCursor(ic0);
                Main.gameScene.setCursor(ic0);
            }
        }
        // Interactable UI cursor
        else if(type == 1){
            if(Settings.defaultCursor){
                Main.launchMenu.setCursor(Cursor.HAND);
                Main.mainMenu.setCursor(Cursor.HAND);
                Main.gameScene.setCursor(Cursor.HAND);
            }else{
                Main.launchMenu.setCursor(ic1);
                Main.mainMenu.setCursor(ic1);
                Main.gameScene.setCursor(ic1);
            }
        }

        else if(type == 2){
            if(Settings.defaultCursor){
                Main.launchMenu.setCursor(Cursor.CLOSED_HAND);
                Main.mainMenu.setCursor(Cursor.CLOSED_HAND);
                Main.gameScene.setCursor(Cursor.CLOSED_HAND);
            }else{
                Main.launchMenu.setCursor(ic2);
                Main.mainMenu.setCursor(ic2);
                Main.gameScene.setCursor(ic2);
            }
        }
    }

    // Cursor interact
    public static void onMouseUI(Node n){

        n.setOnMouseEntered(e->{
            changeCursor(1);
            audio.uiEffect.setVolume(audio.uiEffect.getVolume() / 5);
            audio.playUiEffects();
            audio.uiEffect.setVolume(Settings.soundVolume);
        });

        n.setOnMousePressed(e->{
            changeCursor(2);
            audio.playUiEffects();
        });

        n.setOnMouseExited(e->changeCursor(0));
        n.setOnMouseReleased(e->changeCursor(1));
    }
    public static void onMouseEnemy(Node n){

        n.setOnMouseEntered(e->{
            changeCursor(2);
        });

        n.setOnMouseExited(e->{
            changeCursor(0);
        });
    }
    public static void onMouseEnemyDie(Node n){
        changeCursor(0);

        n.setOnMouseEntered(e->{
            changeCursor(0);
        });

        n.setOnMouseExited(e->{
            changeCursor(0);
        });
    }

    // center screen
    public void centerScreen(Scene s){

        // Check for gullScreen mode
        if(Settings.fullScreenMode){
            Main.window.setFullScreen(true);
            return;
        }

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        Main.window.setX((primScreenBounds.getWidth() - s.getWidth()) / 2);
        Main.window.setY((primScreenBounds.getHeight() - s.getHeight()) / 2);
    }

    // Start game
    public void startGame(){
        // Prepare game scene
        setupGameScene();

        // change scene
        changeScene(Main.gameScene);
    }

    // Shot handler
    public static void shot(MouseEvent event) {
        if(Settings.isPaused)
            return;

        if(Settings.reloading)
            Settings.currentReloadTime = 0;

        if(Settings.currentBullets -1 < 0){
            audio.playEmpty();
            reload();
        }
        else{
            if( (event.getTarget()).toString().contains("Bird") ){
                audio.playShot();
                Settings.bulletsShot++;
            }else{
                audio.playShot();
                Settings.bulletsShot++;
                Settings.currentBullets--;
            }

        }
    }

    // Reloading handler
    public static void reload(){
        if(Settings.currentBullets >= Settings.maxBullets)
            return;

        Settings.reloading = true;

        // Reload
        tReload.setCycleCount(Animation.INDEFINITE);
        tReload.play();
    }
    static void doReloading(){
        if(Settings.isPaused)
            return;

        // Interrupted reloading
        if(!Settings.reloading) {
            Settings.currentReloadTime = 0;
            audio.isPlayingReloading = false;
            return;
        }

        // Check for full magazine
        if(Settings.currentBullets >= Settings.maxBullets)
            tReload.stop();

        // Finish reloading
        if(Settings.currentReloadTime + 0.001 >= Settings.reloadTime){
            Settings.currentBullets++;
            Settings.currentReloadTime = 0;
            audio.isPlayingReloading = false;
            return;
        }

        // check timing for sound effect (Reload effect is 0.6 sec long)
        if(!audio.isPlayingReloading){
            audio.playReload();
            audio.isPlayingReloading = true;
        }

        // Time elapsed
        Settings.currentReloadTime += 0.001;
    }
}
    // <editor-fold desc="">
    // </editor-fold>