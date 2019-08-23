package com.flappybird.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.flappybird.config.GameConfig;

public class Star {
    private float mass = MathUtils.random(50 , 150);
    private float x;
    private float y;
    private Circle bounds;

    private static final float starRadius = GameConfig.STARS_RADIUS;
    private static final float SIZE = 2*starRadius;

    public Star(){
        bounds = new Circle(x,y,starRadius);
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

    public float getMass() {
        return mass;
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

    public void setMass(float mass) {
        this.mass = mass;
    }

    private void updateBounds() {
        bounds.setPosition(x, y);
    }
}
