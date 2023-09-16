package main.java.model;

public class Table {

    public int width = 100;
    public int length = 100;

    public Table(){
        System.out.println("New table appeared in this world");
    }

    public Table(int width, int length){
        System.out.println("New table appeared in this world");
        this.width = width;
        this.length = length;
    }

    public int getArea(){
        return width * length;
    }
}
