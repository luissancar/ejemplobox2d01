package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by luissancar on 28/12/16.
 */

public class Box2DScreen extends BaseScreen {

    private World world;
    private Box2DDebugRenderer renderer;
    private OrthographicCamera camera; //cámara 2d;
    private Body objeto01Body;  // entedad de nuestro mundo, posición, velocidad, no tiene forma
    private Fixture objeto01Fixture; // forma del body




    public Box2DScreen(MyGdxGame game) {
        super(game);
    }

    @Override
    public void show() {
        world=new World(new Vector2(0,-10), true); // Gravedad puede ser negativa, positiva, izquierda derecha;
         // segundo parámetro: si no tenemos nada que simular lo ponemos false y ahorramos recursos

        //You generally will not use this in a released version of your game, but for testing purposes we will set it up now like so:
        renderer=new Box2DDebugRenderer();
        camera=new OrthographicCamera(32,18);
        BodyDef objeto01Def=createObjeto01Def();
        objeto01Body=world.createBody(objeto01Def);

        PolygonShape objeto01Shape=new PolygonShape();
        objeto01Shape.setAsBox(1,1); // en metros
        objeto01Fixture=objeto01Body.createFixture(objeto01Shape,1); //1= densidad
        objeto01Shape.dispose(); // no lo necesitamos



    }
    private BodyDef createObjeto01Def() {
        BodyDef defCuerpo=new BodyDef();
        defCuerpo.position.set(0,10);
        defCuerpo.type= BodyDef.BodyType.DynamicBody;
        return defCuerpo;
    }

    @Override
    public void dispose() {
        world.destroyBody(objeto01Body);
        world.dispose();
        renderer.dispose();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    /*
    To update our simulation we need to tell our world to step. Stepping basically updates the world objects through time.

     The first argument is the time-step, or the amount of time you want your world to simulate. In most cases you want this to be a fixed time step. libgdx recommends using either 1/45f (which is 1/45th of a second) or 1/300f (1/300th of a second).

     The other two arguments are velocityIterations and positionIterations. For now we will leave these at 6 and 2,
    * */
        //world.step(1/60f, 6, 2);
        world.step(delta,6,2);  // con estos valores funciona bien
        camera.update();
        renderer.render(world,camera.combined);  // matriz de proyección
    }
}
