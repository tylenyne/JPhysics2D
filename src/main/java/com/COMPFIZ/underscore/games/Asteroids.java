package com.COMPFIZ.underscore.games;

import com.COMPFIZ.core.*;
import com.COMPFIZ.core.actors.Gravitor;
import com.COMPFIZ.core.actors.Thrower;
import com.COMPFIZ.core.attributes.Physics;
import com.COMPFIZ.core.mixins.Constants;
import com.COMPFIZ.core.mixins.Parser;
import com.COMPFIZ.core.mixins.TriFunction;
import com.COMPFIZ.core.models.Entity;
import com.COMPFIZ.core.models.StillModel;
import com.COMPFIZ.core.shaders.Shaders;
import com.COMPFIZ.core.shaders.myShader;
import com.COMPFIZ.underscore.Launcher;
import org.joml.Math;
import org.joml.Vector3d;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;

import static com.COMPFIZ.core.GPURenderer.setClearColor;


public class Asteroids implements Disc{
    private final WindowManager winMan;
    private final Loader loader;
    private final GPURenderer renderer;

    private Entity[] entities;
    private int updIndex = 0;
    private Gravitor gravity;
    private Camera cam;
    private Vector3d direction;
    private double directionb4 = 0.0;
    private double vdifference = 0.0;
    private double max = 0.0;
    private double min = 0.0;
    private double save;
    private double save2;
    private boolean pressed = false;
    private double atm = 0.0;
    private double speed;

    private TriFunction<Double, Entity, Entity[], Vector3d>[] funcl;
    private Thrower thrower;
    private Physics getter;
    private Entity Sun;
    private Entity Mercury;
    private Entity Venus;
    private Entity Earth;
    private Entity Mars;
    private Entity Jupiter;
    private Entity Saturn;
    private Entity Uranus;
    private Entity Neptune;
    private Entity Asteroid;
    private Entity target;

    private Shaders[] shaders;
    float r = .9f, g = .7f, b = 1, a = .8f;

    public Asteroids() throws Exception{
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
        thrower = new Thrower();

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

        Earth = new Entity(blockModel);
        Sun = new Entity(blockModel2);
        Venus = new Entity(blockModel3);
        Mars = new Entity(blockModel4);
        Mercury = new Entity(blockModel5);
        Jupiter = new Entity(blockModel6);
        Saturn = new Entity(blockModel7);
        Neptune = new Entity(blockModel8);
        Uranus = new Entity(blockModel9);
        //Entity Moon = new Entity(blockModel3);
        Asteroid = new Entity(blockModel0);
        target = Earth;

        Earth.color.set(.2f,.8f,.89f);
        Earth.physics = new Physics(5.97219e24d, 6380, "Earth");
        Earth.scale.set(onekmeter * ((Physics)Earth.physics).r * 1000);
        Parser.load(Earth);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_earth.txt");

        entities = new Entity[10];
        funcl = new TriFunction[10];

        entities[0] = Sun;
        funcl[0] = gravity::updated;
        entities[1] = Mercury;
        funcl[1] = gravity::updated;
        entities[2] = Venus;
        funcl[2] = gravity::updated;
        entities[3] = Earth;
        funcl[3] = gravity::updated;
        entities[4] = Mars;
        funcl[4] = gravity::updated;
        entities[5] = Jupiter;
        funcl[5] = gravity::updated;
        entities[6] = Saturn;
        funcl[6] = gravity::updated;
        entities[7] = Uranus;
        funcl[7] = gravity::updated;
        entities[8] = Neptune;
        funcl[8] = gravity::updated;
        entities[9] = Asteroid;
        funcl[9] = gravity::updated;
        //entities[2] = Moon;

        Mars.color.set(.85f, .2f, 0f);
        Mars.physics = new Physics(6.4171e23d, 3396, "Mars");
        Mars.scale.set(onekmeter * ((Physics)Mars.physics).r * 1000);
        Parser.load(Mars);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_mars.txt");

        //Second mass
        Sun.color.set(.85f, .7f, .2f);
        Sun.physics = new Physics(1988410e24d, 700000, "Sun");
        Sun.scale.set(onekmeter * ((Physics)Sun.physics).r * 1000);
        Parser.load(Sun);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_sun.txt");

        //Moon.color.set(.69f, .7f, .7f);
        //Moon.scale.set(1/80f);
        //Moon.position.set(-4.1052e5, 100000, 0);
        //Moon.physics = new Physics(10000f, 400, "Moon");
        //((Physics)Moon.physics).v.set(Maths.triangulate(47000f, 45));

        Mercury.color.set(.5f, 0f, .1f);
        Mercury.physics = new Physics(3.302e23, 2440, "Mercury");
        Mercury.scale.set(onekmeter * ((Physics)Mercury.physics).r * 1000);
        Parser.load(Mercury);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_mercury.txt");

        Venus.color.set(.5f, .5f, .15f);
        Venus.physics = new Physics(48.685e23d, 6052, "Venus");
        Venus.scale.set(onekmeter * ((Physics)Venus.physics).r * 1000);
        Parser.load(Venus);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_venus.txt");

        Jupiter.color.set(.5, .3, .3);
        Jupiter.physics = new Physics(189818722e19d, 71492, "Jupiter");
        Jupiter.scale.set(onekmeter * ((Physics)Jupiter.physics).r * 1000);
        Parser.load(Jupiter);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_jupiter.txt");

        Saturn.color.set(.5, .5, .3);
        Saturn.physics = new Physics(5.6834e26d, 60268, "Saturn");
        Saturn.scale.set(onekmeter * ((Physics)Saturn.physics).r * 1000);
        Parser.load(Saturn);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_saturn.txt");

        Uranus.color.set(.1, 0, .6);
        Uranus.physics = new Physics(86.813e24, 25559, "Uranus");
        Uranus.scale.set(onekmeter * ((Physics)Uranus.physics).r * 1000);
        Parser.load(Uranus);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_youranus.txt");

        Neptune.color.set(.2, .3, .5);
        Neptune.physics = new Physics(102.409e24, 24764, "Neptune");
        Neptune.scale.set(onekmeter * ((Physics)Neptune.physics).r * 1000);
        Parser.load(Neptune);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_neptune.txt");

        Asteroid.color.set(.6, .6, .6);
        Asteroid.scale.set(target.scale.x/10);
        Asteroid.physics = new Physics(0, 10, "Asteroid", .5f);
    }

