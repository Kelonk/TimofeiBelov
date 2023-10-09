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

    public Animal(String name, String speciesName, DietTypes diet, List<Food> foodLimit, List<MoveTypes> moveLimit) {
        if (diet == DietTypes.Specific && foodLimit == null){
            throw new RuntimeException("Animal can't eat nothing");
        }
        if (moveLimit == null){
            throw new RuntimeException("Movement type is required for Animal");
        }

        this.name = name;
        this.speciesName = speciesName;
        this.diet = diet;
        this.foodLimit = foodLimit;
        this.moveLimit = moveLimit;
    }

    public Animal(String speciesName, DietTypes diet, List<Food> foodLimit, List<MoveTypes> moveLimit){
        this(null, speciesName, diet, foodLimit, moveLimit);
    }

    @Override
    public void eat(Food food) {
        if (!canEat(food)){
            printRefuseToEat(food);
            return;
        }
        ArrayList<String> phrases = new ArrayList<>();
        phrases.add("%s quickly eats %s and looks at you asking for more%n");
        phrases.add("%s eats %s%n");
        phrases.add("%s eats %s and runs away%n");
        phrases.add("%s is unsure but still decides to eat this %s%n");

        System.out.printf(phrases.get(ThreadLocalRandom.current().nextInt(phrases.size())),
                getUpperName(true),
                food.getName());
    }

    @Override
    public void move(MoveTypes move) {
        if (!moveLimit.contains(move)){
            printRefuseToMove(move);
            return;
        }
        switch (move){
            case Air -> {
                System.out.printf("%s flies%n", getUpperName(true));
            }
            case Land -> {
                System.out.printf("%s walks%n", getUpperName(true));
            }
            case Water -> {
                System.out.printf("%s swims%n", getUpperName(true));
            }
        }
    }

    public void move(){
        if (moveLimit != null){
            move(moveLimit.get(0));
        } else {
            System.out.printf("%s can't move!%n", getUpperName(true));
        }
    }

    public boolean canEat(Food food){
        boolean answer = false;
        switch (diet){
            case Carnivore -> {
                answer = food.isMeat();
            }
            case Herbivore -> {
                answer = !food.isMeat();
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

    private void printRefuseToEat(Food food){
        System.out.printf("%s refuses to eat %s -> It %s%n", getUpperName(true), food.getName(),
                (diet.equals(DietTypes.Specific) ? "has " : "is ") + diet.getName());
    }

    private void printRefuseToMove(MoveTypes move){
        System.out.printf("%s can't %s%n", getUpperName(true), move.getName());
    }

    private String getUpperName(boolean speciesName){
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
