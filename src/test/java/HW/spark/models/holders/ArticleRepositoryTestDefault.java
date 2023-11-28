package HW.spark.models.holders;

import HW.spark.models.id.CommentID;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class ArticleRepositoryTestDefault {
  public static void addArticle(ArticleRepository repositoryToCheck){
    AtomicLong counter = new AtomicLong(0);
    String debugValue1 = "meow";
    Article article1 = new Article(debugValue1, Set.of(), List.of(), counter.incrementAndGet());
    Assertions.assertFalse(repositoryToCheck.getArticles().contains(article1));
    repositoryToCheck.addArticle(article1);
    Assertions.assertTrue(repositoryToCheck.getArticles().contains(article1));
  }

  public static void getArticles(ArticleRepository repositoryToCheck){
    AtomicLong counter = new AtomicLong(0);
    String debugValue1 = "meow";
    Article article1 = new Article(debugValue1, Set.of(), List.of(), counter.incrementAndGet());
    Assertions.assertTrue(repositoryToCheck.getArticles().isEmpty());
    repositoryToCheck.addArticle(article1);
    Assertions.assertEquals(repositoryToCheck.getArticles(), List.of(article1));
  }

  public static void findArticle(ArticleRepository repositoryToCheck){
    AtomicLong counter = new AtomicLong(0);
    String debugValue1 = "meow";
    Article article1 = new Article(debugValue1, Set.of(), List.of(), counter.incrementAndGet());
    Assertions.assertTrue(repositoryToCheck.findArticle(article1.id.getId()).isEmpty());
    repositoryToCheck.addArticle(article1);
    Assertions.assertEquals(repositoryToCheck.findArticle(article1.id.getId()).get(), article1);
  }

  public static void delete(ArticleRepository repositoryToCheck){
    AtomicLong counter = new AtomicLong(0);
    String debugValue1 = "meow";
    Article article1 = new Article(debugValue1, Set.of(), List.of(), counter.incrementAndGet());
    repositoryToCheck.addArticle(article1);
    Assertions.assertEquals(repositoryToCheck.findArticle(article1.id.getId()).get(), article1);
    repositoryToCheck.delete(article1.id, new CommentRepository() {
      @Override
      public void addComment(Comment comment) {}
      @Override
      public List<Comment> getComments() {return null;}
      @Override
      public Optional<Comment> findComment(long id) {return Optional.empty();}
      @Override
      public void delete(CommentID id) {}
      @Override
      public void replace(Comment comment) {}
      @Override
      public long getNewID() {return 0;}
    });
    Assertions.assertTrue(repositoryToCheck.findArticle(article1.id.getId()).isEmpty());
  }

  public static void edit(ArticleRepository repositoryToCheck){
    AtomicLong counter = new AtomicLong(0);
    String debugValue1 = "meow";
    String debugValue2 = "meow2";
    Article article1 = new Article(debugValue1, Set.of(), List.of(), counter.incrementAndGet());
    ArticleEditRecord editRequest = new ArticleEditRecord(debugValue2, null, false, null);
    repositoryToCheck.addArticle(article1);
    repositoryToCheck.edit(editRequest, article1, null);
    Assertions.assertEquals(repositoryToCheck.getArticles().get(0).name, debugValue2);
  }

  public static void getNewID(ArticleRepository repositoryToCheck) {
    Assertions.assertEquals(repositoryToCheck.getNewID(), 1);
    Assertions.assertEquals(repositoryToCheck.getNewID(), 2);
    Assertions.assertEquals(repositoryToCheck.getNewID(), 3);
  }
}
