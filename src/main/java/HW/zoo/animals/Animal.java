package HW.zoo.animals;

import HW.zoo.enums.DietTypes;
import HW.zoo.enums.Food;
import HW.zoo.enums.MoveTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Animal implements Creature{
    public final String name;
    public final String speciesName;
    public final DietTypes diet;
    public final List<Food> foodLimit;
    public final List<MoveTypes> moveLimit;

    public Animal(String name, String speciesName, DietTypes diet, Food[] foodLimit, MoveTypes[] moveLimit) {
        if (diet == DietTypes.Specific && foodLimit == null){
            throw new RuntimeException("Animal can't eat nothing");
        }
        if (moveLimit == null){
            throw new RuntimeException("Movement type is required for Animal");
        }

        this.name = name;
        this.speciesName = speciesName;
        this.diet = diet;
        this.foodLimit = foodLimit == null ? null : Arrays.asList(foodLimit);
        this.moveLimit = moveLimit == null ? null : Arrays.asList(moveLimit);
    }

    public Animal(String speciesName, DietTypes diet, Food[] foodLimit, MoveTypes[] moveLimit){
        this(null, speciesName, diet, foodLimit, moveLimit);
    }

    @Override
    public void Eat(Food food) {
        if (!CanEat(food)){
            PrintRefuseToEat(food);
            return;
        }
        ArrayList<String> phrases = new ArrayList<>();
        phrases.add("%s quickly eats %s and looks at you asking for more%n");
        phrases.add("%s eats %s%n");
        phrases.add("%s eats %s and runs away%n");
        phrases.add("%s is unsure but still decides to eat this %s%n");

        System.out.printf(phrases.get(ThreadLocalRandom.current().nextInt(phrases.size())),
                GetUpperName(true),
                food.GetName());
    }

    @Override
    public void Move(MoveTypes move) {
        if (!moveLimit.contains(move)){
            PrintRefuseToMove(move);
            return;
        }
        switch (move){
            case Air -> {
                System.out.printf("%s flies%n", GetUpperName(true));
            }
            case Land -> {
                System.out.printf("%s walks%n", GetUpperName(true));
            }
            case Water -> {
                System.out.printf("%s swims%n", GetUpperName(true));
            }
        }
    }

    public void Move(){
        if (moveLimit != null){
            Move(moveLimit.get(0));
        } else {
            System.out.printf("%s can't move!%n", GetUpperName(true));
        }
    }

    public boolean CanEat(Food food){
        boolean answer = false;
        switch (diet){
            case Carnivore -> {
                answer = food.IsMeat();
            }
            case Herbivore -> {
                answer = !food.IsMeat();
            }
            case Omnivore -> {
                answer = true;
            }
            case Specific -> {
                answer = foodLimit.contains(food);
            }
        }
        return answer;
    }

    private void PrintRefuseToEat(Food food){
        System.out.printf("%s refuses to eat %s -> It %s%n", GetUpperName(true), food.GetName(),
                (diet.equals(DietTypes.Specific) ? "has " : "is ") + diet.GetName());
    }

    private void PrintRefuseToMove(MoveTypes move){
        System.out.printf("%s can't %s%n", GetUpperName(true), move.GetName());
    }

    private String GetUpperName(boolean speciesName){
        String toReturn;
        if (speciesName && this.speciesName != null){
            toReturn = this.speciesName;
        } else if (name != null) {
            toReturn = name;
        } else {
            toReturn = "NoName";
        }
        return toReturn.substring(0, 1).toUpperCase() + toReturn.substring(1);
    }
}
