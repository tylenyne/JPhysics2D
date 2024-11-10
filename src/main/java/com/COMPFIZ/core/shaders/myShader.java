package com.COMPFIZ.core.shaders;

import com.COMPFIZ.core.mixins.maths.Maths;
import com.COMPFIZ.core.mixins.maths.Transformation;
import com.COMPFIZ.core.models.Entity;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;

public class myShader extends Shaders{
    private int TMLocation;
    private int lightLocation;

    @Override
    public void apply(Entity entity) {
        System.out.println(GL30.glGetError());
        Transformation matrix = Maths.createTM(entity.position, entity.rotation, entity.scale);
        ShaderForge.setUniformMatrix(TMLocation, matrix.mat);
    }

    public void set(Entity entity){
        ShaderForge.setUniformv3f(lightLocation, new Vector3f(0f, 0f, 0));
    }

    public void load() throws Exception {
        TMLocation = ShaderForge.loadinUniform("transformationMatrix");
        lightLocation = ShaderForge.loadinUniform("light");
    }
}
