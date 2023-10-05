package HW.zoo.enums;

public enum DietTypes {
    Herbivore("herbivore"),
    Carnivore("carnivore"),
    Omnivore("omnivore"),
    Specific("specific diet");

    private final String printName;

    DietTypes(String printName){
        this.printName = printName;
    }

    public String getName(){
        return this.printName;
    }
}
