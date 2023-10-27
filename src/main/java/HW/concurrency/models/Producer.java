package HW.concurrency.models;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

public class Producer extends Thread {
  private final Logger logger = Logger.getLogger(Producer.class.getSimpleName());
  private final Set<Worker> workers;
  private final TaskPool taskPool;
  private final int taskGiveTime;
  private final int minTaskValue;
  private final int maxTaskValue;

  public Producer(TaskPool taskPool, Set<Worker> workers, int taskGiveTime, int minTaskValue, int maxTaskValue) {
    if (workers == null || taskPool == null) {
      throw new IllegalArgumentException("Null given to producer");
    }
    taskGiveTime = Math.max(taskGiveTime, 10);
    minTaskValue = Math.max(minTaskValue, 100);
    maxTaskValue = Math.min(maxTaskValue, 3000);
    int val1 = minTaskValue;
    int val2 = maxTaskValue;
    minTaskValue = Math.min(val1, val2);
    maxTaskValue = Math.max(val1, val2);

    this.taskPool = taskPool;
    this.workers = Set.copyOf(workers);
    this.taskGiveTime = taskGiveTime;
    this.minTaskValue = minTaskValue;
    this.maxTaskValue = maxTaskValue;
  }

  @Override
  public void run() {
    while (true) {
      if (!Thread.currentThread().isInterrupted()) {
        int value = getRandomTaskValue();
        logger.info("Dispatched task with value: " + value);
        taskPool.addTask(value);
        try {
          Thread.sleep(taskGiveTime);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          continue;
        }
      } else {
        logger.info("Producer was interrupted");
        // interrupt workers and so on
        for (Worker worker : workers) {
          worker.interrupt();
        }
        break;
      }
    }
  }

  private int getRandomTaskValue(){
    return ThreadLocalRandom.current().nextInt(minTaskValue, maxTaskValue);
  }
}