    @Override
    public void input(){

    }

    @Override
    public void update(float interval) {//Add Event-Registry
        cam.input(winMan, entities);
        if(winMan.isKeyPressed(GLFW.GLFW_KEY_ENTER) && !pressed){
            String txt = winMan.getTxtinput();
            System.out.println(txt.length());
            System.out.println(txt);
            ((Physics)Asteroid.physics).mass = 1000 * 12;
            pressed = true;
        }
        for (int f = 0; f < funcl.length; f++){//RealTime
            if(funcl[f] == null) continue;
            Vector3d force = funcl[f].apply((double)interval, entities[f], entities);

            double height = 10e10;
            if(entities[f] != target) height = Asteroid.position.sub(target.position, new Vector3d()).length() - 6371 * 1000;
            if(height < 1e7){
                EventHandler.dt = 1/120f;
            }
            if(height < (50) * 1000){
                //Speed Relative to Earth
                ((Physics) entities[f].physics).h = height;
                System.out.println(((Physics) Asteroid.physics).v.length() - ((Physics)Earth.physics).v.length());
                Vector3d drag = thrower.airdrag(interval, Asteroid, ((Physics)Earth.physics).v);
                atm = Math.max(drag.length(), atm);
                force.add(drag);
                if(height < 10000 && height > 9900){
                    speed = ((Physics)Asteroid.physics).v.length();
                }
            }
            if(((Physics)entities[f].physics).mass != 0) {
                force.div(((Physics) entities[f].physics).mass);//acc
                ((Physics) entities[f].physics).v.add(force.mul(interval));
            }
        }

        if(EventHandler.allFrames % 1200 == 1){
            System.out.println("error code " + GL30.glGetError());
        }
        if(!pressed){
            Vector3d target2Sun = target.position.sub(Sun.position, new Vector3d()).normalize().mul(200000e3);
            Asteroid.position.set(target.position.sub(target2Sun, new Vector3d()));
            ((Physics)Asteroid.physics).v.set(((Physics)target.physics).v);
        }
        //System.out.println((Asteroid.position.sub(Mercury.position, new Vector3d()).length()));
        for (int f = 0; f < funcl.length; f++) {//RealTime
            if (funcl[f] == null) continue;
            entities[f].position.add(((Physics) entities[f].physics).v.mul(interval, new Vector3d()));
        }
        //System.out.println(((Physics)Mercury.physics).v.length());
        //System.out.println(target.position.sub(Asteroid.position, new Vector3d()).length());//Distance from target
        if(Asteroid.position.sub(target.position, new Vector3d()).length() < 6371 * 1000){
            System.out.println(((Physics)Asteroid.physics).v.length() - ((Physics)target.physics).v.length());
            //Resetting
            ((Physics)Asteroid.physics).mass = 0;
            //Asteroid.position.set(target.position.add(2440 * 15 * 1000 * 2, 0, 0, new Vector3d()));
            Asteroid.position.set(target.position.sub(target.position.sub(Sun.position, new Vector3d()).normalize().mul(200000e3), new Vector3d()));
            pressed = false;
            winMan.resettxt();
            setClearColor(0, 0, 0, a);
        }
    }



    @Override
    public void render() {
        if(winMan.isResized()){
            GL30.glViewport(0, 0, winMan.getWidth(), winMan.getHeight());
            winMan.setResized(true);
        }
        //setClearColor(0,0,0, a);
        renderer.prepare();
        renderer.render(entities, cam, shaders[0]);//Unoptimized I think//--
    }

    @Override
    public void cleanup() {
        loader.cleanup();//Cleans Up all the vao-data
        renderer.cleanup();
        System.out.println("Ran For " + EventHandler.totalTime + " Seconds");
        System.out.println(speed);
        System.out.println(atm);
    }

    public float randomSpeed(){//.05 to .01
        return (float)((int)(Math.random()+.5))/100;
    }

    public GPURenderer getRenderer() {
        return renderer;
    }

}
