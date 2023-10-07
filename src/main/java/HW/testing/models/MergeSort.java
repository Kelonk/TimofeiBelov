package HW.testing.models;

import HW.testing.enums.SortMethods;

import java.util.Collections;
import java.util.List;

public class MergeSort implements SortAction<Integer>{
    private final Integer limit;

    MergeSort(int limit){
        this.limit = limit;
    }

    @Override
    public Integer getLimit() {
        return limit;
    }

    @Override
    public SortMethods method() {
        return SortMethods.Merge;
    }

    @Override
    public List<Integer> sort(List<Integer> listToSort) {
        List<Integer> copiedList = getCopy(listToSort);
        if (copiedList == null) { return null; }

        Collections.sort(copiedList);
        return  copiedList;
    }
}
