package com.COMPFIZ.core.mixins.maths;

import com.COMPFIZ.core.mixins.Constants;
import org.joml.Matrix4f;
import com.COMPFIZ.core.models.Entity;
import org.joml.Vector3f;

public class Maths {

    public static Transformation createTM(Vector3f translation, Vector3f rotation, Vector3f scale) {
            Matrix4f matrix = new Matrix4f();
            matrix.identity().translate(translation.mul(Constants.field, new Vector3f()))
                    .rotateX((float) Math.toRadians(rotation.x))
                    .rotateY((float) Math.toRadians(rotation.y))
                    .rotateZ((float) Math.toRadians(rotation.z))
                    .scale(scale);//Method also takes a single float
            return new Transformation(matrix);
        }

        public static float calcSpeed(float mass, float force){
            float speed;
            speed = force/mass; //simplified would be speed ratio ex: 2/1
            return speed;
        }

        public static float calcSpeed(Entity entity, float force){
            return 0;
        }

        public static float calcSpeedRatio(float mass, float force){
            return 0;
        }

        public static float calcFrontFaceArea(float lowpoint, float highpoint, float scale){
            return Math.abs((highpoint - lowpoint) * scale);
        }

        public static Vector3f calcNetForces(Vector3f[] forces){
            Vector3f sum = new Vector3f(0, 0, 0);

            for(Vector3f force : forces){
                sum.add(force);
               //System.out.println(force);
            }
            //Can be optimized
             return sum;
        }

        public static float calcAirDrag(float v, float p, float c, float A){
            return (float) -(.5*A*p*c*v*v);//Negative sign so I can add it | Opposite
        }//If you want more modular/dynamic

        //Does work
        public static Vector3f calcAirDrag(Vector3f v, float p, float c, float A){
           float constant = -(.5f*A*p*c);//So I can add
           return v.mul(v.absolute(new Vector3f()), new Vector3f()).mul(constant);
        }

        public static Vector3f calcMagnus(Vector3f magnus, Vector3f airspeed){//vag or vog?
            return (magnus.cross(airspeed, new Vector3f())).mul(3 * 1f/100);
        }

        @Deprecated//Dont use its useless and interferes with interfave
        public static float calcDropForce(float speedOfGrav, float mass){
            return (speedOfGrav * mass);
        }

        public static Vector3f calcDropForce(float mass, Vector3f dest){
        return Constants.SOG.mul(mass, dest);
    }

        public static Vector3f calcThrustForce(Vector3f relativeSpeed){//uses horsepower or power apparently makes it different
            return relativeSpeed.mul(-128, new Vector3f()).add(new Vector3f(15400, 0, 0));
        }

        public static Vector3f AngledVel(float vel, float theta){
            float x = (float) Math.cos(Math.toRadians(theta)) * vel;
            float y = (float) Math.sin(Math.toRadians(theta)) * vel;
            return new Vector3f(x, y, 0);
        }


    }

