package HW.spark.models.holders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class CommentTest {

  @Test
  void commentWork(){
    String debugValue = "meow";
    AtomicLong counter = new AtomicLong(0);
    Assertions.assertThrows(IllegalArgumentException.class, () -> new Comment(null, null, counter.get()));

    Article article = new Article("", Set.of(), List.of(), counter.incrementAndGet()); // id 1
    Comment comment = new Comment(article.id, "", counter.incrementAndGet()); // id 2
    Comment newContent = comment.newContent(debugValue);

    Assertions.assertEquals(comment.articleID.getId(), 1);
    Assertions.assertEquals(newContent.articleID.getId(), 1);
    Assertions.assertEquals(comment.id.getId(), 2);
    Assertions.assertEquals(newContent.id.getId(), 2);
    Assertions.assertEquals(comment.content, "");
    Assertions.assertEquals(newContent.content, debugValue);
  }
}
