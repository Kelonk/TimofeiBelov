package model;

public class Circle implements Shape{
    private float posX;
    private float posY;
    private float radius;

    public Circle(float posX, float posY, float radius) {
        this.posX = posX;
        this.posY = posY;
        this.radius = radius;
    }

    @Override
    public boolean checkOverlap(float posX, float posY){
        return (posX - this.posX) < Math.pow(radius, 2);
    }
}
