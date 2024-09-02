package com.COMPFIZ.core;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;
//Large Spaces represent Section
public class WindowManager {
    //Fields-
    public static final float FOV = ((float)Math.toRadians(60));
    public static final float zNEAR = 0.01f;

    public static final float zFAR = 1000f;

    private final String Title;

    private int width, height;
    private long window;

    private boolean resize, vSync;

    private final Matrix4f projectionMatrix;


    //Methods-
    public WindowManager(String title, int width, int height, boolean vSync) {
        Title = title;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
        projectionMatrix = new Matrix4f();
    }

    public void init(){
        GLFWErrorCallback.createPrint(System.err).set();

        if(!GLFW.glfwInit()){
            throw new IllegalStateException("Unable to initialize GLFW");
        }


        //WindowHints apply window attributes
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);//Imma just copyPaste chatGPTs explanation GLFW.GLFW_VISIBLE: This hint can be set to either GLFW.GLFW_TRUE or GLFW.GLFW_FALSE.GLFW.GLFW_TRUE: If set to GLFW_TRUE, the window will be created in a visible state, meaning it will be shown immediately after creation.GLFW.GLFW_FALSE: If set to GLFW_FALSE, the window will be created in a hidden state. This can be useful if you want to perform some initialization or setup before showing the window to the user.
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);// Major/greatest version that opengl context can be
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);// Minor/least version that opengl context can be
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE); //Sets the GLFW Window Hint/setting to the Core profile which allow mainly only Modern opengl Features
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);//This is self explanatory toggle a windowHint to true

        boolean maximized = false;
        if(width == 0 | height == 0){
            width = 100;
            height = 100;
            GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);
            maximized = true;
        }
        window = GLFW.glfwCreateWindow(width, height, Title, MemoryUtil.NULL, MemoryUtil.NULL);
        if(window == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW Window");
        }


            GLFW.glfwSetFramebufferSizeCallback(window, (window, width, height) ->
            { //window is also different, probably supplied by glfw
                this.width = width;//different width, lambda function
                this.height = height;//different height, lambda function
                this.setResize(true);
            });

        GLFW.glfwSetKeyCallback(window, (window, key, scanc, action, mods) ->
                {
                    if(key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE){
                        GLFW.glfwSetWindowShouldClose(window, true);
                    }

                });

        if(maximized){
            GLFW.glfwMaximizeWindow(window);
        }
        else{
            GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            GLFW.glfwSetWindowPos(window, ((vidMode.width()-width)/2), ((vidMode.height()-height)/2));
        }

        GLFW.glfwMakeContextCurrent(window);

        if(this.isvSync()){
            GLFW.glfwSwapInterval(1);
        }

        //This is exactly where glfwshowWindow should be, if no work copy/paste the rest of this method to showWindow
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BACK);



    }


    public boolean isResize() {
        return resize;
    }

    public void setResize(boolean resize) {
        this.resize = resize;
    }

    public boolean isvSync() {
        return vSync;
    }

    public void setvSync(boolean vSync) {
        this.vSync = vSync;
    }

    public boolean isKeyPressed(int keycode){
        return GLFW.glfwGetKey(window, keycode) ==GLFW.GLFW_PRESS;
    }

    public boolean windowShouldClose(){
        return GLFW.glfwWindowShouldClose(window);
    }

    public String getTitle(){
        return Title;
    }

    public void setTitle(String Title){
        GLFW.glfwSetWindowTitle(window, Title);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getWindow() {
        return window;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }



    public void update(){
        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();
    }

    public Matrix4f updateProjectionMatrix(){
        float aspectratio = (float)width/height;
        return projectionMatrix.setPerspective(FOV, aspectratio, zNEAR, zFAR);
    }
    //@Overwrite
    public Matrix4f updateProjectionMatrix(Matrix4f matrix, int width, int height){
        float aspectratio = (float)width/height;
        return matrix.setPerspective(FOV, aspectratio, zNEAR, zFAR);
    }

    public void showWindow(){
        GLFW.glfwShowWindow(window);
    }

    public void cleanup(){
        GLFW.glfwDestroyWindow(window);
    }

}
