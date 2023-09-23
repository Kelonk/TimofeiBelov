package model;

public class Rectangle implements Shape{
    private float posX;
    private float posY;
    private float sideLength;
    private float rotation;

    public Rectangle(float posX, float posY, float sideLength, float rotation) {
        this.posX = posX;
        this.posY = posY;
        this.sideLength = sideLength;
        this.rotation = rotation;
    }


    @Override
    public boolean checkOverlap(float posX, float posY) {
        float posX_new = (float) ((posX - this.posX) * Math.cos(rotation) - (posY - this.posY) * Math.sin(rotation));
        float posY_new = (float) ((posX - this.posX) * Math.sin(rotation) + (posY - this.posY) * Math.cos(rotation));

        if (Math.abs(posX_new / sideLength) <= 1 && Math.abs(posY_new / sideLength) <= 1){
            return true;
        } else {
            return false;
        }
    }
}
