package com.COMPFIZ.core.utils;

import org.lwjgl.system.MemoryUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {
    public static FloatBuffer StoreDataInFloatBuffer(float[] data){
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data).flip();
        return buffer;
    }

    public static IntBuffer StoreDataInIntBuffer(int[] data){
        IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
        buffer.put(data).flip();
        return buffer;
    }

    public static String loadResource(String filename) throws Exception{
        String result;
        try(InputStream in = Utils.class.getResourceAsStream(filename);
            Scanner scanMan = new Scanner(in, StandardCharsets.UTF_8.name())){
            result = scanMan.useDelimiter("\\A").next();
        }

        return result;
    }

    public static List<String> readAllLines(String filename){
        List<String> list = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Class.forName(Utils.class.getName()).getResourceAsStream(filename)))){
            String line;
            while((line = bufferedReader.readLine()) != null){
                list.add(line);
            }
        } catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return list;
    }

    public static void renderModel(int id){

    }
}
