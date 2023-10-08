package HW.testing.models;

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
        List<Integer> integerListCopy = new ArrayList<>();
        integerListCopy.addAll(integerList);
        List<Integer> listSorted = integerList.stream().sorted().toList();

        Assertions.assertThrows(IllegalArgumentException.class, () -> bubbleSort.sort(integerList));
        Assertions.assertArrayEquals(listSorted.toArray(), bubbleSort1.sort(integerList).toArray());
        Assertions.assertArrayEquals(integerList.toArray(), integerListCopy.toArray()); // asserts initial array wasn't changed
    }
}