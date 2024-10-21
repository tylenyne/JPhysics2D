package com.COMPFIZ.core.shaders;

import com.COMPFIZ.core.IGameLog;
import com.COMPFIZ.core.mixins.maths.Maths;
import com.COMPFIZ.core.mixins.maths.Transformation;
import com.COMPFIZ.core.models.Entity;
import org.joml.Matrix4f;

public class ShaderTM implements ShaderType {//Remove inheritance

    private int TMlocation;
    private ShaderForge shaderForge;

    public ShaderTM(IGameLog game) throws Exception{//Maybe make shaders static or something like that -using the class
        shaderForge = game.getRenderer().getShaderForge();//There should only be 2 shader managers running it would be nice if there were one
    }

    public void create() throws Exception{
        TMlocation = shaderForge.createUniform("transformationMatrix");
    }

    public void getALLUniformLocations() {
        TMlocation = shaderForge.getUniformLOC("transformationMatrix");
    }

    public void setTransformationMatrix(Matrix4f transformationMatrix) {
        shaderForge.setUniformMatrix(TMlocation, transformationMatrix);
    }

    @Override
    public void use(Entity entity) {
        Transformation matrix = Maths.createTM(entity.getPosition(), entity.getRotation(), entity.getScale());
        this.setTransformationMatrix(matrix.mat);
    }
}
