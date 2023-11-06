package HW.spark.models.spark;

import HW.spark.models.holders.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ArticleControllerTest {
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
  void articleControllerTestList() throws Exception{
    ObjectMapper objectMapper = new ObjectMapper();
    ArticleRepository articleRepository = new ArticleRepositoryMap();
    CommentRepository commentRepository = new CommentRepositoryMap();
    ArticleController articleController = new ArticleController(
        LoggerFactory.getLogger(ArticleController.class),
        articleRepository,
        commentRepository,
        service,
        objectMapper);
    articleController.init();

    HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString("{\"name\": \"meow\", \"tags\": [\"one\", \"two\"]}"))
            .uri(URI.create("http://localhost:%d/api/articles/add".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

    HttpResponse<String> response = HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .GET()
            .uri(URI.create("http://localhost:%d/api/articles".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    List<Article> articles = objectMapper.readValue(response.body(), new TypeReference<List<Article>>() {});
    Assertions.assertEquals(articles.size(), 1);
    Assertions.assertEquals(articles.get(0).name, "meow");
    Assertions.assertEquals(articles.get(0).tags, Set.of("one", "two"));
  }

  @Test
  void articleControllerTestSearch() throws Exception{
    ObjectMapper objectMapper = new ObjectMapper();
    ArticleRepository articleRepository = new ArticleRepositoryMap();
    CommentRepository commentRepository = new CommentRepositoryMap();
    ArticleController articleController = new ArticleController(
        LoggerFactory.getLogger(ArticleController.class),
        articleRepository,
        commentRepository,
        service,
        objectMapper);
    articleController.init();

    HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString("{\"name\": \"meow\", \"tags\": [\"one\", \"two\"]}"))
            .uri(URI.create("http://localhost:%d/api/articles/add".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

    HttpResponse<String> fail = HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .GET()
            .uri(URI.create("http://localhost:%d/api/articles/asd123".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    Assertions.assertEquals(fail.statusCode(), 404);

    fail = HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .GET()
            .uri(URI.create("http://localhost:%d/api/articles/4".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    Assertions.assertEquals(fail.statusCode(), 404);

    HttpResponse<String> response = HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .GET()
            .uri(URI.create("http://localhost:%d/api/articles/1".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    Article article = objectMapper.readValue(response.body(), Article.class);
    Assertions.assertEquals(article.name, "meow");
    Assertions.assertEquals(article.tags, Set.of("one", "two"));
  }

  @Test
  void articleControllerTestAdd() throws Exception{
    ObjectMapper objectMapper = new ObjectMapper();
    ArticleRepository articleRepository = new ArticleRepositoryMap();
    CommentRepository commentRepository = new CommentRepositoryMap();
    ArticleController articleController = new ArticleController(
        LoggerFactory.getLogger(ArticleController.class),
        articleRepository,
        commentRepository,
        service,
        objectMapper);
    articleController.init();

    HttpResponse<String> fail = HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString("{}"))
            .uri(URI.create("http://localhost:%d/api/articles/add".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    Assertions.assertEquals(fail.statusCode(), 404);

    HttpResponse<String> response = HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString("{\"name\": \"meow\", \"tags\": [\"one\", \"two\"]}"))
            .uri(URI.create("http://localhost:%d/api/articles/add".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    Article article = objectMapper.readValue(response.body(), Article.class);
    Assertions.assertEquals(article.name, "meow");
    Assertions.assertEquals(article.tags, Set.of("one", "two"));
  }

  @Test
  void articleControllerTestDelete() throws Exception{
    ObjectMapper objectMapper = new ObjectMapper();
    ArticleRepository articleRepository = new ArticleRepositoryMap();
    CommentRepository commentRepository = new CommentRepositoryMap();
    ArticleController articleController = new ArticleController(
        LoggerFactory.getLogger(ArticleController.class),
        articleRepository,
        commentRepository,
        service,
        objectMapper);
    articleController.init();

    HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString("{\"name\": \"meow\", \"tags\": [\"one\", \"two\"]}"))
            .uri(URI.create("http://localhost:%d/api/articles/add".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

    HttpResponse<String> fail = HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .DELETE()
            .uri(URI.create("http://localhost:%d/api/articles/delete/dsf12".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    Assertions.assertEquals(fail.statusCode(), 404);

    fail = HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .DELETE()
            .uri(URI.create("http://localhost:%d/api/articles/delete/3".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    Assertions.assertEquals(fail.statusCode(), 404);

    HttpResponse<String> response = HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .DELETE()
            .uri(URI.create("http://localhost:%d/api/articles/delete/1".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    Assertions.assertEquals(response.statusCode(), 200);
  }

  @Test
  void articleControllerTestEdit() throws Exception{
    ObjectMapper objectMapper = new ObjectMapper();
    ArticleRepository articleRepository = new ArticleRepositoryMap();
    CommentRepository commentRepository = new CommentRepositoryMap();
    ArticleController articleController = new ArticleController(
        LoggerFactory.getLogger(ArticleController.class),
        articleRepository,
        commentRepository,
        service,
        objectMapper);
    articleController.init();

    HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString("{\"name\": \"meow\", \"tags\": [\"one\", \"two\"]}"))
            .uri(URI.create("http://localhost:%d/api/articles/add".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

    HttpResponse<String> fail = HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString("{\"name\": \"meow\", \"tags\": [\"one\", \"two\"]}"))
            .uri(URI.create("http://localhost:%d/api/articles/edit/dsf12".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    Assertions.assertEquals(fail.statusCode(), 404);

    fail = HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString("{\"name\": \"meow\", \"tags\": [\"one\", \"two\"]}"))
            .uri(URI.create("http://localhost:%d/api/articles/edit/3".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    Assertions.assertEquals(fail.statusCode(), 404);

    HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString("{\"name\": \"meowmeow\", \"tags\": [\"one\"], \"add\": true, \"commentContent\": \"meow\"}"))
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
    Assertions.assertEquals(article.name, "meowmeow");
    Assertions.assertEquals(article.tags, Set.of("one"));
    Assertions.assertEquals(article.comments.size(), 1);
    Comment comment = article.comments.get(0);
    Assertions.assertEquals(comment.id.getId(), 1);
    Assertions.assertEquals(comment.articleID.getId(), 1);
    Assertions.assertEquals(comment.content, "meow");
  }
}