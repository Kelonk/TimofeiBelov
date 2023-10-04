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

        dolphin.Move(MoveTypes.Water);
        dolphin.Eat(Food.Grass);
        dolphin.Eat(Food.Fish);

        tiger.Move(MoveTypes.Water);
        tiger.Move(MoveTypes.Land);
        tiger.Eat(Food.Beef);
        tiger.Eat(Food.Meat);

        horse.Move(MoveTypes.Land);
        horse.Eat(Food.Grass);

        eagle.Move(MoveTypes.Air);
        eagle.Eat(Food.Meat);
        eagle.Eat(Food.Beef);
        eagle.Eat(Food.Fish);

        camel.Eat(Food.Grass);
    }
}
