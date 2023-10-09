package HW.testing.models;

import HW.testing.enums.SortMethods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BubbleSortTest {

    @Test
    void sort() {
        BubbleSort bubbleSort = new BubbleSort(0);
        BubbleSort bubbleSort1 = new BubbleSort(10);
        List<Integer> integerList = List.of(1, 2 ,3, -10, 33);
        List<Integer> listSorted = integerList.stream().sorted().toList();

        Assertions.assertThrows(IllegalArgumentException.class, () -> bubbleSort.sort(integerList));
        Assertions.assertDoesNotThrow(() -> bubbleSort1.sort(integerList)); // asserts initial array wasn't changed
        Assertions.assertArrayEquals(listSorted.toArray(), bubbleSort1.sort(integerList).toArray());
    }
}