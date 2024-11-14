package com.COMPFIZ.core.mixins.maths;

import com.COMPFIZ.core.models.Entity;
import org.joml.Vector3f;

public class ViewMatrix {
    float pitch, yaw, roll;

    public void follow(Entity entity){
        //pitch = -entity.position.x;
        //yaw = -entity.position.y;
        //roll = -entity.position.z;
    }
}
