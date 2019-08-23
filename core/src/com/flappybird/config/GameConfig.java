package com.flappybird.config;

public class GameConfig {

    public static final float WIDTH= 1000.0f;
    public static final float HEIGHT= 800.0f;

    public static final float HUD_WIDTH = 1000.0f;
    public static final float HUD_HEIGHT = 800.0f;

    public static final float WORLD_WIDTH= 20.0f;
    public static final float WORLD_HEIGHT= 16.0f;

    public static final float NUMBER_OF_PLANETS = 9;
    public static final float NUMBER_OF_STARS = 1;

    public static final float PLANET_RADIUS = 0.2f;
    public static final float STARS_RADIUS = 0.5f;

    public static final float VELOCITY_CONSTANT = 0.03f;
    public static final float MAX_VELOCITY = 2f;

    public static final int STAR_MASS = 100;
    public static final int PLANET_MASS = 1;

    public static final float ACCELERATION_CONSTANT = 0.01f;

    public static final float RADIUS_OF_INFLUENCE = 10f;

    public static final float MAX_BOUND = 100f;

    public static final int  MIN_PLANETS = 3;




    private GameConfig() {}
}
