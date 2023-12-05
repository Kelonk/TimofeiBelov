package HW.spark;

import HW.spark.models.holders.*;
import HW.spark.models.spark.ArticleController;
import HW.spark.models.spark.CommentController;
import HW.spark.models.spark.TemplateFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.flywaydb.core.Flyway;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.LoggerFactory;
import spark.Service;

public class Main {
  public static void main(String[] args) {
    Config config = ConfigFactory.load();
    Flyway flyway =
        Flyway.configure()
            .outOfOrder(true)
            .locations("classpath:db/migrations")
            .dataSource(config.getString("app.database.url"), config.getString("app.database.user"),
                config.getString("app.database.password"))
            .load();
    flyway.migrate();
    Jdbi jdbi = Jdbi.create(
        config.getString("app.database.url"),
        config.getString("app.database.user"),
        config.getString("app.database.password")
    );

    Service service = Service.ignite();
    ObjectMapper objectMapper = new ObjectMapper();
    ArticleRepository articleRepository = new ArticleRepositoryDB(jdbi);
    CommentRepository commentRepository = new CommentRepositoryDB(jdbi);
    CommentController commentController = new CommentController(
        LoggerFactory.getLogger(ArticleController.class),
        articleRepository,
        commentRepository,
        service,
        objectMapper);
    commentController.init();
    ArticleController articleController = new ArticleController(
        LoggerFactory.getLogger(ArticleController.class),
        articleRepository,
        commentRepository,
        service,
        objectMapper,
        TemplateFactory.freeMarkerEngine());
    articleController.init();
  }
}
