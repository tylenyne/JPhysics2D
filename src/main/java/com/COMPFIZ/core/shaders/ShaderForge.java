package com.COMPFIZ.core.shaders;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class ShaderForge {

        private final int programID;
        private int vertexShaderID, fragmentShaderID;

        public ShaderForge() throws Exception {
            programID = GL20.glCreateProgram();
            if(programID == 0) {
                throw new Exception("Failed to generate shader");
            }

        }

        public void createVertexShader(String shaderCode) throws Exception {
            vertexShaderID = createShader(shaderCode, GL20.GL_VERTEX_SHADER);
        }

        public void createFragmentShader(String shaderCode) throws Exception {
            fragmentShaderID = createShader(shaderCode, GL20.GL_FRAGMENT_SHADER);
        }

        public int createShader(String shaderCode, int shaderType) throws Exception {
            int shaderID = GL20.glCreateShader(shaderType);
            if(shaderID == 0)
                throw new Exception("Failed to generate shader of type " + shaderType);

            GL20.glShaderSource(shaderID, shaderCode);
            GL20.glCompileShader(shaderID);

            if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == 0) {
                throw new Exception("Failed to compile shader of type " + shaderType + ": " + GL20.glGetShaderInfoLog(shaderID, 1024));
            }

            GL20.glAttachShader(programID, shaderID);

            return shaderID;
        }

        public void link() throws Exception {
            GL21.glLinkProgram(programID);
            if(GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == 0) {
                throw new Exception("Failed to link shader of type : " + GL20.glGetProgramInfoLog(programID, 1024));
            }
            if(vertexShaderID != 0)
                GL20.glDetachShader(programID, vertexShaderID);

            if(fragmentShaderID != 0)
                GL20.glDetachShader(programID, fragmentShaderID);

            GL20.glValidateProgram(programID);
            if(GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == 0) {
                throw new Exception("Failed to validate shader. Error: " + GL20.glGetProgramInfoLog(programID, 1024));
            }

        }

        //Gets Uniforms
        public int createUniform(String uniformName) throws Exception {
            int uniformLOC = GL20.glGetUniformLocation(programID, uniformName);//int Pointer
            if (uniformLOC < 0) {
                throw new Exception("Could not find Uniform " + uniformName);
            }
            return uniformLOC;
        }
        //In videos they were protected
        public int getUniformLOC(String uniformName) {
            return GL20.glGetUniformLocation(programID, uniformName);
        }

        public void setUniform1f(int location, float value) {
            GL30.glUniform1f(location, value);
        }

        public void setUniformv3f(int location, Vector3f value) {
            GL30.glUniform3f(location, value.x, value.y, value.z);
        }

        public void setUniform1i(int location, int value) {
            GL30.glUniform1i(location, value);
        }

        public void setUniformMatrix(int location, Matrix4f matrix) {
            try(MemoryStack stack = MemoryStack.stackPush()){
                GL20.glUniformMatrix4fv(location, false, matrix.get(stack.mallocFloat(16)));
            }
        }


        public void bind(){
            GL20.glUseProgram(programID);
        }

        public void unbind(){
            GL20.glUseProgram(0);
        }


        public void cleanup(){
            unbind();
            if (programID != 0)
                GL20.glDeleteProgram(programID);
        }


    }
