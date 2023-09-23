import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.print("Please enter direction data: ");
        Scanner scanner = new Scanner(System.in);
        String data = scanner.nextLine();
        switch (data){
            case "":
                System.out.print("Empty data detected!");
                break;
            case "Error":
                System.out.print("Program terminate");
                break;
            default:
                break;
        }
        /*
        if (data.isEmpty()){
            System.out.print("Empty data detected!");
            return;
        } else if (data.equals("Error")) {
            System.out.print("Program terminate");
            return;
        }
        */
        for (int i = data.length(); i <= data.length() * 2; i++) {
            System.out.printf("with data: %2$s, i = %1$s%n", i, data);
        }
    }
}