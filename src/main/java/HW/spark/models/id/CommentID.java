package HW.spark.models.id;

import HW.spark.models.holders.Comment;
import java.util.concurrent.atomic.AtomicLong;

public class CommentID implements ID{
  private static AtomicLong counter = new AtomicLong(0);
  private final long id;

  public CommentID(){
    id = getNewID();
  }

  @Override
  public boolean checkID(long id) {
    return id == this.id;
  }

  private long getNewID() {
    return CommentID.counter.incrementAndGet();
  }

  public long getId() {
    return id;
  }
}
