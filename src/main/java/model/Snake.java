package model;

public class Snake {
    public float length;
    public float circumference;
    public float lifespan;

    public Snake(float length, float circumference, float lifespan) {
        this.length = length;
        this.circumference = circumference;
        this.lifespan = lifespan;
    }

    public float getLength() {
        return length;
    }

    public float getCircumference() {
        return circumference;
    }

    public float getLifespan() {
        return lifespan;
    }
}
