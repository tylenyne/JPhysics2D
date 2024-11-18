package com.COMPFIZ.core.mixins.maths;

import org.joml.Matrix3d;
import org.joml.Matrix4d;
import org.joml.Matrix4f;

public class Transformation {
    public Matrix4d mat = new Matrix4d();

    public Transformation(Matrix4d matrix) {
       mat.set(matrix);
    }

}
