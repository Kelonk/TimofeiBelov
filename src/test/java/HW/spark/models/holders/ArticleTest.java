package HW.spark.models.holders;

import HW.spark.models.id.ArticleID;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class ArticleTest {
  private AtomicLong counter;

  @BeforeClass

  @BeforeEach
  void reset(){
    counter = new AtomicLong(0);
  }

  @Test
  void recordTest(){
    Assertions.assertThrows(NullPointerException.class, () -> new ArticleRecord(null, null));
    Assertions.assertDoesNotThrow(() -> new ArticleRecord("", Set.of("")));
  }

  @Test
  void recordEditTest(){
    Assertions.assertThrows(NullPointerException.class, () -> new ArticleEditRecord(null, null, true, null));
    Assertions.assertDoesNotThrow(() -> new ArticleEditRecord(null, null, true, ""));
    Assertions.assertDoesNotThrow(() -> new ArticleEditRecord(null, null, false, null));
    Assertions.assertDoesNotThrow(() -> new ArticleEditRecord("", Set.of(""), false, null));
  }

  @Test
  void articleWork(){
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> new Article(null, null, null, counter.get()));

    String debugValue = "meow";
    Article article = new Article(debugValue, Set.of(debugValue), List.of(), counter.incrementAndGet()); // id 1
    Comment comment = new Comment(article.id, debugValue, counter.incrementAndGet()); // id 2
    Article newName = article.newName(debugValue + debugValue);
    Article newTag = article.newTags(Set.of());
    Article newComment = article.attachComment(comment);
    Article noComment = newComment.deleteComment(2);

    Assertions.assertEquals(article.name, debugValue);
    Assertions.assertEquals(newName.name, debugValue + debugValue);

    Assertions.assertEquals(article.tags, Set.of(debugValue));
    Assertions.assertEquals(newTag.tags, Set.of());

    Assertions.assertEquals(article.comments, List.of());
    Assertions.assertEquals(newComment.comments, List.of(comment));
    Assertions.assertEquals(noComment.comments, List.of());
  }
}
