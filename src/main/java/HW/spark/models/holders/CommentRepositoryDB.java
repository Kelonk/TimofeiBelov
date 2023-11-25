package HW.spark.models.holders;

import HW.spark.models.db.TransactionManager;
import HW.spark.models.id.CommentID;

import java.util.List;
import java.util.Optional;

// partially ignores values
public class CommentRepositoryDB implements  CommentRepository {
  private final TransactionManager transactionManager;

  public CommentRepositoryDB(TransactionManager transactionManager) {
    this.transactionManager = transactionManager;
  }

  @Override
  public void addComment(Comment comment) {

  }

  @Override
  public List<Comment> getComments() {
    return null;
  }

  @Override
  public Optional<Comment> findComment(long id) {
    return Optional.empty();
  }

  @Override
  public void delete(CommentID id) {

  }

  @Override
  public void replace(Comment comment) {

  }

  @Override
  public long getNewID() {
    return 0;
  }
}
