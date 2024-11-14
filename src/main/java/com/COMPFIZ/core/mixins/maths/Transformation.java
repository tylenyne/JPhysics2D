package com.COMPFIZ.core.mixins.maths;

import org.joml.Matrix3d;
import org.joml.Matrix4d;
import org.joml.Matrix4f;

public class Transformation {
    public Matrix4f mat = new Matrix4f();

    public Transformation(Matrix4f matrix) {
        mat.set(matrix);
    }

    public Transformation(Matrix4d matrix) {
       mat.set(matrix);
    }

}
