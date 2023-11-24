package HW.IO.models;

import java.io.*;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class IOOperator {
  private static final Logger logger = Logger.getLogger(IOOperator.class.getSimpleName());

  private final String source;
  private final String destination;

  public IOOperator(String source, String destination) {
    this.source = source;
    this.destination = destination;
  }

  public void rewriteWithFilter(Predicate<String> predicate) throws IOException {
    logger.info("Request to rewrite with filter, from: " + source + " to: " + destination);
    try (
        var from = new BufferedReader(new FileReader(DefaultIOOperations.getPath(source)));
        var to = new PrintWriter(new FileWriter(DefaultIOOperations.getPath(destination)))
    ) {
      from.lines()
          .filter(Predicate.not(predicate))
          .forEachOrdered(to::println);
    } catch (IOException e) {
      logger.info("Failed to rewrite with filter, from: " + source + " to: " + destination);
      throw e;
    }
  }
}
