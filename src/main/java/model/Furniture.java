package model;

abstract class Furniture implements FurnitureInterface {
    protected int width = 100;
    protected int length = 100;
    protected boolean circular = false;

    public abstract String getName();
    public Furniture(){
        printAppearance();
    }

    public Furniture(int width, int length){
        printAppearance();
        this.width = width;
        this.length = length;
    }

    public Furniture(int width, int length, boolean isCircular){
        printAppearance();
        this.width = width;
        this.length = length;
        this.circular = isCircular;
    }

    private void printAppearance(){
        System.out.printf("New %s appeared in this world%n", getName());
    }

    @Override
    public int getArea(String metric){
        return !circular ? width * length : (int) (Math.PI * Math.pow(metric.equals("x") ? width : length, 2));
    }
}
