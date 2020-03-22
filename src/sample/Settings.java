package sample;

import javafx.scene.Group;
import zsolt.master.gameUi;

public class Settings {

    // Singleton
    public static gameUi gameUiSingleton = null; // Singleton to prevent javaFX error with multiple assigned obj
    public static Group gPauseMenuSingleton = null; // Singleton to prevent javaFX error with multiple assigned obj
    public static Group gGameOverSingleton = null; // Singleton to prevent javaFX error with multiple assigned obj
    public static Group highScoreSingleton = null; // Singleton to prevent javaFX error with multiple assigned obj

    // Monitor
    public static double resolutionX = 0; // System resolution (width)
    public static double resolutionY = 0; // System resolution (height)

    // Resolution (16:9 only)
    public static int width = 1280; // Game width
    public static int height = 720; // Game height
    public static int launchWidth = 500; // Launch menu width (Launch menu is hardcoded)
    public static int launchHeight = 700; // Launch menu height (Launch menu is hardcoded)
    public static boolean fullScreenMode = false; // Fullscreen mode

    // Text settings
    public static int fontSize = 8; // Default font size
    public static double fontScale = 0.02; // Font scale with resolution

    // Cursor settings
    public static boolean defaultCursor = false; // Use default cursor

    // User
    public static String username = ""; // Username

    // Audio
    public static boolean music = true; // Background music ENABLED
    public static boolean sound = true; // FX ENABLED
    public static double soundVolume = 0.4; // FX volume
    public static double musicVolume = 0.1; // Background music volume

    // INGAME UI
    public static double uiHeight = 0.1; // 0.1 = 10% of screen height
    public static int mainMenuBirdsCount = 6; // Main menu bird count

    // Highscore UI
    public static int tooltipShowDelay = 300; // In milliseconds

    // GAME (Every time needs to be set to default -> as is below)
        // General
    public static boolean isPaused = false; // Is the game paused
    public static boolean isGameOver = false; // Is the game over
    public static int score = 0; // Score
    public static int time = 0; // Time elapsed
    public static int maxTime = 90; // Max game time
        // Bullets
    public static  boolean reloading = false; // Is the player reloading
    public static double reloadTime = 0.6; // Seconds
    public static double currentReloadTime = 0; // Current reload state time
    public static int maxBullets = 6; // Max bullets player can have loaded
    public static int currentBullets = 6; // Players bullets loaded
    public static int bulletsShot = 0; // How many times player shot a bullet
    public static int bulletsHit = 0; // How many birds player hit
        // Birds
    public static int birdSpawnTime = 400; // Birds spawn time
    public static double birdSize = 0.1; // birds scale for graphics (Original bird graphics resolution is huge)
    public static int maxBirdsSpawned = 6; // Max birds can be on the screen at the same time
    public static double maxBirdsSpeed = 0.5; // Max speed birds can get on spawn
    public static double minBirdsSpeed = 0.1; // Min speed birds can get on spawn
    public static double maxBirdsVerticalSpeed = 0.08; // Max vertical speed birds can get on spawn
    public static double minBirdsVerticalSpeed = 0.03; // Min vertical speed birds can get on spawn
    public static double maxBirdsVerticalMove = height * 0.06; // Max vertical movement birds can get on spawn
    public static double minBirdsVerticalMove = height * 0.03; // Min vertical movement birds can get on spawn
    public static double birdRandomizedSize = 0.5; // Adds to random size (0.0 - 1.0) this number
}
