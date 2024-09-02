package com.COMPFIZ.core;

import com.COMPFIZ.underscore.Launcher;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class GPURenderer {
    public float red, green, blue, alpha;

    //Methods-
    public GPURenderer() {
        WindowManager winman = Launcher.getWinMan();
    }
    public void prepare(){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        //Line above Renders color to Screen so maybe prepare means prepare screen IDK
    }
    public void render(RawModel model){
        this.prepare();
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVcount());
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    public static void setClearColor(float r, float g, float b, float a){
        GL11.glClearColor(r,g,b,a);
    }

}
