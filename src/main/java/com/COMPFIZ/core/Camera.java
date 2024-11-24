package com.COMPFIZ.core;

import com.COMPFIZ.core.mixins.Constants;
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
    public double roll, zoom = 1, field, zoomspeed = 1, scrollspeed = 1;
    private Vector3d saveFollow;

    public Camera(double field){
        this.field = field;
    }

    public void input(WindowManager win, Entity[] ents){
        if(win.isKeyPressed(GLFW.GLFW_KEY_W)){
            //zoom = Math.min(zoom + zoomspeed, 100000);
            zoom += zoomspeed * 10;
            scrollspeed = 1/zoom;
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_S)){
            zoom = Math.max(zoom - zoomspeed * 10, .1);
            scrollspeed = 1/zoom;
        }
        /**
        if(win.isKeyPressed(GLFW.GLFW_KEY_TAB)){
            zoom = (1/Constants.field)/(2440 * 1000);
            zoomspeed = 1/1e7;
            System.out.println(zoom);
        }*/
        /**
        if(win.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)){
            zoomspeed-=100;
        }**/
        if(win.isKeyPressed(GLFW.GLFW_KEY_RIGHT)){
            position.x+=9e-4 * field * scrollspeed;
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_LEFT)){
            position.x-=9e-4 * field * scrollspeed;
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_UP)){
            position.y+=9e-4 * field * scrollspeed;
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_DOWN)){
            position.y-=9e-4 * field * scrollspeed;
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_F1)){
            follow(ents[0].position);
            saveFollow = ents[0].position;
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_F2)){
            follow(ents[1].position);
            saveFollow = ents[1].position;
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_F3)){
            follow(ents[2].position);
            saveFollow = ents[2].position;
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_F4)){
            follow(ents[3].position);
            saveFollow = ents[3].position;
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_F5)){
            follow(ents[4].position);
            saveFollow = ents[4].position;
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_F6)){
            follow(ents[5].position);
            saveFollow = ents[5].position;
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_F7)){
            follow(ents[6].position);
            saveFollow = ents[6].position;
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_F8)){
            follow(ents[7].position);
            saveFollow = ents[7].position;
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_F9)){
            follow(ents[8].position);
            saveFollow = ents[8].position;
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_RIGHT_SHIFT)){
            follow(ents[9].position);
            saveFollow = ents[9].position;
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_SPACE)){
            saveFollow = ents[10].position;
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_K)){
            zoomspeed-=.01;
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_L)){
            zoomspeed+=.01;
        }
        if(saveFollow != null){
            follow(saveFollow);
        }
        if(win.isKeyPressed(GLFW.GLFW_KEY_Q)){
            freeCam();
        }
    }

    public void follow(Vector3d pos){
        position.set(pos);
    }

    public void freeCam(){
        saveFollow = null;
    }
}
