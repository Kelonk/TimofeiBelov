package HW.testing.models;

import HW.testing.enums.SortMethods;

import java.util.ArrayList;
import java.util.List;

public interface SortAction {
    default <T> List<T> getCopy(List<T> listToCopy){
        if (listToCopy == null) { return null; }
        List<T> newList = new ArrayList<>();
        newList.addAll(listToCopy);
        return newList;
    }

    SortMethods method();
    Integer getLimit();
    <T extends Comparable> List<T> sort(List<T> listToSort);
}
