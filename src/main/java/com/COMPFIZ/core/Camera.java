package com.COMPFIZ.core;

import com.COMPFIZ.core.mixins.maths.ViewMatrix;
import com.COMPFIZ.core.models.Entity;
import org.joml.Math;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;

import java.awt.*;

public class Camera {
    public Vector3d position = new Vector3d(0, 0, 0);
    public double roll, zoom = 1, field;

    public Camera(double field){
        this.field = field;
    }

    public void input(WindowManager win, Entity[] ents){
        System.out.println(position);
        if(win.isKeyPressed(GLFW.GLFW_KEY_W)){
            zoom = Math.min(zoom + .01, 5);
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_S)){
            zoom = Math.max(zoom - .01, .1);
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_RIGHT)){
            position.x+=.001 * 1/field;
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_LEFT)){
            position.x-=.001 * 1/field;
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_UP)){
            position.y+=.001 * 1/field;
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_DOWN)){
            position.y-=.001 * 1/field;
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_1)){
            follow(ents[0]);
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_2)){
            follow(ents[1]);
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_3)){
            follow(ents[2]);
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_4)){
            follow(ents[3]);
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_5)){
            follow(ents[4]);
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_6)){
            follow(ents[5]);
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_7)){
            follow(ents[6]);
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_8)){
            follow(ents[7]);
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_9)){
            follow(ents[8]);
        }
    }

    public void follow(Entity entity){
        position.set(entity.getPosition());
    }
}
