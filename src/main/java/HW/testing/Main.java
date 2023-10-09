package HW.testing;

import HW.testing.enums.SortMethods;
import HW.testing.models.BubbleSort;
import HW.testing.models.MergeSort;
import HW.testing.models.SortAction;
import HW.testing.models.SortService;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        SortAction sort1 = new BubbleSort(10);
        SortAction sort2 = new BubbleSort(3);
        SortAction sort3 = new BubbleSort(2);
        SortAction sort4 = new MergeSort(100);
        SortAction sort5 = new MergeSort(15);

        SortService sortService1 = new SortService(List.of(
                sort3,
                sort5,
                sort4
        ));
        SortService sortService2 = new SortService(List.of(
                sort2,
                sort1
        ));

        List<Integer> listToSort = List.of(10, 5, 10, 30, 55, -15, 30, 100000);

        System.out.println("Initial list is:");
        for(Integer value: listToSort){
            System.out.print(value + " ");
        }
        System.out.println();

        try{
            // decided not to make many tries and itteration through a list of dictionaries
            // with tries information (to wrap try-catch in it)
            // because how they work will be tested in Tests anyway...
            System.out.println("Sort with Buddle method:");
            for(Integer value: sortService2.sortWith(SortMethods.Bubble, listToSort)){
                System.out.print(value + " ");
            }
            System.out.println();
        } catch (Exception e){
            System.out.println("Following exception happened while main logic: " + e.getMessage());
        }
    }
}
