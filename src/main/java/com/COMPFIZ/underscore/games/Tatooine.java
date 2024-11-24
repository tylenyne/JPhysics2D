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
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.freetype.*;

public class Tatooine implements Disc{
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


    private TriConsumer<Float, Entity, Entity[]>[] funcl;
    private Entity TatooI;
    private Entity TatooII;
    private Entity Tatooine;

    private Shaders[] shaders;

    float r = .9f, g = .7f, b = 1, a = .8f;


    public Tatooine() throws Exception{
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


        Constants.field = Constants.orbitfield;
        cam = new Camera(1/Constants.orbitfield);

        StillModel blockModel = loader.loadOBJ("/OBJs/circle.obj");//Keep forgetting you have to add a slash prefixing path
        StillModel blockModel2 = loader.loadOBJ("/OBJs/circle.obj");
        StillModel blockModel3 = loader.loadOBJ("/OBJs/circle.obj");


        TatooI = new Entity(blockModel);
        TatooII = new Entity(blockModel2);
        Tatooine = new Entity(blockModel3);
        //Entity Moon = new Entity(blockModel3);


        TatooI.color.set(.85f, .7f, .2f);
        TatooI.scale.set(1/400d);
        TatooI.physics = new Physics(2.187251e30, 200, "Tatoo I");
        ((Physics)TatooI.physics).v.set(0, 12938.27, 0);
        TatooI.position.set(-3.14902e10, 0, 0);


        entities = new Entity[3];
        funcl = new TriConsumer[3];

        entities[0] = TatooI;
        funcl[0] = gravity::update;
        entities[1] = TatooII;
        funcl[1] = gravity::update;
        entities[2] = Tatooine;
        funcl[2] = gravity::update;
        //entities[2] = Moon;

        //Second mass
        TatooII.color.set(.85f, .7f, .2f);
        TatooII.scale.set(1/400d);
        TatooII.physics = new Physics(1.590728e30, 400, "Tatoo II");
        ((Physics)TatooII.physics).v.set(0, -17786, 0);
        TatooII.position.set(4.32998e10, 0, 0);

        //Moon.color.set(.69f, .7f, .7f);
        //Moon.scale.set(1/80f);
        //Moon.position.set(-4.1052e5, 100000, 0);
        //Moon.physics = new Physics(10000f, 400, "Moon");
        //((Physics)Moon.physics).v.set(Maths.triangulate(47000f, 45));

        Tatooine.color.set(.8f, .2f, .2f);
        Tatooine.scale.set(1/400d);
        Tatooine.physics = new Physics(5.97e24d, 20, "Tatooine");
        ((Physics)Tatooine.physics).v.set(0,  -33297, 0);
        Tatooine.position.set(2.15639e11, 0, 0);

        direction = new Vector3d(((Physics)Tatooine.physics).v.normalize(new Vector3d()));
        //vdifference = ((Physics)Saturn.physics).v.length();
    }

    @Override
    public void input(){

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
        }

        double there = ((Physics)Tatooine.physics).v.normalize(new Vector3d()).dot(direction);
        if(there >= .999 && EventHandler.allFrames > 1200){
            if(directionb4 > there){
                //EventHandler.isRunning = false;
            }
            directionb4 = there;
        }
        /**
        double velocity = ((Physics) Saturn.physics).v.length();
        if(velocity - vdifference > max){
            max = velocity - vdifference;//length is always positive
            save = EventHandler.allFrames;
        }
        if(velocity - vdifference < min){
            min = velocity - vdifference;//length is always positive
            save2 = EventHandler.allFrames;
        }
        System.out.println(velocity);*/
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
        System.out.println(max + " | " + min);
        System.out.println(save + ", " + save2);
        System.out.println(winMan.getTxtinput());
    }

    public float randomSpeed(){//.05 to .01
        return (float)((int)(Math.random()+.5))/100;
    }

    public GPURenderer getRenderer() {
        return renderer;
    }

}
