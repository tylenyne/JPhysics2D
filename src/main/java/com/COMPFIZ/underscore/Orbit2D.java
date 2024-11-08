package com.COMPFIZ.underscore;

import com.COMPFIZ.core.*;
import com.COMPFIZ.core.actors.Gravitor;
import com.COMPFIZ.core.mixins.Constants;
import com.COMPFIZ.core.mixins.TriConsumer;
import com.COMPFIZ.core.mixins.maths.Maths;
import com.COMPFIZ.core.models.Entity;
import com.COMPFIZ.core.models.Physics;
import com.COMPFIZ.core.models.StillModel;
import com.COMPFIZ.core.shaders.Shaders;
import com.COMPFIZ.core.shaders.myShader;
import org.joml.Math;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;

import java.io.IOException;
import java.nio.file.*;

public class Orbit2D implements Disc {

    private final WindowManager winMan;
    private final Loader loader;
    private final GPURenderer renderer;

    private Entity[] entities;
    private int updIndex = 0;
    private Gravitor gravity;

    private TriConsumer<Float, Entity, Entity[]>[] funcl;
    private Path PATH;
    private String FILENAME = "src/data.txt";

    private Shaders[] shaders;

    float r = .9f, g = .7f, b = 1, a = .8f;


    public Orbit2D() throws Exception{
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

        float[] blockPoints = Constants.block;
        int[] blockindices = new int[]{0,1,2,3,4,5};

        StillModel blockModel = loader.loadOBJ("/OBJs/circle.obj");//Keep forgetting you have to add a slash prefixing path
        StillModel blockModel2 = loader.loadOBJ("/OBJs/circle.obj");
        StillModel blockModel3 = loader.loadOBJ("/OBJs/circle.obj");




        Entity Earth = new Entity(blockModel);
        Entity Sun = new Entity(blockModel2);
        Entity Moon = new Entity(blockModel3);


        Earth.color.set(.2f,.8f,.89f);
        Earth.scale.set(1/80f);
        Earth.desc = new Physics(290000000000f, 200, "Earth");
        Earth.position.set(-4000000, 0, 0);
        ((Physics)Earth.desc).v.set(1000f, 5000000f, 0);


        entities = new Entity[9];
        funcl = new TriConsumer[9];

        entities[0] = Earth;
        funcl[0] = gravity::update;
        entities[1] = Sun;
        funcl[1] = gravity::update;
        entities[2] = Moon;
        funcl[2] = gravity::update;
        updIndex = 3;

        //Second mass
        Sun.color.set(.9f, .8f, .15f);
        Sun.scale.set(1/5f);
        Sun.position.set(0, 0,0 );
        Sun.desc = new Physics(100000000000000000f, 400, "Sun");

        Moon.color.set(.69f, .7f, .7f);
        Moon.scale.set(1/80f);
        Moon.position.set(-4000000, 100000, 0);
        Moon.desc = new Physics(10000f, 400, "Moon");
        ((Physics)Moon.desc).v.set(1000f, 5000000f, 0);
    }

    @Override
    public void input() {
        if(winMan.isKeyPressed(GLFW.GLFW_KEY_SPACE) && entities[8] == null && !EventHandler.eventStream[0]){
            StillModel blockmodel = loader.loadOBJ("/OBJs/circle.obj");
            entities[updIndex] = new Entity(blockmodel);
            entities[updIndex].scale.set(1/80f);
            entities[updIndex].position.set(-4000000, 0, 0);
            entities[updIndex].desc = new Physics(1f, .125f);
            ((Physics)entities[updIndex].desc).v.set(Maths.triangulate(1100000f, 90));
            entities[updIndex].color.set(.3, 0, .5);
            entities[updIndex].desc.name = "Extra";


            funcl[updIndex] = gravity::update;
            EventHandler.eventStream[0] = true;
            updIndex++;
        }
    }

    @Override
    public void update(float interval) {//Add Event-Registry
        //interval similar to dt
        //System.out.println(GL30.glGetError());

        for (int f = 0; f < funcl.length; f++){//RealTime
            if(funcl[f] == null) continue;
            funcl[f].accept(interval, entities[f], entities);
        }
        //System.out.println(entities[0].vel);

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
        renderer.render(entities, shaders[0]);//Unoptimized I think//--

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
