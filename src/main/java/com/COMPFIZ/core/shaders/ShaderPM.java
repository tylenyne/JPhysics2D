package com.COMPFIZ.core.shaders;

import com.COMPFIZ.core.IGameLog;
import com.COMPFIZ.core.models.Entity;

public class ShaderPM implements ShaderType{

    private ShaderForge shaderForge;
    private int PMlocation;


    public ShaderPM(IGameLog game) {

    }
    @Override
    public void getALLUniformLocations() {
        PMlocation = shaderForge.getUniformLOC("projectionMatrix");
    }

    @Override
    public void use(Entity entity) {

    }

    @Override
    public void create() throws Exception {
        shaderForge.createUniform("projectionMatrix");
    }
}
