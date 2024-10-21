package com.COMPFIZ.core;

import com.COMPFIZ.core.models.StillModel;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    //Fields-
    private List<Integer> vaos = new ArrayList<Integer>();//Stores IDs
    private List<Integer> vbos = new ArrayList<Integer>();//Stores IDs
    public StillModel loadVAO(float[] positions, int[] indices) {
        if(positions.length%3 != 0){
            throw new IllegalArgumentException("positions must be a multiple of 3");
        }//Incase invalid amount of positions so Debug is easier
        int vaoID = createVAO();//creates and returns fully empty vaos ID for you to use
        StoreIndicesBuffer(indices);
        storeDataInAttriblist(positions, 0);//16 attribslots 0-15, 0 represents vertices in this system
        unbindVAO();//unbinds
        return new StillModel(vaoID, positions.length/3);
    }
    private int createVAO(){
        int vaoID = GL30.glGenVertexArrays();//creates vaoID with openGL, binds it, and returns the ID
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        System.out.println(GL30.glGetError());
        return vaoID; //returns ID
    }

    private void storeDataInAttriblist(float[] data, int usage){
        int vboID = GL30.glGenBuffers();//usage is the AttribNum | UsageNum
        vbos.add(vboID);
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboID);
        System.out.println(GL30.glGetError());//binds
        FloatBuffer buf = ConvertToFloatBuffer(data);//Now I have a buffer flipped to the read cycle
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, buf, GL30.GL_STATIC_DRAW);
        System.out.println(GL30.glGetError());//Writes the data to binded vboID
        GL30.glVertexAttribPointer(usage, 3, GL30.GL_FLOAT, false, 0, 0);
        System.out.println(GL30.glGetError());//puts data in VBO slot//xyz==3
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
        System.out.println(GL30.glGetError());//unbinds
    }//Last param slot tells what you are going to do with the data | STATIC_DRAW

    private void StoreIndicesBuffer(int[] indices){
        int vboID = GL30.glGenBuffers();//usage is the AttribNum | UsageNum
        vbos.add(vboID);
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, vboID);
        System.out.println(GL30.glGetError());//binds
        IntBuffer buf = ConvertToIntBuffer(indices);//Now I have a buffer flipped to the read cycle
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, buf, GL30.GL_STATIC_DRAW);

    }
    private void unbindVAO(){
        GL30.glBindVertexArray(0);
        GL30.glGetError();//Binds it by passing in 0 to bind function or by binding it to 0/null/Nothing
    }

    private FloatBuffer ConvertToFloatBuffer(float[] data){
        FloatBuffer buf = MemoryUtil.memAllocFloat(data.length);
        buf.put(data);//adds data to buffer
        buf.flip();//Switches between able to be read/writ
        return buf;
    }

    private IntBuffer ConvertToIntBuffer(int[] data){
        IntBuffer buf = MemoryUtil.memAllocInt(data.length);
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
