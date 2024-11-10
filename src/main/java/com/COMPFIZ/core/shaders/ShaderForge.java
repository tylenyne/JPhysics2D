package com.COMPFIZ.core.shaders;

import com.COMPFIZ.core.GPURenderer;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ShaderForge {

        private static int programID;//Should be final
        private static int vertexShaderID, fragmentShaderID;

        public static void _init() throws Exception {
            programID = GL20.glCreateProgram();
            if(programID == 0){
                throw new Exception("Failed to generate shader");
            }
            createVertexShader(loadResource("/shader/vertex.vsh"));
            createFragmentShader(loadResource("/shader/fragment.fsh"));
            link();
        }

        public static void createVertexShader(String shaderCode) throws Exception {
            vertexShaderID = createShader(shaderCode, GL20.GL_VERTEX_SHADER);
        }

        public static void createFragmentShader(String shaderCode) throws Exception {
            fragmentShaderID = createShader(shaderCode, GL20.GL_FRAGMENT_SHADER);
        }

        private static int createShader(String shaderCode, int shaderType) throws Exception {
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

        private static void link() throws Exception {
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
        public static int loadinUniform(String uniformName) throws Exception {
            int uniformLOC = GL20.glGetUniformLocation(programID, uniformName);//int Pointer
            if (uniformLOC < 0) {
                throw new Exception("Could not find Uniform " + uniformName);
            }
            return uniformLOC;
        }
        //In videos they were protected
        static int getUniformLOC(String uniformName) {
            return GL20.glGetUniformLocation(programID, uniformName);
        }

        public static void setUniform1f(int location, float value) {
            GL30.glUniform1f(location, value);
        }

        public static void setUniformv3f(int location, Vector3f value) {
            GL30.glUniform3f(location, value.x, value.y, value.z);
        }

        public static void setUniform1i(int location, int value) {
            GL30.glUniform1i(location, value);
        }

        public static void setUniformMatrix(int location, Matrix4f matrix) {
            try(MemoryStack stack = MemoryStack.stackPush()){
                GL20.glUniformMatrix4fv(location, false, matrix.get(stack.mallocFloat(16)));
            }
        }

        public static String loadResource(String filename) throws Exception {
            String result;
            try(InputStream in = GPURenderer.class.getResourceAsStream(filename);
                Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name())) {
                result = scanner.useDelimiter("\\A").next();
            }
            return result;
    }


        public static void connect(){
            GL20.glUseProgram(programID);
        }

        public static void disconnect(){
            GL20.glUseProgram(0);
        }


        public static void cleanup(){
            disconnect();
            if (programID != 0)
                GL20.glDeleteProgram(programID);
        }


    }
