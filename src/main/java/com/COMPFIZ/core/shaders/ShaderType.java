package com.COMPFIZ.core.shaders;

import com.COMPFIZ.core.models.Entity;

public interface ShaderType {//Could just be abstract

    public void getALLUniformLocations();

    public void use(Entity entity);//Make a default use method

    public void create() throws Exception;//IDK about this method
}
