package HW.spark.models.holders;

import HW.spark.models.id.ArticleID;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

// partially ignores values
public class ArticleRepositoryDB implements ArticleRepository {
  private final Jdbi jdbi;

  public ArticleRepositoryDB(Jdbi jdbi) {
    this.jdbi = jdbi;
  }

  @Override
  public void addArticle(Article article) {
    jdbi.useTransaction((Handle handle) -> {
      handle.createUpdate("INSERT INTO article VALUES (:id, :name, :trending, :tags);")
          .bind("id", article.id.getId())
          .bind("name", article.name)
          .bind("trending", article.trending)
          .bind("tags", String.join(", ", article.tags))
          .execute();
    });
  }

  @Override
  public void addArticles(List<Article> articles) {
    jdbi.useTransaction((Handle handle) -> {
      for (Article article :
          articles) {
        addArticle(article);
      }
    });
  }

  @Override
  public List<Article> getArticles() {
    return jdbi.inTransaction((Handle handle) -> {
      var result =
          handle.createQuery("SELECT * FROM article")
              .mapToMap()
              .list();
      return result.stream().map(map ->
          new Article(
              (String) map.get("name"),
              map.get("tags").equals("") ? Set.of() : Set.of(((String) map.get("tags")).split(", ")),
              handle.createQuery("SELECT * FROM comment where article_id = :id")
                  .bind("id", map.get("id"))
                  .mapToMap()
                  .map(comment ->
                      new Comment(
                          new ArticleID((long) map.get("article_id")),
                          (String) comment.get("text"),
                          (long) comment.get("id")))
                  .list(),
              (long) map.get("id")))
          .collect(Collectors.toList());
    });
  }

  @Override
  public Optional<Article> findArticle(long id) {
    return jdbi.inTransaction((Handle handle) -> {
      var result =
          handle.createQuery("SELECT * FROM article WHERE id = :id")
              .bind("id", id)
              .mapToMap()
              .stream().findFirst();
      if (result.isEmpty()) {
        return Optional.empty();
      }
      return Optional.of(new Article(
          (String) result.get().get("name"),
          result.get().get("tags").equals("") ? Set.of() : Set.of(((String) result.get().get("tags")).split(", ")),
          handle.createQuery("SELECT * FROM comment where article_id = :id")
              .bind("id", result.get().get("id"))
              .mapToMap()
              .map(comment ->
                  new Comment(
                      new ArticleID((long) result.get().get("article_id")),
                      (String) comment.get("text"),
                      (long) comment.get("id")))
              .list(),
          (long) result.get().get("id")));
    });
  }

  @Override
  public void delete(ArticleID id, CommentRepository commentRepository) {
    jdbi.useTransaction((Handle handle) -> {
      var article = findArticle(id.getId());
      if (article.isEmpty()) {
        return;
      }
      handle.createUpdate("DELETE FROM article WHERE id = :id").bind("id", id.getId()).execute();
      for (Comment comment :
          article.get().comments) {
        commentRepository.delete(comment.id);
      }
    });
  }

  @Override
  public Article edit(ArticleEditRecord editRequest, Article article, CommentRepository commentRepository) {
    return jdbi.inTransaction((Handle handle) -> {
      handle.createQuery("SELECT * FROM article WHERE id = :id FOR UPDATE").bind("id", article.id.getId());
      handle.createUpdate("UPDATE article SET " +
              "name = coalesce(:name, name)," +
              "tags = coalesce(:tags, tags)," +
              "trending = :trending WHERE id = :id")
          .bind("id", article.id.getId())
          .bind("name", editRequest.name())
          .bind("tags", editRequest.tags() == null ? null : String.join(", ", editRequest.tags()))
          .bind("trending", article.comments.size() + (editRequest.add() ? 1 : 0) > Article.trendingThreshold)
          .execute();
      if (editRequest.add()) {
        commentRepository.addComment(new Comment(article.id, editRequest.commentContent(), commentRepository.getNewID()));
      }
      return findArticle(article.id.getId()).get();
    });
  }

  @Override
  public long getNewID() {
    return jdbi.inTransaction((Handle handle) -> {
      long value = (long) handle.createQuery("SELECT nextval('article_id_seq') AS value")
          .mapToMap()
          .first()
          .get("value");
      return value;
    });
  }

  @Override
  public void replace(Article newArticle) {
    throw new RuntimeException("Unsupported operation");
  }
}
