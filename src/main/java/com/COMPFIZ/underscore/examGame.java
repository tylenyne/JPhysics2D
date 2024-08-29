package com.COMPFIZ.underscore;

import com.COMPFIZ.core.*;
import com.COMPFIZ.core.utils.Constants;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class examGame implements _IGamelogic {


    private final WindowManager winMan;


    public examGame() {
        winMan = Launcher.getWinMan();
    }

    @Override
    public void init() throws Exception {

        Random rndm = new Random();




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
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

    }

    @Override
    public void cleanup() {
        winMan.cleanup();
    }

    public float randomSpeed(){//.05 to .01
        return (float)((int)(Math.random()+.5))/100;
    }
}
