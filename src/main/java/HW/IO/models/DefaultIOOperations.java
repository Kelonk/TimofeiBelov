package HW.IO.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class DefaultIOOperations {
  private static final Logger logger = Logger.getLogger(DefaultIOOperations.class.getSimpleName());

  public static Set<String> readAsSet(String source) throws IOException {
    logger.info("Request to words from file: " + source);
    Set<String> read = new HashSet<>();
    try (var reader = new BufferedReader(new FileReader(getResourcePath(source)))) {
      reader.lines()
          .forEach(e -> read.addAll(
              List.of(e
                  .replace("\n", "")
                  .split(" "))
          ));
    } catch (IOException e) {
      logger.info("Failed to read from file: " + source);
      throw e;
    }
    return read;
  }

  public static Predicate<String> checkContains(Set<String> listToCheck){
    return (line) -> listToCheck.stream().anyMatch(line::contains);
  }

  public static String getResourcePath(String path) {
    return DefaultIOOperations.class.getClassLoader().getResource(path).getPath();
  }
}
