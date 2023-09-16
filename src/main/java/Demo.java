package main.java;

import main.java.model.Table;

public class Demo {

    public static void main(String[] args) {
        Table MyTable = new Table(50, 150);
        System.out.printf("Table with width %s and length %s has area of %s%n", MyTable.width, MyTable.length, MyTable.getArea());
    }
    // docket - ?
    // maven - ?
}
