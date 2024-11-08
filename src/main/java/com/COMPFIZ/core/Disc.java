package com.COMPFIZ.core;

import java.io.IOException;

public interface Disc {

    void init() throws Exception;
    void input();
    void update(float interval);
    void render();
    void cleanup();

    GPURenderer getRenderer();
}
