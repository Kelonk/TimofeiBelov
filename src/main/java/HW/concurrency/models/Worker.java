package HW.concurrency.models;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

public class Worker extends Thread {
  private final Logger logger = Logger.getLogger(Producer.class.getSimpleName());
  private final TaskPool taskPool;
  private final int defaultWaitingTime;

  public Worker(TaskPool taskPool, int defaultWaitingTime) {
    if (taskPool == null) {
      throw new IllegalArgumentException("Null given to worker");
    }
    this.taskPool = taskPool;
    this.defaultWaitingTime = defaultWaitingTime;
  }

  @Override
  public void run() {
    while (true) {
      if (!Thread.currentThread().isInterrupted()) {
        var value = taskPool.getTask();
        int timeToSleep = defaultWaitingTime;
        if (value.isPresent()) {
          logger.info("Took task with value: " + value.get());
          timeToSleep = value.get();

        } else {
          logger.info("No task to start");
        }
        try {
          Thread.sleep(timeToSleep);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          continue;
        }
      } else {
        // worker was interrupted
        logger.info("Worker finished");
        break;
      }
    }
  }
}
