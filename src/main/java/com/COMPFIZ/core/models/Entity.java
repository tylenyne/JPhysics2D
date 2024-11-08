package com.COMPFIZ.core.models;

import org.joml.Vector3f;

public class Entity extends StillModel {
    //Fields-
    public Vector3f position = new Vector3f(), rotation = new Vector3f(), scale = new Vector3f(1f);
    public Vector3f color = new Vector3f();
    public Descriptors desc;

    //More constructors but all Vec3fs
    public Entity(int vaoID, int vcount, Vector3f position, Vector3f rotation, float scale) {
        super(vaoID, vcount);
        this.position.set(position);
        this.rotation.set(rotation);
        this.scale = new Vector3f(scale);
    }

    public Entity(StillModel model, Vector3f position){
        super(model.getVaoID(), model.getVcount());
        this.position.set(position);
    }

    public Entity(Entity entity){
        super(entity.getVaoID(), entity.getVcount());
        this.position.set(entity.position);
        this.rotation.set(entity.rotation);
        this.scale.set(entity.scale);
        this.color.set(entity.color);
    }

    public Entity(StillModel model, Vector3f position, Vector3f color){
        super(model.getVaoID(), model.getVcount());
        this.position.set(position);
        this.color.set(color);
    }

    public Entity(StillModel stillModel, float offset, float height){
        super(stillModel.getVaoID(), stillModel.getVcount());
        position.x = offset;
        position.y = height;
    }

    public Entity(StillModel rawModel, Vector3f position, Vector3f rotation, Vector3f color, float scale) {
        super(rawModel);
        this.position = position;
        this.rotation = rotation;
        this.scale.set(scale);
        this.color.set(color);
    }

    public Entity(StillModel model) {
        super(model);
        position = new Vector3f(0,0,0);
        rotation = new Vector3f(0,0,0);
        scale = new Vector3f(1,1,1);
    }

    //Maybe rename getTranslation
    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public float getTotalScale() {//Avg Scale
        return scale.x+scale.y+scale.z;
    }

    public void incPos(float dx, float dy, float dz) {
        position.add(dx, dy, dz);
    }

    public void inRot(float rx, float ry, float rz) {
        rotation.add(rx, ry, rz);
    }

    public void incScale(float x) {
        scale.add(x, x, x);
    }

    public void setPos(float x, float y, float z) {
        position.set(x, y, z);
    }

    public void setPos(Vector3f pos) {
        position = pos;
    }

    public void setColor(float r, float g, float b) {
        color.set(r, g, b);
    }
}
