package com.COMPFIZ.core;

import com.COMPFIZ.core.mixins.Utils;
import com.COMPFIZ.core.models.StillModel;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;
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


    public StillModel loadinVAO(float[] positions, int[] indices) {
        if(positions.length%3 != 0){
            throw new IllegalArgumentException("positions must be a multiple of 3");
        }//Incase invalid amount of positions so Debug is easier
        int vaoID = createVAO();//creates and returns fully empty vaos ID for you to use
        StoreIndicesBuffer(indices);
        storeDataInAttriblist(positions, 0);//16 attribslots 0-15, 0 represents vertices in this system
        unbindVAO();//unbinds
        return new StillModel(vaoID, indices.length);
    }

    public StillModel loadOBJ(String filename){
        List<String> lines = Utils.readAllLines(filename);
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector3i> faces = new ArrayList<>();

        for(String line  : lines){
            String[] tokens = line.split("\\s+");
            switch(tokens[0]) {//.obj file encoding?
                case "v"://case vertices
                    Vector3f verticesVec = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3])
                    );
                    vertices.add(verticesVec);
                    break;
                case "vt"://case vertex textures
                    break;
                case "vn"://vertex normals
                    break;
                case "f"://faces

                    // If it's a quad, split it into two triangles
                    if(tokens.length == 5) {
                        processFaces(tokens[1], faces);
                        processFaces(tokens[2], faces);
                        processFaces(tokens[3], faces);

                        processFaces(tokens[1], faces);
                        processFaces(tokens[3], faces);
                        processFaces(tokens[4], faces);
                    } else{
                        processFaces(tokens[1], faces);
                        processFaces(tokens[2], faces);
                        processFaces(tokens[3], faces);
                    }
                    break;
                default:
                    break;
            }
        }
        List<Integer> indices = new ArrayList<>();
        float[] verticesArr = new float[vertices.size() * 3];
        int i = 0;
        for(Vector3f posi : vertices){
            verticesArr[i * 3] = posi.x;
            verticesArr[i * 3 + 1] = posi.y;
            verticesArr[i * 3 + 2] = posi.z;
            i++;
        }
        faces.stream().forEach(face -> indices.add(face.x));
        int[] indicesArr = indices.stream().mapToInt((Integer v) -> v).toArray();
        return loadinVAO(verticesArr, indicesArr);
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

   // public int loadTexture(String filename){

   // }

    private static void processFaces(String token, List<Vector3i> dest){//Or face singular, idk
        String[] lineToken = token.split("/");
        int length = lineToken.length;
        int position = -1;
        position = Integer.parseInt(lineToken[0])- 1;//faces start at 1
        Vector3i facesVec = new Vector3i(position, 0, 0);
        dest.add(facesVec);
        /**
        if(length > 1){
            String texCoord = lineToken[1];
            coords = texCoord.length() > 0 ? Integer.parseInt(texCoord)-1 : -1;
            //FIX this is in beta
            Vector3i facesVec = new Vector3i(position, coords, normal);
            dest.add(facesVec);
            if(length == 5) { // If it's a quad
                position = Integer.parseInt(lineToken[3])- 1;
                facesVec = new Vector3i(position, coords, normal);
                dest.add(facesVec);
            }
        }
         */
    }

    private void StoreIndicesBuffer(int[] indices){
        int vboID = GL30.glGenBuffers();//usage is the AttribNum | UsageNum
        vbos.add(vboID);
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buf = ConvertToIntBuffer(indices);//Now I have a buffer flipped to the read cycle
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, buf, GL30.GL_STATIC_DRAW);
    }

    private void unbindVAO(){
        GL30.glBindVertexArray(0);
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
