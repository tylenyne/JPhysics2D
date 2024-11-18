package com.COMPFIZ.core.mixins.maths;

import com.COMPFIZ.core.models.Entity;
import org.joml.Matrix4d;
import org.joml.Vector3f;

public class ViewMatrix {
    public Matrix4d mat = new Matrix4d();

    public ViewMatrix(Matrix4d vm) {
        mat.set(vm);
    }
}
