package com.COMPFIZ.core;

import com.COMPFIZ.core.mixins.Constants;
import com.COMPFIZ.underscore.Launcher;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

public class EventHandler {
    public static final long SECOND = 1000000000L/4;//1 second counting in nanoseconds
    public static final float FRAMERATE = 120; //Absolute time because only calling game attribs like update(); each defined frame which correspond to time
    public static double totalTime = 0, absoluteTotalTime = 0;//Outside the games FrameCalls totaltime | Total time entire program has been running
    private static int fps;
    private static float interval = 1000;
    private static float frametime = 1.0f/FRAMERATE;
    public static float allFrames;
    //Static

    public static boolean isRunning;

    private WindowManager winMan;
    private Disc gameLogic;
    private GLFWErrorCallback errorCallback;

    public static boolean[] eventStream = new boolean[1];

    private void init() throws Exception{
        GLFW.glfwSetErrorCallback(this.errorCallback = GLFWErrorCallback.createPrint(System.err));
        winMan = Launcher.getWinMan();
        gameLogic = Launcher.getThisGame();
        winMan.init();//Trying to initialize window but have control of when to show it. Not really important but its practice
        winMan.showWindow();//Make a function for this string of functions especially if  it gets bigger
        gameLogic.init();
    }

    public static int getFps() {
        return fps;
    }

    public static void setFps(int fps) {
        EventHandler.fps = fps;
    }

    public void start() throws Exception{
        this.init();
        if(isRunning){
            return;
        }
        run();
    }

    public void run() {
        this.isRunning = true;
        int frames = 0;
        long frameCounter = 0;
        long lastTime = System.nanoTime();//100000000L
        double unproccessedTime = 0D;
        allFrames = 0;
//MainGameLoop-
        while(isRunning){//Running to pass the time
            boolean render = false;//Update synonym
            long startTime = System.nanoTime();
            long passedTime = startTime-lastTime;//As a nanosecond long
            lastTime = startTime;

            unproccessedTime+=passedTime/(double) SECOND; //5000000000L -> 5 seconds
            frameCounter+=passedTime;
            totalTime =  allFrames*frametime;//120*1/120 = 1 sec


            input();//Outside render so you can input between frames

            while(unproccessedTime > frametime){//If unproccesed is 1/120 it would runonce
                render = true;
                unproccessedTime-=frametime;

                if(winMan.windowShouldClose()){
                    this.endProgram();
                }

                if(frameCounter >= SECOND){//FPS
                    this.setFps(frames);
                    winMan.setTitle(Constants.TITLE +" Fps:"  + getFps());
                    frames = 0;
                    frameCounter = 0L;
                    eventStream = new boolean[1];
                }
            }

            if(render){//Happens every time
                update(interval);
                render();
                frames++;
                allFrames++;
            }

        }
        this.cleanup();
    }

    private void endProgram(){
        if(!this.isRunning){
            return;
        }
        this.isRunning = false;
    }

    private void input(){
        gameLogic.input();
    }

    private void render(){
        gameLogic.render();
        winMan.update();//update
    }

    private void update(float interval) {
        gameLogic.update(interval);
    }

    private void cleanup(){
        winMan.cleanup();
        gameLogic.cleanup();
        errorCallback.free();
        GLFW.glfwTerminate();
    }

}