package com.COMPFIZ.underscore;
import com.COMPFIZ.core.WindowManager;
import org.lwjgl.Version;
import java.net.http.HttpClient;

public class Launcher {
    public static void main(String[] args){
        System.out.println(Version.getVersion());
        WindowManager winMan = new WindowManager("_UNDERSCORE", 1600, 900, false);
        winMan.init();
        while(!winMan.windowShouldClose()){
            winMan.update();
        }
        winMan.cleanup();
    }
}
