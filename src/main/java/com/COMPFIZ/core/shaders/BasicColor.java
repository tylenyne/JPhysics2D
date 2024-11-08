package com.COMPFIZ.core.shaders;

import com.COMPFIZ.core.models.Entity;

public class BasicColor extends Shaders{
    private int colorptr;


    @Override
    public void use() {

    }

    @Override
    public void use(Entity entity) {
        ShaderForge.setUniformv3f(colorptr, entity.color);
    }

    @Override
    public void load() throws Exception {
        colorptr = ShaderForge.createUniform("uColor");
    }
}
