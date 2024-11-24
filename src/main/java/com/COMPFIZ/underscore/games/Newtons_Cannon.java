package com.COMPFIZ.underscore.games;

import com.COMPFIZ.core.*;
import com.COMPFIZ.core.actors.Gravitor;
import com.COMPFIZ.core.attributes.Physics;
import com.COMPFIZ.core.mixins.Constants;
import com.COMPFIZ.core.mixins.Parser;
import com.COMPFIZ.core.mixins.TriConsumer;
import com.COMPFIZ.core.models.Entity;
import com.COMPFIZ.core.models.StillModel;
import com.COMPFIZ.core.shaders.Shaders;
import com.COMPFIZ.core.shaders.myShader;
import com.COMPFIZ.underscore.Launcher;
import org.joml.Math;
import org.joml.Vector3d;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;

public class Newtons_Cannon implements Disc{

    private final WindowManager winMan;
    private final Loader loader;
    private final GPURenderer renderer;

    private Entity[] entities;
    private int updIndex = 0;
    private Gravitor gravity;
    private Camera cam;
    private boolean pressed = false;
    private double scale = 1;


    private TriConsumer<Float, Entity, Entity[]>[] funcl;
    private Entity Sun;
    private Entity Mercury;
    private Entity Venus;
    private Entity Earth;
    private Entity Moon;
    private Entity Mars;
    private Entity Jupiter;
    private Entity Saturn;
    private Entity Uranus;
    private Entity Neptune;
    private Entity CannonBall;
    private Entity target;

    private Shaders[] shaders;

    float r = .9f, g = .7f, b = 1, a = .8f;


    public Newtons_Cannon() throws Exception{
        winMan = Launcher.getWinMan();
        loader = Launcher.getLoader();
        renderer = new GPURenderer();
    }

    @Override
    public void init() throws Exception{
        shaders = new Shaders[1];
        shaders[0] = new myShader();
        renderer.init(shaders);
        //maximum/minimum
        gravity = new Gravitor();
        target = CannonBall;

        Constants.field = Constants.orbitfield;
        cam = new Camera(1/Constants.orbitfield);
        double onekmeter = Constants.field;

        StillModel blockModel = loader.loadOBJ("/OBJs/circle.obj");//Keep forgetting you have to add a slash prefixing path
        StillModel blockModel2 = loader.loadOBJ("/OBJs/circle.obj");
        StillModel blockModel3 = loader.loadOBJ("/OBJs/circle.obj");
        StillModel blockModel4 = loader.loadOBJ("/OBJs/circle.obj");
        StillModel blockModel5 = loader.loadOBJ("/OBJs/circle.obj");
        StillModel blockModel6 = loader.loadOBJ("/OBJs/circle.obj");
        StillModel blockModel7 = loader.loadOBJ("/OBJs/circle.obj");
        StillModel blockModel8 = loader.loadOBJ("/OBJs/circle.obj");
        StillModel blockModel9 = loader.loadOBJ("/OBJs/circle.obj");
        StillModel blockModel0 = loader.loadOBJ("/OBJs/circle.obj");
        StillModel blockModel1 = loader.loadOBJ("/OBJs/circle.obj");

        Earth = new Entity(blockModel);
        Moon = new Entity(blockModel1);
        Sun = new Entity(blockModel2);
        Venus = new Entity(blockModel3);
        Mars = new Entity(blockModel4);
        Mercury = new Entity(blockModel5);
        Jupiter = new Entity(blockModel6);
        Saturn = new Entity(blockModel7);
        Neptune = new Entity(blockModel8);
        Uranus = new Entity(blockModel9);
        CannonBall = new Entity(blockModel0);

        Earth.color.set(.2f,.8f,.89f);
        Earth.physics = new Physics(5.97219e24d, 6380, "Earth");
        Earth.scale.set(onekmeter * ((Physics)Earth.physics).r * 1000 * scale);
        Parser.load(Earth);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_earth.txt");

        Moon.color.set(.7, .7, .7);
        Moon.physics = new Physics(7.349e22, 1737.5f, "Moon");
        Moon.scale.set(onekmeter * ((Physics)Moon.physics).r * 1000 * scale);
        Parser.load(Moon);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_moon.txt");

        entities = new Entity[11];
        funcl = new TriConsumer[11];

        entities[0] = Sun;
        funcl[0] = gravity::update;
        entities[1] = Mercury;
        funcl[1] = gravity::update;
        entities[2] = Venus;
        funcl[2] = gravity::update;
        entities[3] = Earth;
        funcl[3] = gravity::update;
        entities[4] = Mars;
        funcl[4] = gravity::update;
        entities[5] = Jupiter;
        funcl[5] = gravity::update;
        entities[6] = Saturn;
        funcl[6] = gravity::update;
        entities[7] = Uranus;
        funcl[7] = gravity::update;
        entities[8] = Neptune;
        funcl[8] = gravity::update;
        entities[9] = Moon;
        funcl[9] = gravity::update;
        //entities[10] = CannonBall;
        //entities[2] = Moon;

        Mars.color.set(.85f, .2f, 0f);
        Mars.physics = new Physics(6.4171e23d, 3396, "Mars");
        Mars.scale.set(onekmeter * ((Physics)Mars.physics).r * scale);
        Parser.load(Mars);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_mars.txt");

        //Second mass
        Sun.color.set(.85f, .7f, .2f);
        Sun.physics = new Physics(1988410e24d, 700000, "Sun");
        Sun.scale.set(onekmeter * ((Physics)Sun.physics).r * 1000 * 10);//Only one not affected by scale
        Parser.load(Sun);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_sun.txt");

        Mercury.color.set(.5f, 0f, .1f);
        Mercury.physics = new Physics(3.302e23, 2440, "Mercury");
        Mercury.scale.set(onekmeter * ((Physics)Mercury.physics).r * 1000 * scale);
        Parser.load(Mercury);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_mercury.txt");

        Venus.color.set(.5f, .5f, .15f);
        Venus.physics = new Physics(48.685e23d, 6052, "Venus");
        Venus.scale.set(onekmeter * ((Physics)Venus.physics).r * 1000 * scale);
        Parser.load(Venus);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_venus.txt");

        Jupiter.color.set(.5, .3, .3);
        Jupiter.physics = new Physics(189818722e19d, 71492, "Jupiter");
        Jupiter.scale.set(onekmeter * ((Physics)Jupiter.physics).r * 1000 * scale);
        Parser.load(Jupiter);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_jupiter.txt");

        Saturn.color.set(.5, .5, .3);
        Saturn.physics = new Physics(5.6834e26d, 60268, "Saturn");
        Saturn.scale.set(onekmeter * ((Physics)Saturn.physics).r * 1000 * scale);
        Parser.load(Saturn);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_saturn.txt");

        Uranus.color.set(.1, 0, .6);
        Uranus.physics = new Physics(86.813e24, 25559, "Uranus");
        Uranus.scale.set(onekmeter * ((Physics)Uranus.physics).r * 1000 * scale);
        Parser.load(Uranus);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_youranus.txt");

        Neptune.color.set(.2, .3, .5);
        Neptune.physics = new Physics(102.409e24, 24764, "Neptune");
        Neptune.scale.set(onekmeter * ((Physics)Neptune.physics).r * 1000 * scale);
        Parser.load(Neptune);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_neptune.txt");

        CannonBall.color.set(1, 1, 1);
        CannonBall.physics = new Physics(1, 1, "CannonBall");
        CannonBall.scale.set(Moon.scale.x/5);
        ((Physics)CannonBall.physics).v.set(((Physics)Moon.physics).v.add(((Physics)Moon.physics).v.normalize(new Vector3d()).mul(2200), new Vector3d()));
        Vector3d vec2 = Moon.position.normalize(new Vector3d()).mul(5300 + (1737.5 * 1000));
        CannonBall.position.set(Moon.position.sub(vec2, new Vector3d()));
    }

