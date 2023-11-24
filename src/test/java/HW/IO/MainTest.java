package HW.IO;

import HW.IO.enums.ConsoleArgs;
import HW.IO.models.DefaultIOOperations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
  private record returnInfo(Map<ConsoleArgs, File> consoleArgs, List<String> checkInfo) {
  }

  private static Random random = new Random();

  private returnInfo generateFilterTrio(File directory) throws IOException {
    File from = File.createTempFile("from", ".txt", directory);
    File filter = File.createTempFile("filter", ".txt", directory);
    File to = File.createTempFile("into", ".txt", directory);
    from.deleteOnExit();
    filter.deleteOnExit();
    to.deleteOnExit();

    var content = random.ints(100, 1000000, 9999999)
        .distinct().boxed()
        .map(Object::toString).toList();
    List<String> lines =
        IntStream.range(0, content.size() / 3)
            .boxed()
            .map(e -> String.join(" ", content.get(e * 3), content.get(e * 3 + 1), content.get(e * 3 + 2)))
            .toList();
    List<String> leftLines = lines.stream().collect(Collectors.toList());
    List<String> filterLines = new ArrayList<>();

    var toDelete = random.ints(lines.size() / 10, 0, lines.size())
        .distinct().boxed().toList();
    toDelete.forEach(e -> {
      var elements = lines.get(e).split(" ");
      filterLines.add(elements[random.nextInt(0, elements.length)]);
      leftLines.remove(lines.get(e));
    });

    try (
        var fromWriter = new PrintWriter(new FileWriter(from));
        var filterWriter = new PrintWriter(new FileWriter(filter))
    ) {
      lines.forEach(fromWriter::println);
      filterLines.forEach(filterWriter::println);
    }

    return new returnInfo(Map.of(ConsoleArgs.From, from, ConsoleArgs.Filter, filter, ConsoleArgs.To, to), leftLines);
  }

  @Test
  void checkOfMainFilter() throws IOException {
    Path dir = Files.createTempDirectory("tempDir");
    dir.toFile().deleteOnExit();
    var test = generateFilterTrio(dir.toFile());
    Main.main(test.consoleArgs.entrySet().stream().<String>mapMulti((entry, consumer) -> {
      consumer.accept(entry.getKey().key);
      consumer.accept(entry.getValue().getPath());
    }).toArray(String[]::new));
    try (var to = new BufferedReader(
        new FileReader(
            test.consoleArgs.get(ConsoleArgs.To).getPath()
        )
    )) {
      Assertions.assertArrayEquals(test.checkInfo.toArray(), to.lines().toArray());
    }
  }

  @Test
  void checkOfMainFilterConcurrent() throws IOException, InterruptedException {
    Path dir = Files.createTempDirectory("tempDir");
    dir.toFile().deleteOnExit();
    int threads = 10;
    ExecutorService service = Executors.newFixedThreadPool(threads);
    CountDownLatch latch = new CountDownLatch(threads);
    IntStream.range(0, threads).forEach(value -> {
          CompletableFuture.supplyAsync(() -> {
            try {
              returnInfo test = generateFilterTrio(dir.toFile());
              Main.main(test.consoleArgs.entrySet().stream().<String>mapMulti((entry, consumer) -> {
                consumer.accept(entry.getKey().key);
                consumer.accept(entry.getValue().getPath());
              }).toArray(String[]::new));
              return test;
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
          }, service).thenAccept((returnInfo info) -> {
            try (var to = new BufferedReader(
                new FileReader(
                    info.consoleArgs.get(ConsoleArgs.To).getPath()
                )
            )) {
              Assertions.assertArrayEquals(info.checkInfo.toArray(), to.lines().toArray());
              latch.countDown();
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
          });
        }
    );
    latch.await();
  }
}