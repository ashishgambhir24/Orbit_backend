package com.flappybird.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.flappybird.common.InputMethods;
import com.flappybird.config.GameConfig;
import com.flappybird.entity.Planet;
import com.flappybird.entity.Star;
import com.flappybird.util.GdxUtils;
import com.flappybird.util.ViewportUtils;


public class GameRenderer extends InputMethods implements Disposable {

    private static final Logger log = new Logger(GameController.class.getName(), Logger.DEBUG);

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

//    private OrthographicCamera hudCamera;
//    private Viewport hudViewport;
//
    private SpriteBatch batch;
//    private BitmapFont scoreFont;
//    private BitmapFont gameOverFont;
//    private BitmapFont smallFont;
//
//    private final GlyphLayout layout = new GlyphLayout();
//    private final GlyphLayout smallLayout = new GlyphLayout();
//
//    private Texture backgroundTexture;
//    private Texture playerTexture;
//
//    private DebugCameraController debugCameraController;
//
    private GameController controller;

    public GameRenderer(GameController controller) {
        this.controller = controller;
        init();
    }

    private void init(){
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH , GameConfig.WORLD_HEIGHT , camera);
        renderer= new ShapeRenderer();

        Gdx.input.setInputProcessor(this);


//        hudCamera = new OrthographicCamera();
//        hudViewport = new FitViewport(GameConfig.HUD_WIDTH , GameConfig.HUD_HEIGHT , hudCamera);
//        batch = new SpriteBatch();
//        scoreFont = new BitmapFont(Gdx.files.internal(AssetsPath.SCORE_FONT));
//        gameOverFont = new BitmapFont(Gdx.files.internal(AssetsPath.GAMEOVER_FONT));
//        smallFont = new BitmapFont(Gdx.files.internal(AssetsPath.GAMEOVER_FONT_SMALL));
//
//        backgroundTexture = new Texture(Gdx.files.internal(AssetsPath.BACKGROUND));
//        playerTexture = new Texture(Gdx.files.internal(AssetsPath.PLAYER_TEXTURE));
//
//        debugCameraController = new DebugCameraController();
//        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);
    }


    public void render(float delta){
//        debugCameraController.handleDebugInput(delta);
//        debugCameraController.applyTo(camera);


        GdxUtils.clearScreen();

        renderUI();

        renderDebug();
    }

    public void resize(int width , int height){
        viewport.update(width,height,true);
//        hudViewport.update(width,height,true);
        ViewportUtils.debugPixelPerUnit(viewport);
    }


    private void renderUI(){

    }

    private void renderDebug(){
        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();

        renderer.end();

        ViewportUtils.drawGrid(viewport,renderer);
    }

    private void drawDebug(){
        renderer.setColor(Color.RED);
        for (Planet planet: controller.getPlanets()){
            planet.drawDebug(renderer);
        }


        renderer.setColor(Color.YELLOW);
        for (Star star: controller.getStars()){
            star.drawDebug(renderer);
        }
    }

    @Override
    public void dispose() {
        renderer.dispose();

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        int planetCount = controller.getPlanetCount();
        Vector2 screenTouchDown = new Vector2(screenX, screenY);
        Vector2 worldTouchDown = viewport.unproject(new Vector2(screenTouchDown));
        controller.getPlanets().get(planetCount).setTouchDownX(worldTouchDown.x);
        controller.getPlanets().get(planetCount).setTouchDownY(worldTouchDown.y);
        controller.setTouchDown(true);
        controller.setDrag(true);
        controller.setTouchUp(false);

//        log.debug(worldTouchDown.x + "  "  + worldTouchDown.y);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        int planetCount = controller.getPlanetCount();
        Vector2 screenTouchUp = new Vector2(screenX, screenY);
        Vector2 worldTouchUp = viewport.unproject(new Vector2(screenTouchUp));
        controller.getPlanets().get(planetCount).setTouchUpX(worldTouchUp.x);
        controller.getPlanets().get(planetCount).setTouchUpY(worldTouchUp.y);
        controller.setTouchDown(false);
        controller.setDrag(false);
        controller.setTouchUp(true);
        controller.getPlanets().get(planetCount).setInitialVelocity();
        if(controller.getPlanetCount()<GameConfig.NUMBER_OF_PLANETS-1) {
            controller.setPlanetCount(controller.getPlanetCount() + 1);
        }
//        log.debug(worldTouchUp.x + "  " + worldTouchUp.y );
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 screenDragged = new Vector2(screenX , screenY);
        Vector2 worldDragged = viewport.unproject(new Vector2(screenDragged));
        controller.setDragX(worldDragged.x);
        controller.setDragY(worldDragged.y);
        return super.touchDragged(screenX, screenY, pointer);
    }
}
