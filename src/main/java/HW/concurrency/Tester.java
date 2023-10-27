package HW.concurrency;

import HW.concurrency.models.Producer;
import HW.concurrency.models.TaskPool;
import HW.concurrency.models.Worker;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tester {
  public static void main(String[] args) {
    // ExecutorService executorService = Executors.newFixedThreadPool(20);
    TaskPool taskPool = new TaskPool(100);
    Set<Worker> workers = Stream
        .generate(() -> new Worker(taskPool, 500))
        .limit(5)
        .collect(Collectors.toSet());
    Producer producer = new Producer(taskPool, workers, 1000, 100, 3000);
    producer.start();
    for (Worker worker : workers) {
      worker.start();
    }

    try {
      Thread.sleep(30000);
    } catch (InterruptedException e) {
      producer.interrupt();
    } finally {
      producer.interrupt();
    }
  }
}
