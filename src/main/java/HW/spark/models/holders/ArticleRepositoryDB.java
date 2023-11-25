package HW.spark.models.holders;

import HW.spark.models.db.TransactionManager;
import HW.spark.models.id.ArticleID;

import java.util.List;
import java.util.Optional;

// partially ignores values
public class ArticleRepositoryDB implements ArticleRepository {
  private final TransactionManager transactionManager;

  public ArticleRepositoryDB(TransactionManager transactionManager) {
    this.transactionManager = transactionManager;
  }

  @Override
  public void addArticle(Article article) {

  }

  @Override
  public void addArticles(List<Article> articles) {

  }

  @Override
  public List<Article> getArticles() {
    return null;
  }

  @Override
  public Optional<Article> findArticle(long id) {
    return Optional.empty();
  }

  @Override
  public void delete(ArticleID id, CommentRepository commentRepository) {

  }

  @Override
  public void replace(Article article) {

  }

  @Override
  public long getNewID() {
    return 0;
  }
}
