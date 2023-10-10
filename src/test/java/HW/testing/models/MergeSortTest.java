package HW.testing.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {

    @Test
    void sort() {
        MergeSort mergeSort = new MergeSort(0);
        MergeSort mergeSort1 = new MergeSort(10);
        List<Integer> integerList = List.of(1, 2 ,3, -10, 33);
        List<Integer> listSorted = integerList.stream().sorted().toList();

        Assertions.assertThrows(IllegalArgumentException.class, () -> mergeSort.sort(integerList));
        Assertions.assertDoesNotThrow(() -> mergeSort1.sort(integerList)); // asserts initial array wasn't changed
        Assertions.assertArrayEquals(listSorted.toArray(), mergeSort1.sort(integerList).toArray());
    }
}