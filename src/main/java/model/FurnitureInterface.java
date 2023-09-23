package model;

public interface FurnitureInterface {
    public int getArea(String metric);

    default int getArea(){
        return getArea("x");
    }
}
