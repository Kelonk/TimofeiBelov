package HW.spark.models.id;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

class ArticleIDTest {

  @Test
  void checkID() {
    ArticleID articleID1 = new ArticleID(1);
    Assertions.assertTrue(articleID1.checkID(1));
    Assertions.assertFalse(articleID1.checkID(2));
    ArticleID articleID2 = new ArticleID(2);
    Assertions.assertTrue(articleID2.checkID(2));
  }

  @Test
  void getId() {
    ArticleID articleID1 = new ArticleID(1);
    Assertions.assertEquals(articleID1.getId(), 1);
    Assertions.assertNotEquals(articleID1.getId(), 2);
    ArticleID articleID2 = new ArticleID(2);
    Assertions.assertEquals(articleID2.getId(), 2);
    Assertions.assertEquals(articleID1.getId(), 1);
  }
}