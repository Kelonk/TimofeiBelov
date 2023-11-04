package HW.spark.models.holders;

import HW.spark.models.id.ArticleID;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ArticleRepositoryMap implements ArticleRepository {
  private ConcurrentHashMap<ArticleID, Article> articles = new ConcurrentHashMap<>();

  @Override
  public void addArticle(Article article){
    articles.put(article.id, article);
  }

  @Override
  public List<Article> getArticles(){
    return articles.values().stream().toList();
  }

  @Override
  public Optional<Article> findArticle(long id){
    return articles.values().stream().filter(val -> val.id.checkID(id)).findFirst();
  }

  @Override
  public void delete(ArticleID id, CommentRepository commentRepository){
    Article article = articles.remove(id);
    if (article == null) {
      return;
    }
    article.comments.forEach(e -> commentRepository.delete(e.id));
  }

  @Override
  public void replace(Article article) {
    articles.put(article.id, article);
  }
}
