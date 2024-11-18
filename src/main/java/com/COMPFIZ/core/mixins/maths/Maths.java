package com.COMPFIZ.core.mixins.maths;

import com.COMPFIZ.core.mixins.Constants;
import org.joml.Matrix4d;
import org.joml.Matrix4f;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class Maths {

    public static Transformation createTM(Vector3d translation, Vector3d rotation, Vector3d scale){
        Matrix4d matrix = new Matrix4d();
        matrix.identity().translate(translation.mul(Constants.field, new Vector3d()))
                .rotateX((float) Math.toRadians(rotation.x))
                .rotateY((float) Math.toRadians(rotation.y))
                .rotateZ((float) Math.toRadians(rotation.z))
                .scale(scale);//Because 16:9 resolution
        return new Transformation(matrix);
    }

    public static ViewMatrix createVM(Vector3d position, Vector3d zoom, double field){
        Matrix4d matrix = new Matrix4d();
        matrix.identity()
                .translate(position.negate(new Vector3d()).mul(zoom.mul(field, new Vector3d()), new Vector3d()))
                .scale(zoom);
        return new ViewMatrix(matrix);
    }

        public static float calcSpeed(float mass, float force){
            float speed;
            speed = force/mass; //simplified would be speed ratio ex: 2/1
            return speed;
        }

        public static float calcFrontFaceArea(float lowpoint, float highpoint, float scale){
            return Math.abs((highpoint - lowpoint) * scale);
        }

        public static Vector3f calcNetForces(Vector3f[] forces){
            Vector3f sum = new Vector3f(0, 0, 0);

            for(Vector3f force : forces){
                if(force == null) continue;
                sum.add(force);
               //System.out.println(force);
            }
            //Can be optimized
             return sum;
        }

    public static Vector3d calcNetForces(Vector3d[] forces){
        Vector3d sum = new Vector3d(0, 0, 0);

        for(Vector3d force : forces){
            if(force == null) continue;
            sum.add(force);
        }
        //Can be optimized
        return sum;
    }

        public static float calcAirDrag(float v, float p, float c, float A){
            return (float) -(.5*A*p*c*v*v);//Negative sign so I can add it | Opposite
        }//If you want more modular/dynamic

        //Does work
        public static Vector3f calcAirDrag(Vector3f v, float p, float c, float A){
           float constant = -(.5f*A*p*c);
           Vector3f drag =  v.mul(v.length(), new Vector3f())
                   .mul(constant);
           return drag;
        }

        public static Vector3f calcMagnus(Vector3f magnus, Vector3f airspeed){//vag or vog?
            float dragco = 3.5f * 1f/100000;
            return (magnus.cross(airspeed, new Vector3f())).mul(dragco);
        }

        @Deprecated//Dont use its useless and interferes with interfave
        public static float calcDropForce(float speedOfGrav, float mass){
            return (speedOfGrav * mass);
        }

        public static Vector3f calcDropForce(float mass, Vector3f dest){
        return Constants.FOG.mul(mass, dest);
    }

    public static double calcOrbitForce(double massOne, double massTwo, double distance){
        double force = (6.67e-11d * massOne * massTwo)/(distance * distance);
        return force;
    }

    public static float distanceFormula2F(Vector3f a, Vector3f b){
        float x = (float) Math.pow(a.x - b.x, 2);
        float y = (float) Math.pow(a.y - b.y, 2);
        return (float)Math.sqrt(x+y);
    }

        public static Vector3f calcThrustForce(Vector3f relativeSpeed){//uses horsepower or power apparently makes it different
            return relativeSpeed.mul(-128, new Vector3f()).add(new Vector3f(15400, 0, 0));
        }

        public static Vector3f triangulate(float hypotenuse, float theta){
            float x = (float) Math.cos(Math.toRadians(theta)) * hypotenuse;
            float y = (float) Math.sin(Math.toRadians(theta)) * hypotenuse;
            return new Vector3f(x, y, 0);
        }

    public static Vector3d triangulate(double hypotenuse, double theta){
        double x =  Math.cos(Math.toRadians(theta)) * hypotenuse;
        double y =  Math.sin(Math.toRadians(theta)) * hypotenuse;
        return new Vector3d(x, y, 0);
    }


    }

