package com.COMPFIZ.underscore;

import com.COMPFIZ.core.*;
import com.COMPFIZ.core.actors.Gravitor;
import com.COMPFIZ.core.mixins.Constants;
import com.COMPFIZ.core.mixins.TriConsumer;
import com.COMPFIZ.core.mixins.maths.Maths;
import com.COMPFIZ.core.models.Entity;
import com.COMPFIZ.core.attributes.Physics;
import com.COMPFIZ.core.models.StillModel;
import com.COMPFIZ.core.shaders.Shaders;
import com.COMPFIZ.core.shaders.myShader;
import org.joml.Math;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;

import java.io.IOException;
import java.nio.file.*;

public class Orbit2D implements Disc{

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

        Constants.field = Constants.orbitfield;

        StillModel blockModel = loader.loadOBJ("/OBJs/circle.obj");//Keep forgetting you have to add a slash prefixing path
        StillModel blockModel2 = loader.loadOBJ("/OBJs/circle.obj");
        StillModel blockModel3 = loader.loadOBJ("/OBJs/circle.obj");
        StillModel blockModel4 = loader.loadOBJ("/OBJs/circle.obj");




        Entity Earth = new Entity(blockModel);
        Entity Sun = new Entity(blockModel2);
        Entity Moon = new Entity(blockModel3);


        Earth.color.set(.2f,.8f,.89f);
        Earth.scale.set(1/80f);
        Earth.physics = new Physics(5.972e3d, 200, "Earth");
        Earth.position.set(-4.015e5, 0, 0);
        ((Physics)Earth.physics).v.set(-2.721826952615546E+01, 1.299836387907317E+01f, -7.733795645012975E-04).mul(10);


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
        Sun.color.set(.85f, .7f, .2f);
        Sun.scale.set(1/5f);
        Sun.position.set(0, 0,0 );
        Sun.physics = new Physics(1e16f, 400, "Sun");

        Moon.color.set(.69f, .7f, .7f);
        Moon.scale.set(1/80f);
        Moon.position.set(-4.1052e5, 100000, 0);
        Moon.physics = new Physics(10000f, 400, "Moon");
        ((Physics)Moon.physics).v.set(Maths.triangulate(47000f, 45));


    }

    @Override
    public void input() {
        if(winMan.isKeyPressed(GLFW.GLFW_KEY_SPACE) && entities[8] == null && !EventHandler.eventStream[0]){
            StillModel blockmodel = loader.loadOBJ("/OBJs/circle.obj");
            entities[updIndex] = new Entity(blockmodel);
            entities[updIndex].scale.set(1/80f);
            entities[updIndex].position.set(-40000000d, 0, 0);
            entities[updIndex].physics = new Physics(1f, .125f);
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
        System.out.println("FRAME: " + EventHandler.allFrames);
        System.out.println("********************************");
        for (int f = 0; f < funcl.length; f++){//RealTime
            if(funcl[f] == null) continue;
            funcl[f].accept(interval, entities[f], entities);
        }
        System.out.println("error code " + GL30.glGetError());
        for (int f = 0; f < funcl.length; f++){//RealTime
            if(funcl[f] == null) continue;
            System.out.println(((Physics) entities[f].physics).v + " << " + entities[f].physics.name);
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
