package model;

public class Table extends Furniture {
    @Override
    public String getName() {
        return "Table";
    }

    public Table(){
        super();
    }

    public Table(int width, int length){
        super(width, length);
    }

    public Table(int width, int length, boolean isCircular){
        super(width, length, isCircular);
    }
}
