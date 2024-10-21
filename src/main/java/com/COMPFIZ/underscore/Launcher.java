package com.COMPFIZ.underscore;

import com.COMPFIZ.core.EngineManager;
import com.COMPFIZ.core.Loader;
import com.COMPFIZ.core.WindowManager;
import com.COMPFIZ.core.mixins.Constants;
import com.COMPFIZ.core.shaders.ShaderForge;
import org.lwjgl.Version;

public class Launcher {//idea- make buffers that only allow inputs every other frame or every 3 frames so that the game feels better
    private static WindowManager winMan;
    private static Loader loader;
    private static examGame thisGame;//should be able to switch class to switch game
    private static ShaderForge shaderForge;


    public static void main(String[] args) throws Exception {
        System.out.println(Version.getVersion());
        winMan = new WindowManager(Constants.TITLE, 1600, 900, false);
        loader = new Loader();
        thisGame = new examGame();
        EngineManager EM = new EngineManager();
        try {
            EM.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WindowManager getWinMan() {
        return winMan;
    }

    public static examGame getThisGame() {
        return thisGame;
    }
    //Leave Loader in examGame class because I only use it there
    public static Loader getLoader() {
        return loader;
    }

}
