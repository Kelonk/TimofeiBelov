package HW.IO.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class DefaultIOOperations {
  private static final Logger logger = Logger.getLogger(DefaultIOOperations.class.getSimpleName());

  public static Set<String> readAsSet(String source) throws IOException {
    logger.info("Request to words from file: " + source);
    Set<String> read = new HashSet<>();
    try (var reader = new BufferedReader(new FileReader(getPath(source)))) {
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

  public static String getPath(String path) throws IOException {
    if (Path.of(path).isAbsolute()) {
      return path;
    }

    URL resource;
    if ((resource = DefaultIOOperations.class.getClassLoader().getResource(path)) != null) {
      return resource.getPath();
    } else if ((resource = DefaultIOOperations.class.getResource(path)) != null) {
      return  resource.getPath();
    }
    throw new IOException("Can't find file by path: " + path);
  }
}
