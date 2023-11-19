package HW.spark.models.holders;

import java.util.Objects;
import java.util.Set;

public record ArticleRecord(String name, Set<String> tags) {
  public ArticleRecord {
    Objects.requireNonNull(name);
    Objects.requireNonNull(tags);
  }
}
