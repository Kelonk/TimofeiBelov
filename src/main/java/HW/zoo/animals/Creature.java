package HW.zoo.animals;

import HW.zoo.enums.Food;
import HW.zoo.enums.MoveTypes;

public interface Creature {
    void Eat(Food food);
    void Move(MoveTypes move);
}
