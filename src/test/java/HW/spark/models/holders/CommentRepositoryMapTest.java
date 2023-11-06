package HW.spark.models.holders;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentRepositoryMapTest {
  @Test
  void addComment() {
    CommentRepository commentRepository = new CommentRepositoryMap();
    CommentRepositoryTestDefault.addComment(commentRepository);
  }

  @Test
  void getComments() {
    CommentRepository commentRepository = new CommentRepositoryMap();
    CommentRepositoryTestDefault.getComments(commentRepository);
  }

  @Test
  void findComment() {
    CommentRepository commentRepository = new CommentRepositoryMap();
    CommentRepositoryTestDefault.findComment(commentRepository);
  }

  @Test
  void delete() {
    CommentRepository commentRepository = new CommentRepositoryMap();
    CommentRepositoryTestDefault.delete(commentRepository);
  }

  @Test
  void replace() {
    CommentRepository commentRepository = new CommentRepositoryMap();
    CommentRepositoryTestDefault.replace(commentRepository);
  }

  @Test
  void getNewID(){
    CommentRepository commentRepository = new CommentRepositoryMap();
    CommentRepositoryTestDefault.getNewID(commentRepository);
  }
}