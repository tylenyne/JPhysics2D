package com.COMPFIZ.underscore.games;

import com.COMPFIZ.core.*;
import com.COMPFIZ.core.actors.Gravitor;
import com.COMPFIZ.core.mixins.Constants;
import com.COMPFIZ.core.mixins.Parser;
import com.COMPFIZ.core.mixins.TriConsumer;
import com.COMPFIZ.core.mixins.maths.Maths;
import com.COMPFIZ.core.models.Entity;
import com.COMPFIZ.core.attributes.Physics;
import com.COMPFIZ.core.models.StillModel;
import com.COMPFIZ.core.shaders.Shaders;
import com.COMPFIZ.core.shaders.myShader;
import com.COMPFIZ.underscore.Launcher;
import org.joml.Math;
import org.joml.Vector3d;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;

import java.io.IOException;
import java.nio.file.*;

public class Orrey implements Disc{

    private final WindowManager winMan;
    private final Loader loader;
    private final GPURenderer renderer;

    private Entity[] entities;
    private int updIndex = 0;
    private Gravitor gravity;
    private Camera cam;


    private TriConsumer<Float, Entity, Entity[]>[] funcl;
    private Path PATH;
    private String FILENAME = "src/data.txt";

    private Shaders[] shaders;

    float r = .9f, g = .7f, b = 1, a = .8f;


    public Orrey() throws Exception{
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
        PATH = Paths.get(FILENAME);
        if(Files.exists(PATH)){
            Files.delete(PATH);
        }
        Files.createFile(PATH);


        Constants.field = Constants.orbitfield;
        cam = new Camera(Constants.orbitfield);

        StillModel blockModel = loader.loadOBJ("/OBJs/circle.obj");//Keep forgetting you have to add a slash prefixing path
        StillModel blockModel2 = loader.loadOBJ("/OBJs/circle.obj");
        StillModel blockModel3 = loader.loadOBJ("/OBJs/circle.obj");
        StillModel blockModel4 = loader.loadOBJ("/OBJs/circle.obj");
        StillModel blockModel5 = loader.loadOBJ("/OBJs/circle.obj");
        StillModel blockModel6 = loader.loadOBJ("/OBJs/circle.obj");
        StillModel blockModel7 = loader.loadOBJ("/OBJs/circle.obj");
        StillModel blockModel8 = loader.loadOBJ("/OBJs/circle.obj");
        StillModel blockModel9 = loader.loadOBJ("/OBJs/circle.obj");




        Entity Earth = new Entity(blockModel);
        Entity Sun = new Entity(blockModel2);
        Entity Venus = new Entity(blockModel3);
        Entity Mars = new Entity(blockModel4);
        Entity Mercury = new Entity(blockModel5);
        Entity Jupiter = new Entity(blockModel6);
        Entity Saturn = new Entity(blockModel7);
        Entity Neptune = new Entity(blockModel8);
        Entity Uranus = new Entity(blockModel9);
        //Entity Moon = new Entity(blockModel3);


        Earth.color.set(.2f,.8f,.89f);
        Earth.scale.set(1/80d);
        Earth.physics = new Physics(5.97219e24d, 200, "Earth");
        Parser.load(Earth);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_earth.txt");
        System.out.println(Earth.position);
        System.out.println(((Physics)Earth.physics).v);


        entities = new Entity[9];
        funcl = new TriConsumer[9];

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
        //entities[2] = Moon;

        //Second mass
        Sun.color.set(.85f, .7f, .2f);
        Sun.scale.set(1/80d);
        Sun.physics = new Physics(1988410e24d, 400, "Sun");
        Parser.load(Sun);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_sun.txt");
        System.out.println(Sun.position);
        System.out.println(((Physics)Sun.physics).v);

        //Moon.color.set(.69f, .7f, .7f);
        //Moon.scale.set(1/80f);
        //Moon.position.set(-4.1052e5, 100000, 0);
        //Moon.physics = new Physics(10000f, 400, "Moon");
        //((Physics)Moon.physics).v.set(Maths.triangulate(47000f, 45));

        Venus.color.set(.5f, .5f, .15f);
        Venus.scale.set(1/80d);
        Venus.physics = new Physics(48.685e23d, 20, "Venus");
        Parser.load(Venus);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_venus.txt");

        Mars.color.set(.85f, .2f, 0f);
        Mars.scale.set(1/80d);
        Mars.physics = new Physics(6.4171e23d, 20, "Mars");
        Parser.load(Mars);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_mars.txt");

        Mercury.color.set(.5f, 0f, .1f);
        Mercury.scale.set(1/80d);
        Mercury.physics = new Physics(3.302e23, 20, "Mercury");
        Parser.load(Mercury);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_mercury.txt");

        Jupiter.color.set(.5, .3, .3);
        Jupiter.scale.set(1/80d);
        Jupiter.physics = new Physics(189818722e19d, 20, "Jupiter");
        Parser.load(Jupiter);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_jupiter.txt");

        Neptune.color.set(.2, .3, .5);
        Neptune.scale.set(1/80d);
        Neptune.physics = new Physics(102.409e24, 20, "Neptune");
        Parser.load(Neptune);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_neptune.txt");

        Saturn.color.set(.5, .5, .3);
        Saturn.scale.set(1/80d);
        Saturn.physics = new Physics(5.6834e26d, 20, "Saturn");
        Parser.load(Saturn);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_saturn.txt");

        Uranus.color.set(.1, 0, .6);
        Uranus.scale.set(1/80d);
        Uranus.physics = new Physics(86.813e24, 20, "Uranus");
        Parser.load(Uranus);
        Parser.parse("2024-Nov-26 00:00:00.0000 TDB", "/JPL/_youranus.txt");
    }

