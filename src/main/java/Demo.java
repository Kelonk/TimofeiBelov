import model.*;
import java.util.Scanner;

public class Demo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int mode = scanner.nextInt();
        switch (mode){
            case 0:
                Table table = new Table(100, 200);
                Chair chair = new Chair(50, 70, true);
                Customer customer = new Customer(10000);

                customer.buy(table);
                customer.buy(chair);
                break;
            case 1:
                Student student = new Student(80);
                Subject subject = new Subject("Somebody", 50);
                if (University.canStudy(student, subject)){
                    System.out.println("This student can study");
                } else {
                    System.out.println("This student can't study");
                }
                break;
        }
        /*
       Table table = new Table(100, 200);
       Chair chair = new Chair(50, 70, true);
       Customer customer = new Customer(10000);

       customer.buy(table);
       customer.buy(chair);
       */
        Student student = new Student(80);
        Subject subject = new Subject("Somebody", 50);
        if (University.canStudy(student, subject)){
            System.out.println("This student can study");
        } else {
            System.out.println("This student can't study");
        }
    }
    // docket - ?
    // maven - ?
}
