package HW.spark.models.holders;

import HW.spark.models.id.CommentID;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
  public void addComment(Comment comment);
  public List<Comment> getComments();
  public Optional<Comment> findComment(long id);
  public void delete(CommentID id);
}
