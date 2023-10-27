package HW.concurrency.models;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;

public class TaskPool {
  private ConcurrentLinkedDeque<Integer> tasks = new ConcurrentLinkedDeque<>();
  private final int poolLimit;

  public TaskPool(int poolLimit) {
    this.poolLimit = poolLimit;
  }

  public void addTask(int length) {
    if (tasks.size() > poolLimit) {
      return;
    }
    tasks.addLast(length);
  }

  public Optional<Integer> getTask() {
    return Optional.ofNullable(tasks.pollFirst());
  }
}
