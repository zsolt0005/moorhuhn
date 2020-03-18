package sample;

public class Settings {

    // Monitor
    public static double resolutionX = 0;
    public static double resolutionY = 0;

    // Resolution (16:9 only)
    public static int width = 1280;
    public static int height = 720;
    public static int launchWidth = 500;
    public static int launchHeight = 700;
    public static boolean fullScreenMode = false;

    // Text settings
    public static int fontSize = 8;
    public static double fontScale = 0.02;

    // Cursor settings
    public static boolean defaultCursor = false;

    // User
    public static String username = "";

    // Audio
    public static boolean music = true;
    public static boolean sound = true;
    public static double soundVolume = 0.4;
    public static double musicVolume = 0.1;

    // INGAME UI
    public static double uiHeight = 0.1; // 0.1 = 10% of screen height
    public static int mainMenuBirdsCount = 6;

    // GAME (Every time needs to be set to default -> as is below)
        // General
    public static boolean isPaused = false;
    public static int score = 0;
    public static int time = 0;
    public static int maxTime = 90;
        // Bullets
    public static  boolean reloading = false;
    public static double reloadTime = 0.6; // Seconds
    public static double currentReloadTime = 0;
    public static int maxBullets = 6; // Max bullets player can have loaded
    public static int currentBullets = 6; // Players bullets loaded
    public static int bulletsShot = 0; // How many times player shot a bullet
    public static int bulletsHit = 0; // How many birds player hit
        // Birds
    public static int birdSpawnTime = 400;
    public static double birdSize = 0.1;
    public static int maxBirdsSpawned = 6; // Max birds can be on the screen at the same time
    public static double maxBirdsSpeed = 0.5;
    public static double minBirdsSpeed = 0.1;
    public static double maxBirdsVerticalSpeed = 0.08;
    public static double minBirdsVerticalSpeed = 0.03;
    public static double maxBirdsVerticalMove = height * 0.06;
    public static double minBirdsVerticalMove = height * 0.03;
    public static double birdRandomizedSize = 0.5; // Adds to random size (0.0 - 1.0) this number
}
