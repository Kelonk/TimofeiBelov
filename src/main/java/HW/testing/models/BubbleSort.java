package HW.testing.models;

import HW.testing.enums.SortMethods;

import java.util.List;

public class BubbleSort implements SortAction<Integer>{
    private final Integer limit;

    BubbleSort(int limit){
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
    public List<Integer> sort(List<Integer> listToSort) {
        List<Integer> copiedList = getCopy(listToSort);
        if (copiedList == null) { return null; }

        for (int i = 0; i < copiedList.size(); i++) {
            for (int j = i + 1; j < copiedList.size(); j++) {
                if (copiedList.get(i) > copiedList.get(j)) {
                    int temp = copiedList.get(i);
                    copiedList.set(i, copiedList.get(j));
                    copiedList.set(j, temp);
                }
            }
        }
        return copiedList;
    }
}
