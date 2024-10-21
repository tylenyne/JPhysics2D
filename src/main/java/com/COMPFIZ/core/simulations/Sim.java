package com.COMPFIZ.core.simulations;

import org.joml.Vector3f;

public class Sim implements ISimulation {

    float rho = .97f, A = 0.049f, c = .3f;
    //Variables
    Vector3f acc = new Vector3f();
    Vector3f force = new Vector3f(0, 0, 0);
    Vector3f airspeed = new Vector3f(0,0,0);
    Vector3f w = new Vector3f(0,0,0);

    public Sim(){

    }

    @Override
    public void update(float interval) {

    }

    public Vector3f getAcc() {
        return acc;
    }

    public Vector3f getForce() {
        return force;
    }

    public void setW(Vector3f w){
        this.w = w;
    }
    public Vector3f getAirspeed() {
        return airspeed;
    }

}
