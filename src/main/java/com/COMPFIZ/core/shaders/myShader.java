package com.COMPFIZ.core.shaders;

import com.COMPFIZ.core.mixins.maths.Maths;
import com.COMPFIZ.core.mixins.maths.Transformation;
import com.COMPFIZ.core.models.Entity;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL30;

public class myShader extends Shaders{
    private int TMLocation;

    @Override
    public void use(Entity entity) {
        System.out.println(GL30.glGetError());
        Transformation matrix = Maths.createTM(entity.position, entity.rotation, entity.scale);
        ShaderForge.setUniformMatrix(TMLocation, matrix.mat);
    }

    public void load() throws Exception {
        TMLocation = ShaderForge.createUniform("transformationMatrix");
    }

    public void use(){

    }
}