    @Override
    public void input(){

    }

    @Override
    public void update(float interval) {//Add Event-Registry
        int hh = (int) (EventHandler.totalTime / 3.154e+7 * 8760);
        int alldays = hh/24;
        hh = hh % 24;
        if(winMan.isKeyPressed(GLFW.GLFW_KEY_ENTER)){
            entities[10] = CannonBall;
            funcl[10] = gravity::update;
            pressed = true;
        }
        System.out.println("FRAME: " + EventHandler.allFrames + "HOUR:" + hh + " | DAY:" + alldays);
        System.out.println("********************************");
        for (int f = 0; f < funcl.length; f++){//RealTime
            if(funcl[f] == null) continue;
            funcl[f].accept(interval, entities[f], entities);
        }
        System.out.println("error code " + GL30.glGetError());
        for (int f = 0; f < funcl.length; f++){//RealTime
            if(funcl[f] == null) continue;
            entities[f].position.add(((Physics) entities[f].physics).v.mul(interval, new Vector3d()));
        }
        if(!pressed) {
            ((Physics)CannonBall.physics).v.set(((Physics)Moon.physics).v.add(((Physics)Moon.physics).v.normalize(new Vector3d()).mul(2200), new Vector3d()));
            Vector3d vec2 = Moon.position.normalize(new Vector3d()).mul(5300 + (1737.5 * 1000));
            CannonBall.position.set(Moon.position.sub(vec2, new Vector3d()));
        }
        cam.input(winMan, entities);
        System.out.println(Moon.position.normalize(new Vector3d()).dot(CannonBall.position.normalize(new Vector3d())));
    }



    @Override
    public void render() {
        if(winMan.isResized()){
            GL30.glViewport(0, 0, winMan.getWidth(), winMan.getHeight());
            winMan.setResized(true);
        }
        renderer.setClearColor(0,0,0, a);
        renderer.prepare();
        renderer.render(entities, cam, shaders[0]);//Unoptimized I think//--

    }

    @Override
    public void cleanup() {
        loader.cleanup();//Cleans Up all the vao-data
        renderer.cleanup();
        System.out.println("Ran For " + EventHandler.totalTime + " Seconds");
    }

    public float randomSpeed(){//.05 to .01
        return (float)((int)(Math.random()+.5))/100;
    }

    public GPURenderer getRenderer() {
        return renderer;
    }
}
