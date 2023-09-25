package model.zoo.animals;

import model.zoo.enums.Food;
import model.zoo.enums.MoveTypes;

public interface Creature {
    void Eat(Food food);
    void Move(MoveTypes move);
}
