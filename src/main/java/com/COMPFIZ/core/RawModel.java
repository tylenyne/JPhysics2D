package com.COMPFIZ.core;

public class RawModel {
    private static int vaoID;
    private static int vcount;

    public RawModel(int vaoID, int vcount) {
        this.vaoID = vaoID;
        this.vcount = vcount;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVcount() {
        return vcount;
    }
}
