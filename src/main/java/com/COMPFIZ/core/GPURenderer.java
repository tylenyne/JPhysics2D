package com.COMPFIZ.core;

import com.COMPFIZ.core.mixins.Constants;
import com.COMPFIZ.core.models.Entity;
import com.COMPFIZ.core.models.StillModel;
import com.COMPFIZ.core.shaders.ShaderForge;
import com.COMPFIZ.core.shaders.ShaderTM;
import com.COMPFIZ.core.shaders.ShaderType;
import com.COMPFIZ.underscore.Launcher;
import org.lwjgl.opengl.GL30;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class GPURenderer {
    public float red, green, blue, alpha;
    private WindowManager winman;
    private ShaderForge shaderForge;
    private ShaderTM TM;

    //Methods-
    public GPURenderer() {
        winman = Launcher.getWinMan();
    }

    public void init() throws Exception{
        shaderForge = new ShaderForge();
        shaderForge.createVertexShader(this.loadResource("/shader/vertex.vsh"));
        shaderForge.createFragmentShader(this.loadResource("/shader/fragment.fsh"));
        shaderForge.link();

        shaderForge.createUniform("transformationMatrix");
        shaderForge.createUniform("uColor");
    }
    public void prepare(){
        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        //Line above Renders color to Screen so maybe prepare means prepare screen IDK
    }

    public void render(StillModel[] model, ShaderType shaderType) {//New Param Shader
        //this.prepare();
        shaderType.getALLUniformLocations();
        for(int i = 0; model[i] != null; i++) {//Make it so if there are duplicate models you only bind to one and process shaders during

            shaderForge.bind();
            GL30.glBindVertexArray(model[i].getVaoID());
            GL30.glEnableVertexAttribArray(0);
            shaderType.use((Entity) model[i]);//for now
            setEntityColor((Entity) model[i]);
            GL30.glDrawArrays(GL30.GL_TRIANGLES, 0, model[i].getVcount());
            GL30.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);
            shaderForge.unbind();
        }
    }
    //static
    public static void setClearColor(float r, float g, float b, float a){
        GL30.glClearColor(r, g, b, a);
    }

    public String loadResource(String filename) throws Exception {
        String result;
        try(InputStream in = GPURenderer.class.getResourceAsStream(filename);
            Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name())) {
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    }

    public void setEntityColor(Entity entity) {
        int colorptr = shaderForge.getUniformLOC("uColor");
        shaderForge.setUniformv3f(colorptr, entity.color);
    }

    public ShaderForge getShaderForge(){
        return shaderForge;
    }

    public void cleanup(){
        shaderForge.cleanup();
    }


}
