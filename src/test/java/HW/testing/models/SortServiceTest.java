package HW.testing.models;

import HW.testing.enums.SortMethods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SortServiceTest {

    @Test
    void sortWith() {
        List<SortAction> sortActions = List.of(new MergeSort(5), new BubbleSort(3),
                new MergeSort(10), new BubbleSort(5));
        SortService sortService = new SortService(sortActions);
        SortService sortServiceNull = new SortService(null);
        List<Integer> integerList = List.of(1, 2 ,3, -10, 33, 80);
        List<Integer> integerListCopy = new ArrayList<>();
        integerListCopy.addAll(integerList);

        List<Integer> listSorted = integerList.stream().sorted().toList();

        // unable to sort due to sortActions limit
        Assertions.assertThrows(RuntimeException.class, () -> sortService.sortWith(SortMethods.Bubble, integerList));
        // assert works fine when it's able to
        Assertions.assertArrayEquals(listSorted.toArray(),
                sortService.sortWith(SortMethods.Merge, integerList).toArray());
        Assertions.assertArrayEquals(integerList.toArray(), integerListCopy.toArray());
        // assert work with null list as argument or its sortActions
        Assertions.assertThrows(NullPointerException.class,
                () -> sortServiceNull.sortWith(SortMethods.Bubble, integerList));
        Assertions.assertNull(sortService.sortWith(SortMethods.Bubble, null));
    }
}