package HW.zoo.enums;

public enum Food {
    Fish(true, "fish"),
    Meat(true, "meat"),
    Beef(true, "beef"),
    Grass(false, "grass");

    private final boolean meat;
    private final String printName;

    Food(boolean meat, String printName){
        this.meat = meat;
        this.printName = printName;
    }

    public boolean isMeat(){
        return this.meat;
    }

    public String getName(){
        return this.printName;
    }
}
