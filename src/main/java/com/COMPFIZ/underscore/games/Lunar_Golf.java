package com.COMPFIZ.underscore.games;

import com.COMPFIZ.core.*;
import com.COMPFIZ.core.actors.Thrower;
import com.COMPFIZ.core.attributes.Physics;
import com.COMPFIZ.core.mixins.Constants;
import com.COMPFIZ.core.mixins.maths.Maths;
import com.COMPFIZ.core.models.Entity;
import com.COMPFIZ.core.models.StillModel;
import com.COMPFIZ.core.shaders.Shaders;
import com.COMPFIZ.core.shaders.myShader;
import com.COMPFIZ.underscore.Launcher;
import org.joml.Math;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.function.BiConsumer;

public class Lunar_Golf implements Disc {


    private final WindowManager winMan;

    private final Loader loader;
    private Camera cam;

    private final GPURenderer renderer;

    private Entity[] entities;
    float height = 0;

    private Thrower thrower;
    private int updIndex = 0;//Basically relative size of Entities arr
    private Shaders[] shaders = new Shaders[1];
    //private Map<Entity, Function> linker; heavyweight option
    private BiConsumer<Float, Entity>[] funcl;
    private Path PATH;
    private String FILENAME = "src/data.txt";
    private Vector3f groundposition = new Vector3f(0f,-.5f/Constants.physicfield,0f);
    float r = .9f, g = .7f, b = 1, a;//a doesnt have to be initialized for some reason
    float hypotenuse = 0f;
    float theta;


    public Lunar_Golf() throws Exception{
        winMan = Launcher.getWinMan();
        loader = Launcher.getLoader();
        renderer = new GPURenderer();
    }

    @Override
    public void init() throws Exception{
        shaders[0] = new myShader();
        renderer.init(shaders);
        thrower = new Thrower();
        thrower.setrho(0);
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
        StillModel skyModel = loader.loadinVAO(groundPoints, groundindices);
        blockModel = loader.loadOBJ("/OBJs/circle.obj");//Keep forgetting you have to add a slash prefixing path

        //ball
        theta = 8f;
        Constants.field = Constants.physicfield;
        Constants.FOG = Constants.MOG;

        Entity myBlock = new Entity(blockModel);
        Entity myGround = new Entity(groundModel);
        Entity myBackGround = new Entity(skyModel);


        //skyblue = .2f,.8f,.89f
        myBlock.scale.set(1/80f);
        myBlock.position = Maths.triangulate((double) hypotenuse, theta);
        myBlock.color.set(.5f, .5f, .5f);

        myBlock.physics = new Physics(0.045f, .021335f, 0f);
        ((Physics) myBlock.physics).v = Maths.triangulate(70d, theta);
        
        myBlock.physics.name = "Golf Ball";

        entities = new Entity[9];
        funcl = new BiConsumer[9];
        entities[0] = myBlock;
        funcl[0] = thrower::update;
        entities[1] = myGround;
        entities[2] = myBackGround;
        updIndex = 3;


        //StableGround
        myGround.color.set(.8f, .8f, .7f);
        myGround.position.set(groundposition);
        myGround.scale.set(2, 1, 1);

        myBackGround.color.set(0f, 0f, 0f);
        myBackGround.position.set(0, 0, 0);
        myBackGround.scale.set(5);
    }

    @Override
    public void input() {
        if(winMan.isKeyPressed(GLFW.GLFW_KEY_SPACE) && entities[8] == null && !EventHandler.eventStream[0]){
            StillModel blockModel = loader.loadOBJ("/OBJs/circle.obj");
            entities[updIndex] = new Entity(blockModel);
            entities[updIndex].scale.set(1/80f);
            entities[updIndex].position.set(400, 0, 0);
            entities[updIndex].physics = new Physics(10f, .125f);
            entities[updIndex].color.set(.5f, .5f, .5f);
            entities[updIndex].physics.name = "Extra";
            funcl[updIndex] = thrower::update;
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
            funcl[f].accept(interval, entities[f]);
        }
        System.out.println("error code " + GL30.glGetError());
        for (int f = 0; f < funcl.length; f++){//RealTime
            if(funcl[f] == null) continue;
            System.out.println(((Physics) entities[f].physics).v + " << " + entities[f].physics.name);
        }
        for (int f = 0; f < funcl.length; f++){//RealTime
            if(funcl[f] == null) continue;
            System.out.println(( entities[f].position + " ## " + entities[f].physics.name));
        }

        if(entities[0].getPosition().y < 0){
            EventHandler.isRunning = false;
            System.out.println(entities[0].getPosition().x - Maths.triangulate(hypotenuse, theta).x);
            System.out.println(EventHandler.totalTime);
        }

/**
if(entities[0].getPosition().y < height){
    EngineManager.isRunning = false;
    System.out.println(EngineManager.totalTime);
    System.out.println(entities[0].getPosition().y);
}*/

height = (float) entities[0].getPosition().y;

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
