package com.flappybird.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.flappybird.config.GameConfig;

public class Planet {
    private float mass = MathUtils.random(1,5);
    private float x;
    private float y;
    private Circle bounds;
    private float touchDownX;
    private float touchDownY;
    private float touchUpX;
    private float touchUpY;

    private float initialVelocityX =0;
    private float initialVelocityY =0;
    private float accelerationX =0;
    private float accelerationY =0;

    private Boolean inBound = true;

    private float velocityX;
    private float velocityY;


    private float planetRadius =GameConfig.PLANET_RADIUS;
    private float SIZE = 2*planetRadius;

    public Planet(){
        bounds = new Circle(x,y,planetRadius);
    }
    public void drawDebug(ShapeRenderer renderer) {
        renderer.circle(bounds.x, bounds.y, bounds.radius, 30);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateBounds();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Circle getBounds() {
        return bounds;
    }

    public float getWidth(){
        return SIZE;
    }
    public float getHeight(){
        return SIZE;
    }

    public float getMass() {
        return mass;
    }

    public void setPlanetRadius(float planetRadius) {
        this.planetRadius = planetRadius;

    }

    private void updateBounds() {
        bounds.setPosition(x, y);
    }

    public void setTouchDownX(float touchDownX) {
        this.touchDownX = touchDownX;
    }

    public void setTouchDownY(float touchDownY) {
        this.touchDownY = touchDownY;
    }

    public void setTouchUpX(float touchUpX) {
        this.touchUpX = touchUpX;
    }

    public void setTouchUpY(float touchUpY) {
        this.touchUpY = touchUpY;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public void setInitialVelocity(){
        initialVelocityX = GameConfig.VELOCITY_CONSTANT*(touchUpX - touchDownX);
        initialVelocityY = GameConfig.VELOCITY_CONSTANT*(touchUpY - touchDownY);
        initialVelocityX = MathUtils.clamp(initialVelocityX, -GameConfig.MAX_VELOCITY , GameConfig.MAX_VELOCITY);
        initialVelocityY = MathUtils.clamp(initialVelocityY, -GameConfig.MAX_VELOCITY, GameConfig.MAX_VELOCITY);
    }

    public void update(float delta){
        setPosition(x- initialVelocityX + (accelerationX*delta) , y- initialVelocityY + (accelerationY*delta));
    }

    public void updateAcceleration(Array<Planet> planets ,  Array<Star> stars){
        for(Star star :stars){
            float distance = (float) Math.sqrt(( Math.pow((double)(star.getX() - getX()),2d) + Math.pow((double)(star.getY() - getY()),2d)));
            if(distance<GameConfig.RADIUS_OF_INFLUENCE) {
                accelerationX += GameConfig.ACCELERATION_CONSTANT * star.getMass() * (star.getX() - getX()) / (Math.pow((double) distance, 3));
                accelerationY += GameConfig.ACCELERATION_CONSTANT * star.getMass() * (star.getY() - getY()) / (Math.pow((double) distance, 3));
            }
        }
        for (Planet planet: planets){
            if(planet.getX()!=getX() && planet.getY()!=getY()){
                float distance = (float) Math.sqrt(( Math.pow((double)(planet.getX() - getX()),2d) + Math.pow((double)(planet.getY() - getY()),2d)));
                if(distance<GameConfig.RADIUS_OF_INFLUENCE && planet.getMass()!=0) {
                    accelerationX += GameConfig.ACCELERATION_CONSTANT * planet.getMass() * (planet.getX() - getX()) / (Math.pow((double) distance, 3));
                    accelerationY += GameConfig.ACCELERATION_CONSTANT * planet.getMass() * (planet.getY() - getY()) / (Math.pow((double) distance, 3));
                }
            }
        }

    }

    public Boolean checkCollisionStar(Star star){
        Circle starBounds = star.getBounds();
        return Intersector.overlaps(starBounds , getBounds());
    }

    public Boolean checkCollisionPlanet(Planet planet){
        Circle planetBounds = planet.getBounds();
        return Intersector.overlaps(planetBounds , getBounds());
    }

//    private void updateVelocity(){
//        velocityX = velocityX - accelerationX
//    }


    public void setInBound(Boolean inBound) {
        this.inBound = inBound;
    }

    public Boolean getInBound() {
        return inBound;
    }
}
