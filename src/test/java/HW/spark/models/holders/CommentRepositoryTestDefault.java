package HW.spark.models.holders;

import HW.spark.models.id.CommentID;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class CommentRepositoryTestDefault {
  private static void tryInsertArticle(ArticleRepository articleRepository, Article article) {
    if (articleRepository != null) {
      articleRepository.addArticle(article);
    }
  }

  public static void addComment(CommentRepository repositoryToCheck, ArticleRepository articleRepository){
    AtomicLong counter = new AtomicLong(0);
    String debugValue1 = "meow";
    Article article1 = new Article(debugValue1, Set.of(), List.of(), counter.incrementAndGet()); // id 1
    Comment comment1 = new Comment(article1.id, debugValue1, counter.incrementAndGet()); // id 2
    Assertions.assertFalse(repositoryToCheck.getComments().contains(comment1));
    tryInsertArticle(articleRepository, article1);
    repositoryToCheck.addComment(comment1);
    Assertions.assertTrue(repositoryToCheck.getComments().contains(comment1));
  }

  public static void getComments(CommentRepository repositoryToCheck, ArticleRepository articleRepository){
    AtomicLong counter = new AtomicLong(0);
    String debugValue1 = "meow";
    Article article1 = new Article(debugValue1, Set.of(), List.of(), counter.incrementAndGet()); // id 1
    Comment comment1 = new Comment(article1.id, debugValue1, counter.incrementAndGet()); // id 2
    Assertions.assertTrue(repositoryToCheck.getComments().isEmpty());
    tryInsertArticle(articleRepository, article1);
    repositoryToCheck.addComment(comment1);
    Assertions.assertEquals(repositoryToCheck.getComments(), List.of(comment1));
  }

  public static void findComment(CommentRepository repositoryToCheck, ArticleRepository articleRepository){
    AtomicLong counter = new AtomicLong(0);
    String debugValue1 = "meow";
    Article article1 = new Article(debugValue1, Set.of(), List.of(), counter.incrementAndGet()); // id 1
    Comment comment1 = new Comment(article1.id, debugValue1, counter.incrementAndGet()); // id 2
    Assertions.assertTrue(repositoryToCheck.findComment(comment1.id.getId()).isEmpty());
    tryInsertArticle(articleRepository, article1);
    repositoryToCheck.addComment(comment1);
    Assertions.assertEquals(repositoryToCheck.findComment(comment1.id.getId()).get(), comment1);
  }

  public static void delete(CommentRepository repositoryToCheck, ArticleRepository articleRepository){
    AtomicLong counter = new AtomicLong(0);
    String debugValue1 = "meow";
    Article article1 = new Article(debugValue1, Set.of(), List.of(), counter.incrementAndGet()); // id 1
    Comment comment1 = new Comment(article1.id, debugValue1, counter.incrementAndGet()); // id 2
    tryInsertArticle(articleRepository, article1);
    repositoryToCheck.addComment(comment1);
    Assertions.assertEquals(repositoryToCheck.findComment(comment1.id.getId()).get(), comment1);
    repositoryToCheck.delete(comment1.id);
    Assertions.assertTrue(repositoryToCheck.findComment(comment1.id.getId()).isEmpty());
  }

  public static void replace(CommentRepository repositoryToCheck, ArticleRepository articleRepository){
    AtomicLong counter = new AtomicLong(0);
    String debugValue1 = "meow";
    String debugValue2 = "meow2";
    Article article1 = new Article(debugValue1, Set.of(), List.of(), counter.incrementAndGet()); // id 1
    Comment comment1 = new Comment(article1.id, debugValue1, counter.incrementAndGet()); // id 2
    Comment newComment = comment1.newContent(debugValue2);
    tryInsertArticle(articleRepository, article1);
    repositoryToCheck.addComment(comment1);
    repositoryToCheck.replace(newComment);
    Assertions.assertEquals(repositoryToCheck.getComments().get(0), newComment);
  }

  public static void getNewID(CommentRepository repositoryToCheck, ArticleRepository articleRepository) {
    Assertions.assertEquals(repositoryToCheck.getNewID(), 1);
    Assertions.assertEquals(repositoryToCheck.getNewID(), 2);
    Assertions.assertEquals(repositoryToCheck.getNewID(), 3);
  }
}
