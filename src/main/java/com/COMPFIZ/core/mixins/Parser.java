package com.COMPFIZ.core.mixins;

import com.COMPFIZ.core.attributes.Physics;
import com.COMPFIZ.core.models.Entity;
import org.joml.Vector3d;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Vector;

public class Parser {

    private static Entity entity;

    public static void load(Entity e) {
        entity = e;
    }

    public static String findData(String signature, String filename){
            try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Class.forName(Utils.class.getName()).getResourceAsStream(filename)))){
                String line = bufferedReader.readLine();
                while(!line.contains(signature)){
                    if(line == null) throw new Exception("Invalid signature");
                    line = bufferedReader.readLine();
                }
                line = bufferedReader.readLine() + bufferedReader.readLine();
                System.out.println(line);
                return line + " ";//to indicate eof
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void parse(String flag, String filename){
        String target = findData(flag, filename);
        String x, y, z;
        int ix = target.indexOf('X') + 3;
        int iy = target.indexOf('Y') + 3;
        int iz = target.indexOf('Z') + 3;
        x = target.charAt(ix) + "";
        y = target.charAt(iy) + "";
        z = target.charAt(iz) + "";
        ix++; iy++; iz++;
        while(target.charAt(ix) != ' '){
            x = x.concat(target.charAt(ix) + "");
            ix++;
        }while(target.charAt(iy) != ' '){
            y = y.concat(target.charAt(iy) + "");
            iy++;
        }while(target.charAt(iz) != ' '){
            z = z.concat(target.charAt(iz) + "");
            iz++;
        }


        Vector3d position = new Vector3d(Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z)).mul(1000d);
        int ivx = target.indexOf("VX") + 3;
        int ivy = target.indexOf("VY") + 3;
        int ivz = target.indexOf("VZ") + 3;
        x = target.charAt(ivx) + "";
        y = target.charAt(ivy) + "";
        z = target.charAt(ivz) + "";
        ivx++; ivy++; ivz++;
        while(target.charAt(ivx) != ' '){
            x = x.concat(target.charAt(ivx) + "");
            ivx++;
        }while(target.charAt(ivy) != ' '){
            y = y.concat(target.charAt(ivy) + "");
            ivy++;
        }while(target.charAt(ivz) != ' '){
            z = z.concat(target.charAt(ivz) + "");
            ivz++;
        }
        Vector3d velocity = new Vector3d(Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z)).mul(1000d);
        entity.position.set(position);
        ((Physics) entity.physics).v.set(velocity);
        entity = null;
    }
}
