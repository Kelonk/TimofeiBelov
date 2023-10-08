package HW.testing.models;

import HW.testing.enums.SortMethods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SortActionTest {

    @Test
    void getCopy() {
        SortAction sortAction = new SortAction() {
            @Override
            public SortMethods method() {
                return null;
            }

            @Override
            public Integer getLimit() {
                return null;
            }

            @Override
            public <T extends Comparable> List<T> sort(List<T> listToSort) {
                return null;
            }
        };
        List<Integer> integerList = List.of(1, 2 ,3, -10, 33);
        List<Integer> returnList = sortAction.getCopy(integerList);

        Assertions.assertNotEquals(integerList, returnList);
        Assertions.assertArrayEquals(integerList.toArray(), returnList.toArray());
    }
}