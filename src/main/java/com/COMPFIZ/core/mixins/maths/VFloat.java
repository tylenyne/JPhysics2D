package com.COMPFIZ.core.mixins.maths;

public class VFloat {
    public float x;
    public float y;
    public float z;

    /**
     *
     * This class does not do
     * subtraction or division because
     * they are bad operators and are dumdum
     */

    public VFloat(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;//Usually zero for this use case
    }

    public VFloat add(VFloat vfloat){
        this.x += vfloat.x;
        this.y += vfloat.y;
        this.z += vfloat.z;

        return this;
    }

    public VFloat add(float x, float y, float z){
        this.x += x;
        this.y += y;
        this.z += z;

        return this;
    }

    public VFloat mul(float s){
        this.x *= s;
        this.y *= s;
        this.z *= s;

        return this;
    }

    public float dot(VFloat vfloat){
        return (vfloat.x * x) + (vfloat.y * y) + (vfloat.z * z);
    }
    //If it was a wideVector(matrix) it would return a vector
    public float cross(){
        return 0;
    }

    public VFloat reciprocal(){
        this.x = 1/x;
        this.y = 1/y;
        this.z = 1/z;
        return this;
    }

    public VFloat toNegative(){
        return this.mul(-1);
    }

    /**
     * Float and Mag I dont understand
     */



}
