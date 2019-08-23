package com.flappybird.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.flappybird.common.InputMethods;
import com.flappybird.config.GameConfig;
import com.flappybird.entity.Planet;
import com.flappybird.entity.Star;

import org.omg.CORBA.MARSHAL;

public class GameController {

    private static final Logger log = new Logger(GameController.class.getName(), Logger.DEBUG);


    private Array<Star> stars = new Array<>();
    private Array<Planet> planets = new Array<>();
    private int planetCount = 0;

    private float dragX;
    private float dragY;
    private boolean drag=false;


    private boolean gameOver = false;

    private boolean touchDown = false;


    private boolean touchUp = false;



    public GameController(){
        init();
    }

    private void init(){
        for(int i =0 ; i<GameConfig.NUMBER_OF_STARS ; i++){
            stars.add(new Star());
        }
        stars.get(0).setPosition(10,8);
//        stars.get(1).setPosition(18,14);
//        stars.get(2).setPosition(7,2);
//        stars.get(3).setPosition(13 , 7);

        for(int i =0 ; i<GameConfig.NUMBER_OF_PLANETS ; i++){
            planets.add(new Planet());
        }

    }

    public void update(float delta){
        if(isgameOver()){
            playAgain();
        }else {
            if (drag) {
                planets.get(planetCount).setPosition(dragX, dragY);
            }
            updatePlanets(delta);
            updateAcceleration();
            checkCollision(delta);
            checkBound();
        }
    }

    public Array<Star> getStars() {
        return stars;
    }

    public Array<Planet> getPlanets() {
        return planets;
    }

    public int getPlanetCount() {
        return planetCount;
    }

    public void setPlanetCount(int planetCount) {
        this.planetCount = planetCount;
    }

    public void setDragX(float dragX) {
        this.dragX = dragX;
    }

    public void setDragY(float dragY) {
        this.dragY = dragY;
    }

    public void setDrag(boolean drag) {
        this.drag = drag;
    }


    public void setTouchDown(boolean touchDown) {
        this.touchDown = touchDown;
    }


    public void setTouchUp(boolean touchUp) {
        this.touchUp = touchUp;
    }

    private boolean isgameOver(){
        int inBoundCounter =0;
        for (Planet planet : planets){
            if(planet.getInBound()){
                inBoundCounter++;
            }
        }
        log.debug(inBoundCounter + " ");
        if(planetCount==GameConfig.NUMBER_OF_PLANETS-1 && inBoundCounter<GameConfig.MIN_PLANETS){
            return true;
        }
        return false;
    }

    private void updatePlanets(float delta){
        for(int i =0 ; i <planetCount; i++){
            planets.get(i).update(delta);
//            log.debug("update called" + planets.size);
        }
        if(!drag){
            planets.get(planetCount).update(delta);
//            log.debug("update called");
        }
    }

    private void updateAcceleration(){
        for(int i = 0 ; i <planets.size ; i++){
            planets.get(i).updateAcceleration(planets , stars);
        }
    }

    private void checkCollision(float delta){
        for(int i = 0 ; i <planetCount ; i++){
            for(int j =0 ; j<stars.size ; j++){
                Boolean collision = planets.get(i).checkCollisionStar(stars.get(j));
                if(collision){
                    stars.get(j).setMass(stars.get(j).getMass() + planets.get(i).getMass());
                    planets.get(i).setMass(0);
                    planets.get(i).setPosition(stars.get(j).getX(),stars.get(j).getY());
                    planets.get(i).setPlanetRadius(0);
                    planets.get(i).setInBound(false);
                }
            }

            for (int j = 0 ; j<planetCount ; j++) {
                Planet planet = planets.get(i);
                Planet otherPlanet = planets.get(j);
                if (planet.getX() != otherPlanet.getX() && planet.getY() != otherPlanet.getY() && planet.getMass()!=0 && otherPlanet.getMass()!=0) {
                    Boolean collision = planet.checkCollisionPlanet(otherPlanet);
                    if (collision) {
//                        float totalMass = planet.getMass() + otherPlanet.getMass();
//                        float totalMomentumX = planet.getMass()*planet.get

                    }
                }
            }
        }
        if(!drag){
            for(int j =0 ; j<stars.size ; j++) {
                Boolean collision = planets.get(planetCount).checkCollisionStar(stars.get(j));
                if (collision) {
                    stars.get(j).setMass(stars.get(j).getMass() + planets.get(planetCount).getMass());
                    planets.get(planetCount).setMass(0);
                    planets.get(planetCount).setPosition(Float.MAX_VALUE, Float.MAX_VALUE);
                    planets.get(planetCount).setPlanetRadius(0);
                    planets.get(planetCount).setInBound(false);
                }
            }
            for (int j = 0 ; j<planetCount ; j++) {
                Planet planet = planets.get(planetCount);
                Planet otherPlanet = planets.get(j);
                if (planet.getX() != otherPlanet.getX() && planet.getY() != otherPlanet.getY()) {
                    Boolean collision = planet.checkCollisionPlanet(otherPlanet);
                    if (collision) {
//                        float totalMass = planet.getMass() + otherPlanet.getMass();
//                        float totalMomentumX = planet.getMass()*planet.ge

                    }
                }
            }

        }
    }

    private void checkBound(){
        for (Planet planet : planets){
            if(planet.getX()>GameConfig.MAX_BOUND || planet.getY()>GameConfig.MAX_BOUND){
                planet.setInBound(false);
            }
        }
    }

    private void playAgain(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){

        }
    }
}

