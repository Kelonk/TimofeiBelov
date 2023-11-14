package HW.IO;

import HW.IO.enums.ConsoleArgs;
import HW.IO.models.DefaultIOOperations;
import HW.IO.models.IOOperator;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

  public static void main(String[] args) {
    Map<String,String> map =
        IntStream.range(0, args.length/2)
            .boxed()
            .collect(Collectors.toMap(i->args[2*i], i->args[2*i+1]));
    if (!Arrays.stream(ConsoleArgs.values()).allMatch(e -> map.containsKey(e.key))) {
      System.out.println("Not all required arguments are provided, required:");
      Arrays.stream(ConsoleArgs.values()).forEach(e -> System.out.println(e.key));
      System.exit(-1);
    }

    IOOperator operator = new IOOperator(map.get(ConsoleArgs.From.key), map.get(ConsoleArgs.To.key));
    try {
      Set<String> filter = DefaultIOOperations.readAsSet(map.get(ConsoleArgs.Filter.key));
      Predicate<String> predicate = DefaultIOOperations.checkContains(filter);
      operator.rewriteWithFilter(predicate);
    } catch (IOException e) {
      System.out.println("Failed to execute");
    }
    System.out.println("Main finished without error");
  }
}
