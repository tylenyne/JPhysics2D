package com.COMPFIZ.core.shaders;

import com.COMPFIZ.core.models.Entity;

public abstract class Shaders {
    public abstract void use();
    public abstract void use(Entity entity);
    public abstract void load() throws Exception;
}
