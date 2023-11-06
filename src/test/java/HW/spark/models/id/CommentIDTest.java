package HW.spark.models.id;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

class CommentIDTest {

  @Test
  void checkID() {
    CommentID commentID1 = new CommentID(1);
    Assertions.assertTrue(commentID1.checkID(1));
    Assertions.assertFalse(commentID1.checkID(2));
    CommentID commentID2 = new CommentID(2);
    Assertions.assertTrue(commentID2.checkID(2));
  }

  @Test
  void getId() {
    CommentID commentID1 = new CommentID(1);
    Assertions.assertEquals(commentID1.getId(), 1);
    Assertions.assertNotEquals(commentID1.getId(), 2);
    CommentID commentID2 = new CommentID(2);
    Assertions.assertEquals(commentID2.getId(), 2);
    Assertions.assertEquals(commentID1.getId(), 1);
  }
}