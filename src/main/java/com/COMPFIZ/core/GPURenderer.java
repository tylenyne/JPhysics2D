package com.COMPFIZ.core;

import com.COMPFIZ.core.models.Entity;
import com.COMPFIZ.core.models.StillModel;
import com.COMPFIZ.core.shaders.BasicColor;
import com.COMPFIZ.core.shaders.ShaderForge;
import com.COMPFIZ.core.shaders.Shaders;
import com.COMPFIZ.underscore.Launcher;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class GPURenderer {
    public float red, green, blue, alpha;
    private WindowManager winman;
    private Shaders color;

    //Methods-
    public GPURenderer() {
        winman = Launcher.getWinMan();
    }

    public void init(Shaders[] shaders) throws Exception{
        ShaderForge._init();
        color = new BasicColor();

        for(Shaders shader : shaders) {
            shader.load();
        }
        color.load();
    }
    public void prepare(){
        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        //Line above Renders color to Screen so maybe prepare means prepare screen IDK
    }

    public void render(StillModel[] model, Shaders shader) {//New Param Shader
        //this.prepare();
        ShaderForge.connect();

        for(int i = 0; model[i] != null; i++) {//Make it so if there are duplicate models you only bind to one and process shaders during
            GL30.glBindVertexArray(model[i].getVaoID());
            GL30.glEnableVertexAttribArray(0);
            shader.use((Entity) model[i]);//for now
            setEntityColor((Entity) model[i]);
            GL30.glDrawElements(GL30.GL_TRIANGLES, model[i].getVcount(), GL11.GL_UNSIGNED_INT, 0);
            GL30.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);
        }
        ShaderForge.disconnect();
    }
    //static
    public static void setClearColor(float r, float g, float b, float a){
        GL30.glClearColor(r, g, b, a);
    }


    public void setEntityColor(Entity entity) {
        color.use(entity);
    }


    public void cleanup(){
        ShaderForge.cleanup();
    }


}
