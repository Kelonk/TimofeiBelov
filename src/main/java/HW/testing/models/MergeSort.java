package HW.testing.models;

import HW.testing.enums.SortMethods;
import java.util.Collections;
import java.util.List;

public class MergeSort implements SortAction{
    private final Integer limit;

    public MergeSort(int limit){
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
    public <T extends Comparable> List<T> sort(List<T> listToSort) {
        List<T> copiedList = getCopy(listToSort);
        if (copiedList == null) { return null; }
        if (copiedList.size() > limit) { throw new IllegalArgumentException("List size exceeded sort method limit"); }

        Collections.sort(copiedList);
        return  copiedList;
    }
}
