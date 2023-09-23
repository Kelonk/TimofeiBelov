package model;

public class Chair extends Furniture {

    @Override
    public String getName() {
        return "Chair";
    }

    public Chair(){
        super();
    }

    public Chair(int width, int length){
        super(width, length);
    }

    public Chair(int width, int length, boolean isCircular){
        super(width, length, isCircular);
    }


}
