package com.COMPFIZ.core.simulations;

import com.COMPFIZ.core.mixins.Constants;
import com.COMPFIZ.core.mixins.maths.Maths;
import com.COMPFIZ.core.models.Entity;
import com.COMPFIZ.core.models.PhysicEntity;
import org.joml.Vector3f;

public class Projectile2D extends Sim{
//Environmentals


    /**
    public Projectile2D(Drive1D drive, Fall1D fall) {
        this.fall = fall;
        this.drive = drive;
    }
     */

    public Projectile2D(){

    }

    public void update(float interval, PhysicEntity pentity){
        float temp, p;
        airspeed.set(pentity.vel);
        airspeed.sub(w);//Tailwind would be positive headwind would be negative
        Vector3f force = Maths.calcNetForces(new Vector3f[]{Maths.calcAirDrag(airspeed, rho, c, A), Maths.calcDropForce(pentity.mass, new Vector3f()), Maths.calcMagnus(new Vector3f(0, 0, 0), airspeed)});
        //You will just have to change if its a car or not for now | thrustForce is for car
        //Could change it to where only x is affected by airdrag//Velocity - AG or wind = airspeed
        force.div(pentity.mass, acc);
        //System.out.println(airspeed);//TroubleShoot
        pentity.vel.add(acc.mul(interval, new Vector3f()));
        //System.out.println(force);
        pentity.translation.add(pentity.vel.mul(interval, new Vector3f()));//Translation/pos
        //vel acc and pos should all be positive maybe not pos
        temp = -pentity.translation.y*.00649f +15.04f;
        p = (101.29f * (float)Math.pow((temp+273.1)/288.08, 5.256));
        //System.out.println(rho);
        rho = p/(.2869f * (temp+273.1f));//Doesnt matter first time around because airdrag is zero
    }
}