    @Override
    public void input() {

        if(winMan.isKeyPressed(GLFW.GLFW_KEY_SPACE) && entities[8] == null && !EventHandler.eventStream[0]){
            StillModel blockmodel = loader.loadOBJ("/OBJs/circle.obj");
            entities[updIndex] = new Entity(blockmodel);
            entities[updIndex].scale.set(1/80f);
            entities[updIndex].position.set(-40000000d, 0, 0);
            entities[updIndex].physics = new Physics(1d, .125f);
            ((Physics)entities[updIndex].physics).v.set(Maths.triangulate(00f, 90));
            entities[updIndex].color.set(.3, 0, .5);
            entities[updIndex].physics.name = "Extra";

            funcl[updIndex] = gravity::update;
            EventHandler.eventStream[0] = true;
            updIndex++;
        }
    }

    @Override
    public void update(float interval) {//Add Event-Registry
        cam.input(winMan, entities);

        System.out.println("FRAME: " + EventHandler.allFrames);
        System.out.println("********************************");
        for (int f = 0; f < funcl.length; f++){//RealTime
            if(funcl[f] == null) continue;
            funcl[f].accept(interval, entities[f], entities);
        }
        System.out.println("error code " + GL30.glGetError());
        for (int f = 0; f < funcl.length; f++){//RealTime
            if(funcl[f] == null) continue;
            entities[f].position.add(((Physics) entities[f].physics).v.mul(interval, new Vector3d()));
            System.out.println(((Physics) entities[f].physics).v + " << " + entities[f].physics.name);
            System.out.println((entities[f].position) + " ## " + entities[f].physics.name);
        }

/**
 if(entities[0].getPosition().y < height){
 EngineManager.isRunning = false;
 System.out.println(EngineManager.totalTime);
 System.out.println(entities[0].getPosition().y);
 }*/

        try {
            Files.writeString(PATH, (entities[0].getPosition().y) + "\n", StandardOpenOption.APPEND);//Make it so it writes to a file and a different program can filter through answers
        }
        catch(IOException e){
            System.out.println(e);
        }

        //Try to save super bad class
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
