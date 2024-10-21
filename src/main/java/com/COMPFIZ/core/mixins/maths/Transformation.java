package com.COMPFIZ.core.mixins.maths;

import org.joml.Matrix4f;

public class Transformation {
    public Matrix4f mat;

    public Transformation(Matrix4f matrix) {
        this.mat = matrix;
    }

}
