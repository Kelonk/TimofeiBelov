package HW.spark.models.spark;

import HW.spark.models.holders.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import spark.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class CommentControllerTest {
  private Service service;

  @BeforeEach
  void setUp() {
    service = Service.ignite();
  }

  @AfterEach
  void tearDown() {
    service.stop();
    service.awaitStop();
  }

  @Test
  void commentControllerTestDelete() throws Exception{
    ObjectMapper objectMapper = new ObjectMapper();
    ArticleRepository articleRepository = new ArticleRepositoryMap();
    CommentRepository commentRepository = new CommentRepositoryMap();
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

    HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString("{\"name\": \"meow\", \"tags\": [\"one\", \"two\"]}"))
            .uri(URI.create("http://localhost:%d/api/articles/add".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString("{\"add\": true, \"commentContent\": \"meow\"}"))
            .uri(URI.create("http://localhost:%d/api/articles/edit/1".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

    HttpResponse<String> response = HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .GET()
            .uri(URI.create("http://localhost:%d/api/articles/1".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    Article article = objectMapper.readValue(response.body(), Article.class);
    Assertions.assertEquals(article.comments.size(), 1);

    HttpResponse<String> responseDelete = HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .GET()
            .uri(URI.create("http://localhost:%d/api/comments/delete/1".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    Assertions.assertEquals(responseDelete.statusCode(), 200);

    HttpResponse<String> responseNew = HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .GET()
            .uri(URI.create("http://localhost:%d/api/articles/1".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    Article articleNew = objectMapper.readValue(responseNew.body(), Article.class);
    Assertions.assertEquals(articleNew.comments.size(), 0);
  }
}
