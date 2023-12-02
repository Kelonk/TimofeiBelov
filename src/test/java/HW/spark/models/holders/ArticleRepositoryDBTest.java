package HW.spark.models.holders;

import org.flywaydb.core.Flyway;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class ArticleRepositoryDBTest {
  @Container
  public static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:13");

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
  void addArticle() {
    ArticleRepository articleRepository = new ArticleRepositoryDB(jdbi);
    ArticleRepositoryTestDefault.addArticle(articleRepository);
  }

  @Test
  void addArticles() {
    ArticleRepository articleRepository = new ArticleRepositoryDB(jdbi);
    ArticleRepositoryTestDefault.addArticles(articleRepository);
  }

  @Test
  void getArticles() {
    ArticleRepository articleRepository = new ArticleRepositoryDB(jdbi);
    ArticleRepositoryTestDefault.getArticles(articleRepository);
  }

  @Test
  void findArticle() {
    ArticleRepository articleRepository = new ArticleRepositoryDB(jdbi);
    ArticleRepositoryTestDefault.findArticle(articleRepository);
  }

  @Test
  void delete() {
    ArticleRepository articleRepository = new ArticleRepositoryDB(jdbi);
    ArticleRepositoryTestDefault.delete(articleRepository);
  }

  @Test
  void edit() {
    ArticleRepository articleRepository = new ArticleRepositoryDB(jdbi);
    ArticleRepositoryTestDefault.edit(articleRepository);
  }

  @Test
  void getNewID(){
    ArticleRepository articleRepository = new ArticleRepositoryDB(jdbi);
    ArticleRepositoryTestDefault.getNewID(articleRepository);
  }
}