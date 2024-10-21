package com.COMPFIZ.core.simulations;

import com.COMPFIZ.core.mixins.maths.Maths;
import org.joml.Vector3f;

public class SuperBadClass {//Maybe make some variables super class global so only sim can be run per program
    //Inputs
   private float rho = 1.15f, A = .23f, c = 1;
   //Variables
   private Vector3f acc = new Vector3f();
    private Vector3f force = new Vector3f();
    private Vector3f pos = new Vector3f(0,0,0);
    private Vector3f vel = new Vector3f(0,0,0);
    private Vector3f airspeed = vel;
    private Vector3f tw = new Vector3f(0,0,0);
    private Vector3f hw = new Vector3f(0,0,0);



   public class Projectile2D{
        private float angle, startVel;//In Radians maybe
        private Vector3f pos, vel, acc;


        public Projectile2D(float mass, float radius, float angle, float startVel) {
            this.angle = angle;
            this.startVel = startVel;

            pos = new Vector3f(0, 0, 0);
            vel = new Vector3f(0, 0, 0);
        }
        public void simulate(){

        }
        public void update(float interval){

        }

        private void check(){

        }


    }

    public class Drive1D{
       private float mass;
        public Drive1D(float mass){
            this.mass = mass;
        }

        public void simulate(){

        }

        public void update(float interval){
            force = Maths.calcNetForces(new Vector3f[]{Maths.calcThrustForce(vel), new Vector3f(Maths.calcAirDrag(airspeed.x, rho, c, A),0, 0)});
            //You will just have to change if its a car or not for now | thrustForce is for car
            //Could change it to where only x is affected by airdrag
            force.div(mass, acc);
            //TroubleShoot
            vel.add(acc.mul(interval, new Vector3f()));
            airspeed.set(vel);
            airspeed.add(tw).sub(hw);

            pos.add(airspeed.mul(interval, new Vector3f()));
        }
    }

   public class Fall1D {//Maybe make package private
        //Inputs | Most stuff should be positive
        private float height, mass, p = 0, temp = 0;
        //Variables
        private boolean isDone;

        public Fall1D(float height, float mass) {
            this.height = height;
            this.mass = mass;
            pos.y = height;
        }

        public void simulate(){//Fully preloaded sim | assign values to frames while loading

        }

        public void update(float interval){//Idk if calcRotaryForce should be vel or airspeed

        }

       public float getHeight() {
           return height;
       }


   }

   public SuperBadClass(){

   }

    public Vector3f getAirspeed() {
        return airspeed;
    }

    public float getRho(){
        return rho;
    }

    public Vector3f getPos(){
       return pos;
    }

    public void settw(Vector3f tw) {
        this.tw = tw;
    }

    public void sethw(Vector3f hw) {
        this.hw = hw;
    }

   public void reinit(){

   }
}
