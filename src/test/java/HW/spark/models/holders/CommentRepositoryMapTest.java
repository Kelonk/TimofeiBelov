package HW.spark.models.holders;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentRepositoryMapTest {
  @Test
  void addComment() {
    CommentRepository commentRepository = new CommentRepositoryMap();
    CommentRepositoryTestDefault.addComment(commentRepository, null);
  }

  @Test
  void getComments() {
    CommentRepository commentRepository = new CommentRepositoryMap();
    CommentRepositoryTestDefault.getComments(commentRepository, null);
  }

  @Test
  void findComment() {
    CommentRepository commentRepository = new CommentRepositoryMap();
    CommentRepositoryTestDefault.findComment(commentRepository, null);
  }

  @Test
  void delete() {
    CommentRepository commentRepository = new CommentRepositoryMap();
    CommentRepositoryTestDefault.delete(commentRepository, null);
  }

  @Test
  void replace() {
    CommentRepository commentRepository = new CommentRepositoryMap();
    CommentRepositoryTestDefault.replace(commentRepository, null);
  }

  @Test
  void getNewID(){
    CommentRepository commentRepository = new CommentRepositoryMap();
    CommentRepositoryTestDefault.getNewID(commentRepository, null);
  }
}