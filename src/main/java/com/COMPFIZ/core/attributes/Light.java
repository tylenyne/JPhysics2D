package com.COMPFIZ.core.attributes;


import org.joml.Vector3f;

public class Light extends Descriptors {
    public Vector3f color;
    public float intensity;

    public Light(){
        color = new Vector3f();
        intensity = 1.0f;
    }

    public Light(Vector3f color){
        this.color = color;
        intensity = 1;
    }
}
