package com.COMPFIZ.core.mixins;

import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Constants {
    public static final String TITLE = "_UNDERSCORE";
    public static final float MOUSE_SENSITIVITY = 0.2f;
    public static final Vector3f DEFAULTVECTOR3F = new Vector3f(0, 0, 0);
    //I could overload methods in entity class to use this but I dont wanna right now
    public static final float DEFAULTSCALE = 1;
    //public static class Color;
    public static final int MAX_SPOTLIGHTS = 5;
    public static double field;
    public static final float moongravity = 1.62f;
    public static int masses = 2;
    public static final int MAX_POINTLIGHTS = 5;
    public static final Vector4f DEFAULT_COLOR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    public static final Vector3f AMBIENT_LIGHT = new Vector3f(.2f, .2f, .2f);
    public static final float SPECULAR_POWER = 10f;
    public static final Vector3d STDDIR = new Vector3d(0, -1, 0);
    public static final float SOG = 9.8f;
    public static final Vector3d EOG = (STDDIR.mul(SOG, new Vector3d()));
    public static Vector3d FOG = EOG;
    public static final double orbitfield = 1/(5906372947200d);//radius from sun to pluto in km
    public static final float physicfield = 1/400f;
    public static final Vector3d MOG = (STDDIR.mul(moongravity, new Vector3d()));
    public static final float STDWEIGHT = 1;
    public static final float STDRHO = 1.225f;
    public static final Vector3f STDCOLOR = new Vector3f(0f, 0f, 0.0f);
    public static final Vector3f[] COLORLIST = {
            new Vector3f(.9f, .8f, .2f),
            new Vector3f(1f, 1f, .9f),
            new Vector3f(.6f, .5f, .7f),
            new Vector3f((float)Math.random(), (float)Math.random(), (float)Math.random()),
            new Vector3f((float)Math.random(), (float)Math.random(), (float)Math.random()),
            new Vector3f((float)Math.random(), (float)Math.random(), (float)Math.random()),
            new Vector3f((float)Math.random(), (float)Math.random(), (float)Math.random()),
            new Vector3f((float)Math.random(), (float)Math.random(), (float)Math.random()),
            new Vector3f((float)Math.random(), (float)Math.random(), (float)Math.random())
    };
    public static final float[] circle = new float[] {};
    public static final float[] block = new float[]{
            //LeftBottom
            -.5f, 0.5f, 0f,//[0]
            -.5f, -0.5f, 0f,//[1]
            .5f, -0.5f, 0f,//[2]
            //RightTop
            .5f, -0.5f, 0f,//[3]
            .5f, 0.5f, 0f,//[4]
            -.5f, 0.5f, 0f//[5]};
    };

    public static final float[] rectangle = new float[] {
            //LeftBottom
            -1f, 0.5f, 0f,     //[0]
            -1f, -0.5f, 0f,  //[1]
            1f, -0.5f, 0f,    //[2]
            //RightTop
            1f, -0.5f, 0f,     //[3]
            1f, 0.5f, 0f,      //[4]
            -1f, 0.5f, 0f      //[5]
    };

    @Deprecated//Geometry
    public static float[] makecircle(){
        float[] circle = new float[60 * 3 * 3];
        float s2 = (float) Math.sqrt(2);
        for(int i = 0; i < 60; i++){
            circle[i * 9] = s2/2 * (float) Math.sin((Math.PI/30) * (i + 1));//Not the best code
            circle[i * 9 + 1] = s2/2 * (float) Math.cos((Math.PI/30) * (i + 1));
            //btween points
            circle[i * 8] = s2/2 * (float) Math.sin((Math.PI/30) * (i + 1));//Not the best code
            circle[i * 8 + 1] = s2/2 * (float) Math.cos((Math.PI/30) * (i + 1));
            //circle[i * 3 + 2] = 0;
        }
        return circle;
    }

}
