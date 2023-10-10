package HW.zoo.animals;

import HW.zoo.enums.Food;
import HW.zoo.enums.MoveTypes;

public interface Creature {
    void eat(Food food);
    void move(MoveTypes move);
}
