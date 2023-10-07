package HW.testing.models;

import HW.testing.enums.SortMethods;

import java.util.ArrayList;
import java.util.List;

public interface SortAction <T extends Comparable> {
    default List<T> getCopy(List<T> listToCopy){
        if (listToCopy == null) { return null; }
        List<T> newList = new ArrayList<>();
        newList.addAll(listToCopy);
        return newList;
    }

    SortMethods method();
    Integer getLimit();
    List<T> sort(List<T> listToSort);
}
