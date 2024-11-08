package com.COMPFIZ.underscore;

import com.COMPFIZ.core.*;
import com.COMPFIZ.core.mixins.Constants;
import com.COMPFIZ.core.mixins.maths.Maths;
import com.COMPFIZ.core.models.Entity;
import com.COMPFIZ.core.models.Physics;
import com.COMPFIZ.core.models.StillModel;
import com.COMPFIZ.core.actors.Thrower;
import com.COMPFIZ.core.shaders.myShader;
import org.joml.Math;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;

import java.io.IOException;
import java.nio.file.*;
import java.util.function.BiConsumer;

public class Physics2D implements Disc {


    private final WindowManager winMan;

    private final Loader loader;

    private final GPURenderer renderer;

    private Entity[] entities;
    float height = 0;

    private Thrower projectile;
    private int updIndex = 0;//Basically relative size of Entities arr
    //private Map<Entity, Function> linker; heavyweight option
    private BiConsumer<Float, Entity>[] funcl;
    private Path PATH;
    private String FILENAME = "src/data.txt";
    private Vector3f groundposition = new Vector3f(0f,-.5f/Constants.field,0f);
    float r = .9f, g = .7f, b = 1, a;//a doesnt have to be initialized for some reason
    float pos3fLength = 30.48f;
    float theta;


    public Physics2D() throws Exception{
        winMan = Launcher.getWinMan();
        loader = Launcher.getLoader();
        renderer = new GPURenderer();
    }

    @Override
    public void init() throws Exception{
        //renderer.init();
        projectile = new Thrower();
        projectile.setW(new Vector3f(0, 0, 0));
        PATH = Paths.get(FILENAME);

        //FileWriter
        if(Files.exists(PATH)){
            Files.delete(PATH);
        }
        Files.createFile(PATH);

        StillModel blockModel;
        StillModel groundModel;
        //decl

        //Model loading
        float [] groundPoints = Constants.rectangle;//This shouldnt be a square
        int[] groundindices = new int[]{0,1,2,3,4,5};
        groundModel = loader.loadinVAO(groundPoints, groundindices);
        blockModel = loader.loadOBJ("/OBJs/circle.obj");//Keep forgetting you have to add a slash prefixing path

        //ball
        theta = 10f;

        Entity myBlock = new Entity(blockModel);
        Entity myGround = new Entity(groundModel);


        //skyblue = .2f,.8f,.89f
        myBlock.scale.set(1/80f);
        myBlock.position = Maths.triangulate(pos3fLength, theta);

        myBlock.desc = new Physics(4.2f, .125f, .3f);
        ((Physics) myBlock.desc).v = Maths.triangulate(330f, theta);

        entities = new Entity[9];
        funcl = new BiConsumer[9];
        entities[0] = myBlock;
        funcl[0] = projectile::update;
        entities[1] = myGround;
        updIndex = 2;

        //StableGround
        myGround.color.set(.2f, .8f, .89f);
        myGround.position.set(groundposition);
    }

    @Override
    public void input() {
        if(winMan.isKeyPressed(GLFW.GLFW_KEY_SPACE) && entities[8] == null && !EventHandler.eventStream[0]){

            StillModel blockModel = loader.loadOBJ("/OBJs/circle.obj");
            entities[updIndex] = new Entity(blockModel);
            entities[updIndex].scale.set(1/80f);
            entities[updIndex].position.set(Maths.triangulate(pos3fLength, theta));
            entities[updIndex].desc = new Physics(10f, .125f);

            funcl[updIndex] = projectile::update;
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
            funcl[f].accept(interval, entities[f]);
        }

        if(entities[0].getPosition().y < 0){
            EventHandler.isRunning = false;
            System.out.println(entities[0].getPosition().x - Maths.triangulate(pos3fLength, theta).x);
            System.out.println(EventHandler.totalTime);
        }

/**
if(entities[0].getPosition().y < height){
    EngineManager.isRunning = false;
    System.out.println(EngineManager.totalTime);
    System.out.println(entities[0].getPosition().y);
}*/

height = entities[0].getPosition().y;

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
        renderer.setClearColor(r,g,b, a);
        renderer.prepare();
        renderer.render(entities, new myShader());//Unoptimized I think//--

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
