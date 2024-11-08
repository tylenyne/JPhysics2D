package com.COMPFIZ.core.actors;

import com.COMPFIZ.core.mixins.maths.Maths;
import com.COMPFIZ.core.models.Entity;
import com.COMPFIZ.core.models.Physics;
import org.joml.Vector3f;

public class Thrower {
//Environmentals
private Vector3f W = new Vector3f();

    /**
    public Projectile2D(Drive1D drive, Fall1D fall) {
        this.fall = fall;
        this.drive = drive;
    }
     */

    public void update(float interval, Entity entity){
        boolean onEarth = false;
        float temp = 0, p = 0, relativeRho = .97f;//0.97
        Vector3f force = new Vector3f(), acc = new Vector3f(), airspeed = new Vector3f();
        Physics pac = (Physics)entity.desc;
        /**
        if(onEarth) {
            if (pentity.getPosition().y < 11000) {//Calculate Air Density
                temp = -pentity.translation.y * .00649f + 15.04f;
                p = (101.29f * (float) Math.pow((temp + 273.1) / 288.08, 5.256));
            } else if (pentity.getPosition().y < 25000) {
                temp = -56.25f;
                p = (float) (22.65 * Math.pow(Math.E, 1.73f - pentity.getPosition().y * .00157f));
            }
            else if(pentity.getPosition().y > 25000) {
                temp = -131.21f + .00299f * pentity.getPosition().y;
                p = 2.488f * (float) Math.pow((temp + 273.1) / 216.6f, -11.388f);
            }
            relativeRho = p / (.2869f * (temp + 273.1f));
        }*/
        airspeed.set(pac.v);
        airspeed.sub(W);//Tailwind would be positive headwind would be negative
        force.set(Maths.calcNetForces(new Vector3f[]{Maths.calcAirDrag(airspeed, relativeRho, pac.dc, (float) (Math.pow(pac.r, 2)*Math.PI)), Maths.calcDropForce(pac.mass, new Vector3f()), Maths.calcMagnus(new Vector3f(0, 0, 0), airspeed)}));
        //You will just have to change if its a car or not for now | thrustForce is for car
        //Could change it to where only x is affected by airdrag//Velocity - AG or wind = airspeed
        force.div(pac.mass, acc);
        //System.out.println(airspeed);//TroubleShoot
        pac.v.add(acc.mul(interval));
        //System.out.println(Maths.calcAirDrag(airspeed, relativeRho, pac.dc, (float) (Math.pow(pac.r, 2)*Math.PI)));
        entity.position.add(pac.v.mul(interval, new Vector3f()));//Translation/pos
        //vel acc and pos should all be positive maybe not pos
    }

    public Vector3f getW() {
        return W;
    }

    public void setW(Vector3f w) {
        W = w;
    }
}
