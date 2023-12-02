package HW.spark.models.holders;

import HW.spark.models.id.ArticleID;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
  public void addArticle(Article article);
  public void addArticles(List<Article> articles);
  public List<Article> getArticles();
  public Optional<Article> findArticle(long id);
  public void delete(ArticleID id, CommentRepository commentRepository);
  public Article edit(ArticleEditRecord editRequest, Article article, CommentRepository commentRepository);
  public long getNewID();
  public void replace(Article newArticle);
}
