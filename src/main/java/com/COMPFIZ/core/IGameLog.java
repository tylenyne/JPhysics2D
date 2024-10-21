package com.COMPFIZ.core;

public interface IGameLog {

    void init() throws Exception;
    void input();
    void update(float interval);
    void render();
    void cleanup();

    GPURenderer getRenderer();
}
