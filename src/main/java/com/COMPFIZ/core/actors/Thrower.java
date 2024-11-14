package com.COMPFIZ.core.actors;

import com.COMPFIZ.core.mixins.maths.Maths;
import com.COMPFIZ.core.models.Entity;
import com.COMPFIZ.core.attributes.Physics;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class Thrower {
//Environmentals
private Vector3f w = new Vector3f();
private boolean atmospherical = false;
private float relativeRho = 0.97f;






    public void update(float interval, Entity entity){
        float temp = 0, p = 0;//0.97
        Vector3d force = new Vector3d(), acc = new Vector3d();
        Vector3f airspeed = new Vector3f();
        Physics pac = (Physics)entity.physics;
        if(atmospherical){
            if (entity.getPosition().y < 11000) {//Calculate Air Density
                temp = (float) -entity.position.y * .00649f + 15.04f;
                p = (101.29f * (float) Math.pow((temp + 273.1) / 288.08, 5.256));
            } else if (entity.getPosition().y < 25000) {
                temp = -56.25f;
                p = (float) (22.65 * Math.pow(Math.E, 1.73f - entity.getPosition().y * .00157f));
            }
            else if(entity.getPosition().y > 25000) {
                temp = -131.21f + .00299f * (float) entity.getPosition().y;
                p = 2.488f * (float) Math.pow((temp + 273.1) / 216.6f, -11.388f);
            }
            relativeRho = p / (.2869f * (temp + 273.1f));
        }
        airspeed.set(pac.v);
        airspeed.sub(w);//Tailwind would be positive headwind would be negative
        force.set(Maths.calcNetForces(new Vector3f[]{Maths.calcAirDrag(airspeed, relativeRho, pac.dc, (float) (Math.pow(pac.r, 2)*Math.PI)), Maths.calcDropForce((float) pac.mass, new Vector3f()), Maths.calcMagnus(new Vector3f(0, 0, 0), airspeed)}));

        force.div(pac.mass, acc);

        pac.v.add(acc.mul(interval));
        entity.position.add(pac.v.mul(interval, new Vector3d()));//Translation/pos
        System.out.println(force + " | " + pac.name);
        System.out.println(acc + " \\\\ " + pac.name);
    }

    public Vector3f getWind() {
        return w;
    }

    public void setWind(Vector3f w) {
        this.w = w;
    }

    public boolean get(){
        return atmospherical;
    }

    public void set(boolean b){
        atmospherical = b;
    }

    public void setrho(float f){
        relativeRho = f;
    }

    public float getrho(){
        return relativeRho;
    }

}
