package com.COMPFIZ.core;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    //Fields-
    private List<Integer> vaos = new ArrayList<Integer>();//Stores IDs
    private List<Integer> vbos = new ArrayList<Integer>();//Stores IDs
    public RawModel loadVAO(float[] positions){
        if(positions.length%3 != 0){
            throw new IllegalArgumentException("positions must be a multiple of 3");
        }//Incase invalid amount of positions so Debug is easier
        int vaoID = createVAO();//creates and returns fully empty vaos ID for you to use
        storeDataInAttriblist(positions, 0);//16 attribslots 0-15, 0 represents vertices in this system
        unbindVAO();//unbinds
        return new RawModel(vaoID, positions.length/3);
    }
    private int createVAO(){
        int vaoID = GL30.glGenVertexArrays();//creates vaoID with openGL, binds it, and returns the ID
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID; //returns ID
    }

    private void storeDataInAttriblist(float[] data, int usage){
        int vboID = GL15.glGenBuffers();//usage is the AttribNum | UsageNum
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);//binds
        FloatBuffer buf = ConvertToFloatBuffer(data);//Now I have a buffer flipped to the read cycle
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buf, GL15.GL_STATIC_DRAW);//Writes the data to binded vboID
        GL20.glVertexAttribPointer(usage, 3, GL11.GL_FLOAT, false, 0, 0);//puts data in VBO slot//xyz==3
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);//unbinds
    }//Last param slot tells what you are going to do with the data | STATIC_DRAW
    private void unbindVAO(){
        GL30.glBindVertexArray(0);//Binds it by passing in 0 to bind function or by binding it to 0/null/Nothing
    }

    private FloatBuffer ConvertToFloatBuffer(float[] data){
        FloatBuffer buf = MemoryUtil.memAllocFloat(data.length);
        buf.put(data);//adds data to buffer
        buf.flip();//Switches between able to be read/writ
        return buf;
    }

    public void cleanup(){
        for(int vao : vaos){
            GL30.glDeleteVertexArrays(vao);
        }
        for(int vbo : vbos){
            GL30.glDeleteBuffers(vbo);
        }
    }


}
