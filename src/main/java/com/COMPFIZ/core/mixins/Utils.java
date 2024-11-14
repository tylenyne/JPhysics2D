package com.COMPFIZ.core.mixins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
//Renaming/organizing off this package might be necesarry
public class Utils {

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

    //Maths
    }

