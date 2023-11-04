package HW.spark.models.id;

import HW.spark.models.holders.Article;
import java.util.concurrent.atomic.AtomicLong;

public class ArticleID implements ID{
  private static AtomicLong counter = new AtomicLong(0);
  private final long id;

  public ArticleID(){
    id = getNewID();
  }

  @Override
  public boolean checkID(long id) {
    return id == this.id;
  }

  private long getNewID() {
    return ArticleID.counter.incrementAndGet();
  }

  public long getId() {
    return id;
  }
}
