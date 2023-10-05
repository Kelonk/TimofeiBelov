package HW.zoo;

import HW.zoo.animals.Animal;
import HW.zoo.enums.DietTypes;
import HW.zoo.enums.Food;
import HW.zoo.enums.MoveTypes;

public class CheckScript {
    public static void main(String[] args){
        Animal dolphin = new Animal("dolphin", DietTypes.Specific, new Food[]{Food.Fish}, new MoveTypes[]{MoveTypes.Water});
        Animal tiger = new Animal("tiger", DietTypes.Specific, new Food[]{Food.Beef}, new MoveTypes[]{MoveTypes.Land});
        Animal horse = new Animal("horse", DietTypes.Herbivore, null, new MoveTypes[]{MoveTypes.Land});
        Animal eagle = new Animal("eagle", DietTypes.Carnivore, null, new MoveTypes[]{MoveTypes.Air, MoveTypes.Land});
        Animal camel = new Animal("camel", DietTypes.Herbivore, null, new MoveTypes[]{MoveTypes.Land});

        dolphin.move(MoveTypes.Water);
        dolphin.eat(Food.Grass);
        dolphin.eat(Food.Fish);

        tiger.move(MoveTypes.Water);
        tiger.move(MoveTypes.Land);
        tiger.eat(Food.Beef);
        tiger.eat(Food.Meat);

        horse.move(MoveTypes.Land);
        horse.eat(Food.Grass);

        eagle.move(MoveTypes.Air);
        eagle.eat(Food.Meat);
        eagle.eat(Food.Beef);
        eagle.eat(Food.Fish);

        camel.eat(Food.Grass);
    }
}
