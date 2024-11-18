package com.COMPFIZ.core.shaders;

import com.COMPFIZ.core.Camera;
import com.COMPFIZ.core.models.Entity;

public abstract class Shaders {
    public abstract void set(Entity entity);
    public abstract void apply(Entity entity);
    public abstract void apply(Entity entity, Camera camera);
    public abstract void load() throws Exception;
}
