package com.COMPFIZ.underscore;

import com.COMPFIZ.core.*;
import com.COMPFIZ.core.utils.Constants;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class examGame implements _IGamelogic {


    private final WindowManager winMan;

    private final Loader loader;

    private final GPURenderer renderer;

    private RawModel model;

    float r, g, b, a;


    public examGame() {
        winMan = Launcher.getWinMan();
        loader = new Loader();
        renderer = new GPURenderer();
    }

    @Override
    public void init() throws Exception {
        r=0; g=0; b=0; a=0;
        r+=Math.random(); g+=Math.random(); b+=Math.random(); a+=Math.random();
        GPURenderer.setClearColor(r, g, b, 0);
        Random rndm = new Random();
       float[] rectPoints = {
               -0.5f, 0.5f, 0f,
               -0.5f, -0.5f, 0f,
               0.5f, -0.5f, 0f,
               //Left Bottom
               0.5f, -0.5f, 0f,
               0.5f, 0.5f, 0f,
               -0.5f, 0.5f, 0f
               //Top Right
        };

        model = loader.loadVAO(rectPoints);





    }

    @Override
    public void input() {

    }

    @Override
    public void update(float interval) {

    }



    @Override
    public void render() {
        if(winMan.isResize()){
            GL11.glViewport(0, 0, winMan.getWidth(), winMan.getHeight());
            winMan.setResize(true);
        }
        renderer.render(model);



    }

    @Override
    public void cleanup() {
        loader.cleanup();//Cleans Up all the vao-data
    }

    public float randomSpeed(){//.05 to .01
        return (float)((int)(Math.random()+.5))/100;
    }
}
