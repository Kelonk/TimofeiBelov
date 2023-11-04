package HW.spark.models.holders;

import java.util.Objects;
import java.util.Set;

public record ArticleEditRecord(String name, Set<String> tags, Boolean add, String commentContent) {
  public ArticleEditRecord {
    if (add) {
      Objects.requireNonNull(commentContent);
    }
  }
}
