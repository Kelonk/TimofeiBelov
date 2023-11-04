package HW.spark.models.holders;

import HW.spark.models.id.CommentID;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CommentRepositoryMap implements CommentRepository {
  private ConcurrentHashMap<CommentID, Comment> comments = new ConcurrentHashMap<>();

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
}

