package HW.spark.models.holders;

import HW.spark.models.id.CommentID;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class CommentRepositoryMap implements CommentRepository {

  private ConcurrentHashMap<CommentID, Comment> comments = new ConcurrentHashMap<>();
  private final AtomicLong counter;

  public CommentRepositoryMap(AtomicLong counter) {
    this.counter = counter;
  }

  public CommentRepositoryMap() {
    this.counter = new AtomicLong(0);
  }

  @Override
  public void addComment(Comment comment){
    comments.put(comment.id, comment);
  }

  @Override
  public List<Comment> getComments(){
    return comments.values().stream().toList();
  }

  @Override
  public Optional<Comment> findComment(long id){
    return comments.values().stream().filter(val -> val.id.checkID(id)).findFirst();
  }

  @Override
  public void delete(CommentID id){
    comments.remove(id);
  }

  @Override
  public void replace(Comment comment) {
    comments.put(comment.id, comment);
  }

  @Override
  public long getNewID() {
    return counter.incrementAndGet();
  }
}

