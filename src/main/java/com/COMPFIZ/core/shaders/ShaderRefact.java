package com.COMPFIZ.core.shaders;

import java.io.IOException;
import java.util.ArrayList;

public class ShaderRefact {//ShaderManager for multiple Shaders
    private ArrayList<ShaderType> staticShaders;

    public ShaderRefact(){
         staticShaders = new ArrayList<ShaderType>();
    }

    public ShaderRefact(ShaderType shader){
        staticShaders = new ArrayList<ShaderType>();
        staticShaders.add(shader);
    }

    public void addStaticShader(ShaderType shader){
        staticShaders.add(shader);
    }

    public void create() throws Exception{

    }
}
