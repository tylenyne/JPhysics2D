package com.COMPFIZ.underscore;

import com.COMPFIZ.core.*;
import com.COMPFIZ.core.mixins.Constants;
import com.COMPFIZ.core.mixins.maths.Maths;
import com.COMPFIZ.core.models.Entity;
import com.COMPFIZ.core.models.PhysicEntity;
import com.COMPFIZ.core.models.StillModel;
import com.COMPFIZ.core.simulations.Projectile2D;
import com.COMPFIZ.core.shaders.ShaderTM;
import com.COMPFIZ.core.shaders.ShaderType;
import org.joml.Math;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;

import java.util.Random;
import java.util.function.BiConsumer;

public class examGame implements IGameLog {


    private final WindowManager winMan;

    private final Loader loader;

    private final GPURenderer renderer;

    private ShaderType shaderType;

    private PhysicEntity myGround;
    private PhysicEntity myBlock;
    private PhysicEntity[] entities;

    private float[] rectPoints;
    private float pos = 0, vel = 0, acc = 0, force, time, airspeed;
    private Projectile2D projectile;
    private int updIndex = 0;//Basically size of Entities arr
    //private Map<Entity, Function> linker; heavyweight option
    private BiConsumer<Float, PhysicEntity>[] funcl;
    private StillModel blockModel;

    float r, g, b, a;
    //Physics
    float A, c, rho, temp, height, tw, hw;


    public examGame() throws Exception{
        winMan = Launcher.getWinMan();
        loader = Launcher.getLoader();
        renderer = new GPURenderer();
    }

    @Override
    public void init() throws Exception{
        renderer.init();
        shaderType = new ShaderTM(this);
        //maximum/minimum
        projectile = new Projectile2D();
        projectile.setW(new Vector3f(0, 0, 0));

        r=0; g=0; b=0; a =0;
        r+=.9f; g+=.7f; b+=1;
        //coordinate system is a clamped 0-1 coordinate plane
             float[] blockPoints = Constants.block;
             int[] blockindices = new int[]{0,1,3,//Indices so the points arent in specific order
                                            3,1,2};
        //indexes correspond to position points(rectPoints), every 3 = 1 triangle
        blockModel = loader.loadVAO(blockPoints, blockindices);//RawModel

        //rectPoints = Constants.makecircle();
        float [] groundPoints = Constants.rectangle;

        StillModel groundModel = loader.loadVAO(groundPoints, blockindices);//RawModel


        Vector3f position = new Vector3f(0f,-2000f,0f);
        Vector3f rotation = new Vector3f(0,0,0);
        Vector3f color = new Vector3f(.2f,.8f,.89f);

        Entity ground = new Entity(groundModel, position, color);
        Entity block = new Entity(blockModel, Maths.AngledVel(30.48f, 10), rotation, color,1f/20);
        myBlock = new PhysicEntity(block, Maths.AngledVel(330, 10), 4.2f);
        myGround = new PhysicEntity(ground, 100000);//RawModel
        entities = new PhysicEntity[9];
        funcl = new BiConsumer[9];
        entities[0] = myBlock;
        renderer.setEntityColor(entities[0]);
        funcl[0] = projectile::update;
        entities[1] = myGround;
        renderer.setEntityColor(entities[0]);
        updIndex = 2;
    }

    @Override
    public void input() {
        if(winMan.isKeyPressed(GLFW.GLFW_KEY_SPACE) && entities[8] == null && !EngineManager.eventStream[0]){
            int[] indices = {0,1,3,
                    3,1,2};
            entities[updIndex] = new PhysicEntity(new Entity(loader.loadVAO(Constants.block, indices), new Vector3f(-100, 0, 0), new Vector3f(0,0, 0), Constants.COLORLIST[updIndex-2], 1f/20), Maths.AngledVel((float) Math.random() * 320, (float) Math.random() * 90), 4f);
            funcl[updIndex] = projectile::update;
            EngineManager.eventStream[0] = true;
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

        //if(Math.abs(entities[0].getPosition().y) < Math.abs(Maths.AngledVel(30.48f, 10).y)){
            System.out.println(entities[0].getPosition().y);
            System.out.println(EngineManager.totalTime);
        //}

        //Try to save super bad class
    }



    @Override
    public void render() {
        if(winMan.isResized()){
            GL30.glViewport(0, 0, winMan.getWidth(), winMan.getHeight());
            winMan.setResized(true);
        }
        renderer.setClearColor(r,g,b, 0);
        renderer.prepare();
        renderer.render(entities, shaderType);//Unoptimized I think//--

    }

    @Override
    public void cleanup() {
        loader.cleanup();//Cleans Up all the vao-data
        renderer.cleanup();
        System.out.println("Ran For " + EngineManager.totalTime + " Seconds");
    }

    public float randomSpeed(){//.05 to .01
        return (float)((int)(Math.random()+.5))/100;
    }

    public GPURenderer getRenderer() {
        return renderer;
    }
}
