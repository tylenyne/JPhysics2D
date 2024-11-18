package com.COMPFIZ.core.shaders;

import com.COMPFIZ.core.Camera;
import com.COMPFIZ.core.mixins.maths.Maths;
import com.COMPFIZ.core.mixins.maths.Transformation;
import com.COMPFIZ.core.mixins.maths.ViewMatrix;
import com.COMPFIZ.core.models.Entity;
import org.joml.Matrix4f;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;

public class myShader extends Shaders{
    private int TMLocation;
    private int VMLocation;
    private int lightLocation;
    private int lightColorLocation;

    @Override
    public void apply(Entity entity, Camera camera){
        Transformation matrix = Maths.createTM(entity.position, entity.rotation, entity.scale);
        ShaderForge.setUniformMatrix(TMLocation, matrix.mat);
        ViewMatrix mtrx = Maths.createVM(camera.position, new Vector3d(camera.zoom), camera.field);
        ShaderForge.setUniformMatrix(VMLocation, mtrx.mat);
    }

    public void set(Entity entity){
        ShaderForge.setUniformv3f(lightLocation, new Vector3f(.8f, .8f, 0));
    }

    @Override
    public void apply(Entity entity) {

    }

    public void load() throws Exception {
        TMLocation = ShaderForge.loadinUniform("transformationMatrix");
        VMLocation = ShaderForge.loadinUniform("viewMatrix");
        lightLocation = ShaderForge.loadinUniform("light");
    }
}
