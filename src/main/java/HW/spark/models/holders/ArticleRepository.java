package HW.spark.models.holders;

import HW.spark.models.id.ArticleID;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
  public void addArticle(Article article);
  public List<Article> getArticles();
  public Optional<Article> findArticle(long id);
  public void delete(ArticleID id, CommentRepository commentRepository);
  public void replace(Article article);
  public long getNewID();
}
