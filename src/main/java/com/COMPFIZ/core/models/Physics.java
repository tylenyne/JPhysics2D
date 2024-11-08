package com.COMPFIZ.core.models;

import org.joml.Vector3f;

public class Physics extends Descriptors{
    public float mass, r, dc;
    public Vector3f v = new Vector3f();

    public Physics(float mass, float r, float dc) {
        this.mass = mass;
        this.r = r;
        this.dc = dc;
    }

    public Physics(float mass, float r, String name) {
        this.name = name;
        this.mass = mass;
        this.r = r;
    }

    public Physics(float mass, float r) {
        this.mass = mass;
        this.r = r;
    }

    public void setV(Vector3f v) {
        this.v = v;
    }
}
