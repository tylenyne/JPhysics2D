package com.COMPFIZ.core.shaders;

import com.COMPFIZ.core.Camera;
import com.COMPFIZ.core.models.Entity;

public class BasicColor extends Shaders{
    private int colorptr;


    @Override
    public void set(Entity entity) {

    }

    @Override
    public void apply(Entity entity) {
        ShaderForge.setUniformv3f(colorptr, entity.color);
    }

    @Override
    public void apply(Entity entity, Camera camera) {

    }

    @Override
    public void load() throws Exception {
        colorptr = ShaderForge.loadinUniform("uColor");
    }
}
