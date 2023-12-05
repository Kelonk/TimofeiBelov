package HW.spark.models.holders;

import org.flywaydb.core.Flyway;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class CommentRepositoryDBTest {
  @Container
  public static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:14");

  private static Jdbi jdbi;

  @BeforeAll
  static void beforeAll() {
    String postgresJdbcUrl = POSTGRES.getJdbcUrl();
    Flyway flyway =
        Flyway.configure()
            .outOfOrder(true)
            .locations("classpath:db/migrations")
            .dataSource(postgresJdbcUrl, POSTGRES.getUsername(), POSTGRES.getPassword())
            .load();
    flyway.migrate();
    jdbi = Jdbi.create(postgresJdbcUrl, POSTGRES.getUsername(), POSTGRES.getPassword());
  }

  @BeforeEach
  void beforeEach() {
    jdbi.useTransaction(handle -> handle.createUpdate("DELETE FROM comment; DELETE FROM article;").execute());
  }

  @Test
  void addComment() {
    CommentRepository commentRepository = new CommentRepositoryDB(jdbi);
    ArticleRepository articleRepository = new ArticleRepositoryDB(jdbi);
    CommentRepositoryTestDefault.addComment(commentRepository, articleRepository);
  }

  @Test
  void getComments() {
    CommentRepository commentRepository = new CommentRepositoryDB(jdbi);
    ArticleRepository articleRepository = new ArticleRepositoryDB(jdbi);
    CommentRepositoryTestDefault.getComments(commentRepository, articleRepository);
  }

  @Test
  void findComment() {
    CommentRepository commentRepository = new CommentRepositoryDB(jdbi);
    ArticleRepository articleRepository = new ArticleRepositoryDB(jdbi);
    CommentRepositoryTestDefault.findComment(commentRepository, articleRepository);
  }

  @Test
  void delete() {
    CommentRepository commentRepository = new CommentRepositoryDB(jdbi);
    ArticleRepository articleRepository = new ArticleRepositoryDB(jdbi);
    CommentRepositoryTestDefault.delete(commentRepository, articleRepository);
  }

  @Test
  void replace() {
    CommentRepository commentRepository = new CommentRepositoryDB(jdbi);
    ArticleRepository articleRepository = new ArticleRepositoryDB(jdbi);
    CommentRepositoryTestDefault.replace(commentRepository, articleRepository);
  }

  @Test
  void getNewID(){
    CommentRepository commentRepository = new CommentRepositoryDB(jdbi);
    ArticleRepository articleRepository = new ArticleRepositoryDB(jdbi);
    CommentRepositoryTestDefault.getNewID(commentRepository, articleRepository);
  }
}