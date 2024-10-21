package com.COMPFIZ.core.models;

import org.joml.Vector3f;

public class PhysicEntity extends Entity {
    public float mass;
    public Vector3f vel = new Vector3f(0,0,0);


    public PhysicEntity(Entity parent,  float mass) {//Float startRotation
        super(parent);
        this.mass = mass;//New method that defaults mass to 0? For things like light rays or smthing | wouldnt that just be an Entity though
    }

    public PhysicEntity(Entity parent, Vector3f svelocity, float mass) {
        super(parent);
        this.vel.set(svelocity);
        this.mass = mass;
    }

}
