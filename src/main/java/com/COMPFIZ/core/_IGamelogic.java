package com.COMPFIZ.core;

public interface _IGamelogic {

    void init() throws Exception;
    void input();
    void update(float interval);
    void render();
    void cleanup();

}
