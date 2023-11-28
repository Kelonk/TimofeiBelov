package HW.spark.models.holders;

import HW.spark.models.id.ArticleID;
import HW.spark.models.id.CommentID;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

// partially ignores values
public class CommentRepositoryDB implements CommentRepository {
  private final Jdbi jdbi;

  public CommentRepositoryDB(Jdbi jdbi) {
    this.jdbi = jdbi;
  }

  @Override
  public void addComment(Comment comment) {
    jdbi.useTransaction((Handle handle) -> {
      handle.createUpdate("INSERT INTO comment VALUES (:id, :article_id, :text);")
          .bind("id", comment.id.getId())
          .bind("article_id", comment.articleID.getId())
          .bind("text", comment.content)
          .execute();
    });
  }

  @Override
  public List<Comment> getComments() {
    return jdbi.inTransaction((Handle handle) -> {
      var result =
          handle.createQuery("SELECT * FROM comment")
              .mapToMap();
      return result.stream().map(map ->
          new Comment(
              new ArticleID((long) map.get("article_id")),
              (String) map.get("text"),
              (long) map.get("id"))
      ).toList();
    });
  }

  @Override
  public Optional<Comment> findComment(long id) {
    return jdbi.inTransaction((Handle handle) -> {
      var result =
          handle.createQuery("SELECT * FROM comment WHERE id = :id")
              .bind("id", id)
              .mapToMap()
              .stream().findFirst();
      if (result.isEmpty()) {
        return Optional.empty();
      }
      return Optional.of(new Comment(
          new ArticleID((long) result.get().get("article_id")),
          (String) result.get().get("text"),
          id));
    });
  }

  @Override
  public void delete(CommentID id) {
    jdbi.useTransaction((Handle handle) -> {
      handle.createUpdate("DELETE FROM comment where id = :id").bind("id", id.getId()).execute();
    });
  }

  @Override
  public void replace(Comment comment) {
    jdbi.useTransaction((Handle handle) -> {
      handle.createUpdate("UPDATE comment SET text = :text WHERE id = :id")
          .bind("id", comment.id.getId())
          .bind("text", comment.content)
          .execute();
    });
  }

  @Override
  public long getNewID() {
    return jdbi.inTransaction((Handle handle) -> {
      long value = (long) handle.createQuery("SELECT nextval('comment_id_seq') AS value")
          .mapToMap()
          .first()
          .get("value");
      return value;
    });
  }
}
