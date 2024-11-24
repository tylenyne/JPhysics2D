package com.COMPFIZ.core.attributes;

import org.joml.Vector3d;
import org.joml.Vector3f;

public class Physics extends Descriptors {
    public double mass, r, h;//Radius in km
    public float dc;
    public Vector3d v = new Vector3d();
    public boolean atmospherical = false;//Or going to be on earth

    public Physics(double mass, float r, float dc) {
        this.mass = mass;
        this.r = r;
        this.dc = dc;
    }

    public Physics(double mass, float r){
        this.mass = mass;
        this.r = r;
    }

    public Physics(double mass, float r, String name) {
        this.name = name;
        this.mass = mass;
        this.r = r;
    }

    public Physics(double mass, float r, String name, float dc) {
        this.mass = mass;
        this.r = r;
        this.dc = dc;
        this.name = name;
    }

    public void setV(double x, double y, double z) {
        v.set(x, y, z);
    }
}
