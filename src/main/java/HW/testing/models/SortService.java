package HW.testing.models;

import HW.testing.enums.SortMethods;

import java.util.List;

public class SortService {
    private final List<SortAction> sortActions;

    public SortService(List<SortAction> sortActions) {
        this.sortActions = sortActions;
    }

    public <T extends Comparable> List<T> sortWith(SortMethods method, List<T> listToSort){
        for (SortAction action : sortActions) {
            if (action.method().equals(method) /* && action.getLimit() <= listToSort.size() */){
                // comment on condition because it will prevent exception from triggering
                try {
                    return action.sort(listToSort);
                } catch (RuntimeException e){
                    System.out.println("Following error happened while handling list to sort: " + e.getMessage());
                }
            }
        }
        throw new RuntimeException("SortService was unable to sort your list");
    }
}
