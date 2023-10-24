package HW.concurrency.models;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;

public class TaskPool {
  private ConcurrentLinkedDeque<Float> tasks = new ConcurrentLinkedDeque<>();
  private final int poolLimit;

  TaskPool(int poolLimit) {
    this.poolLimit = poolLimit;
  }

  public void addTask(float length) {
    if (tasks.size() > poolLimit) {
      return;
    }
    tasks.addLast(length);
  }

  public Optional<Float> getTask() {
    return Optional.ofNullable(tasks.pollFirst());
  }
}
