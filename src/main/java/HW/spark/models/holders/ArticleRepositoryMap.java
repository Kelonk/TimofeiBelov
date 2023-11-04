package HW.spark.models.holders;

import HW.spark.models.id.ArticleID;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ArticleRepositoryMap implements ArticleRepository {
  private ConcurrentHashMap<ArticleID, Article> articles = new ConcurrentHashMap<>();

  public void addArticle(Article article){
    articles.put(article.id, article);
  }

  public List<Article> getArticles(){
    return articles.values().stream().toList();
  }

  public Optional<Article> findArticle(long id){
    return articles.values().stream().filter(val -> val.id.checkID(id)).findFirst();
  }

  public void delete(ArticleID id){
    Article article = articles.remove(id);
    if (article == null) {
      return;
    }
    article.comments.forEach(e -> e.remove());
  }
}
