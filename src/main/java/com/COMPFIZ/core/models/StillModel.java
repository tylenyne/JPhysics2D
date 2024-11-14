package com.COMPFIZ.core.models;

public class StillModel {
    private int vaoID;
    private int vcount;

    public StillModel(int vaoID, int vcount) {
        this.vaoID = vaoID;
        this.vcount = vcount;
        System.out.println("Succesfully Created New Model | VAOID: " + vaoID + "\t VERTICES: " + vcount);
    }

    public StillModel(StillModel rawModel) {
        vaoID = rawModel.getVaoID();
        vcount = rawModel.getVcount();
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVcount() {
        return vcount;
    }
}
