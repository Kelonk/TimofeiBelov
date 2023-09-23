package model;

public class Customer {
    private int freeSpace = 10000;

    public Customer(int freeSpace) {
        this.freeSpace = freeSpace;
    }

    public void buy(FurnitureInterface furniture){
        if (furniture.getArea() < freeSpace){
            freeSpace -= furniture.getArea();
            System.out.printf("New furniture was acquired with area of %s\n", furniture.getArea());
        } else {
            System.out.printf("Furniture with area of %s wasn't acquired\nLacks %s of area\n", furniture.getArea(), furniture.getArea() - freeSpace);
        }
    }
}
