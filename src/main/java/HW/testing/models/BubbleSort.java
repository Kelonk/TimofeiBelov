package HW.testing.models;

import HW.testing.enums.SortMethods;

import java.util.List;

public class BubbleSort implements SortAction{
    private final Integer limit;

    public BubbleSort(int limit){
        this.limit = limit;
    }

    @Override
    public Integer getLimit() {
        return limit;
    }

    @Override
    public SortMethods method() {
        return SortMethods.Bubble;
    }

    @Override
    public <T extends Comparable> List<T> sort(List<T> listToSort) {
        List<T> copiedList = getCopy(listToSort);
        if (copiedList == null) { return null; }
        if (copiedList.size() > limit) { throw new IllegalArgumentException("List size exceeded sort method limit"); }

        for (int i = 0; i < copiedList.size(); i++) {
            for (int j = i + 1; j < copiedList.size(); j++) {
                if (copiedList.get(i).compareTo(copiedList.get(j)) > 0) {
                    T temp = copiedList.get(i);
                    copiedList.set(i, copiedList.get(j));
                    copiedList.set(j, temp);
                }
            }
        }
        return copiedList;
    }
}
