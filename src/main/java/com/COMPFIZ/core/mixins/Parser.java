package com.COMPFIZ.core.mixins;

import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Vector;

public class Parser {

    private static String filename;

    public void load(String filename) {
        this.filename = filename;
    }

    public static String findData(String signature){
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Class.forName(Utils.class.getName()).getResourceAsStream(filename)))){
            String line = bufferedReader.readLine();
            while(!line.equals(signature)){
                if(line == null) throw new Exception("Invalid signature");
                line = bufferedReader.readLine();
            }
            line.concat(bufferedReader.readLine());
            while(!line.equals(signature)){
                line.concat(bufferedReader.readLine());
            }
            return line;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Vector3f parsev(String signature, String filename){
        String target = findData(signature);
        String x, y, z;
        int ix = target.indexOf('X') + 4;
        int iy = target.indexOf('Y') + 4;
        int iz = target.indexOf('Z') + 4;
        x = target.charAt(ix) + "";
        y = target.charAt(iy) + "";
        z = target.charAt(iz) + "";
        while(target.charAt(ix) != ' '){
            x.concat(target.charAt(ix) + "");
        }while(target.charAt(iy) != ' '){
            y.concat(target.charAt(iy) + "");
        }while(target.charAt(iz) != ' '){
            z.concat(target.charAt(iz) + "");
        }
        return new Vector3f(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z));
    }
}
