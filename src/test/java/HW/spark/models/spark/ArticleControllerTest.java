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
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ArticleControllerTest {
  private Service service;

  private HttpResponse<String> createArticle(Service service, ObjectMapper objectMapper, String name, Set<String> tags) throws Exception {
    return HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(Map.of("name", name, "tags", tags))))
            .uri(URI.create("http://localhost:%d/api/articles/add".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
  }

  private HttpResponse<String> deleteArticle(Service service, ObjectMapper objectMapper, String id) throws Exception {
    return HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .DELETE()
            .uri(URI.create(("http://localhost:%d/api/articles/delete/" + id).formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
  }

  private HttpResponse<String> editArticle(Service service, ObjectMapper objectMapper, Map<Object, Object> body, String id) throws Exception {
    return HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(body)))
            .uri(URI.create(("http://localhost:%d/api/articles/edit/" + id).formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
  }

  private List<Article> getArticles(Service service, ObjectMapper objectMapper) throws Exception {
    HttpResponse<String> response = HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .GET()
            .uri(URI.create("http://localhost:%d/api/articles".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    return objectMapper.readValue(response.body(), new TypeReference<List<Article>>() {});
  }

  private HttpResponse<String> getArticles(Service service) throws Exception {
    return HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .GET()
            .uri(URI.create("http://localhost:%d/articles".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
  }

  private HttpResponse<String> getArticle(Service service, ObjectMapper objectMapper, String id) throws Exception {
    return HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .GET()
            .uri(URI.create(("http://localhost:%d/api/articles/" + id).formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
  }

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
        objectMapper,
        TemplateFactory.freeMarkerEngine());
    articleController.init();

    createArticle(service, objectMapper, "meow", Set.of("one", "two"));
    List<Article> articles = getArticles(service, objectMapper);
    HttpResponse<String> html = getArticles(service);

    Assertions.assertEquals(articles.size(), 1);
    Assertions.assertEquals(articles.get(0).name, "meow");
    Assertions.assertEquals(articles.get(0).tags, Set.of("one", "two"));
    Assertions.assertTrue(html.body().contains("meow"));
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
        objectMapper,
        TemplateFactory.freeMarkerEngine());
    articleController.init();

    createArticle(service, objectMapper, "meow", Set.of("one", "two"));

    HttpResponse<String> fail = getArticle(service, objectMapper, "asd123");
    Assertions.assertEquals(fail.statusCode(), 404);

    fail = getArticle(service, objectMapper, "4");
    Assertions.assertEquals(fail.statusCode(), 404);

    HttpResponse<String> response = getArticle(service, objectMapper, "1");
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
        objectMapper,
        TemplateFactory.freeMarkerEngine());
    articleController.init();

    HttpResponse<String> fail = HttpClient.newHttpClient().send(
        HttpRequest
            .newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString("{}"))
            .uri(URI.create("http://localhost:%d/api/articles/add".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    Assertions.assertEquals(fail.statusCode(), 404);

    HttpResponse<String> response = createArticle(service, objectMapper, "meow", Set.of("one", "two"));
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
        objectMapper,
        TemplateFactory.freeMarkerEngine());
    articleController.init();

    createArticle(service, objectMapper, "meow", Set.of("one", "two"));

    HttpResponse<String> fail = deleteArticle(service, objectMapper, "dsf12");
    Assertions.assertEquals(fail.statusCode(), 404);

    fail = deleteArticle(service, objectMapper, "3");
    Assertions.assertEquals(fail.statusCode(), 404);

    HttpResponse<String> response = deleteArticle(service, objectMapper, "1");
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
        objectMapper,
        TemplateFactory.freeMarkerEngine());
    articleController.init();

    createArticle(service, objectMapper, "meow", Set.of("one", "two"));


    HttpResponse<String> fail = editArticle(service, objectMapper, Map.of("name", "meow", "tags", Set.of("one", "two")), "dsf12");
    Assertions.assertEquals(fail.statusCode(), 404);

    fail = editArticle(service, objectMapper, Map.of("name", "meow", "tags", Set.of("one", "two")), "3");
    Assertions.assertEquals(fail.statusCode(), 404);

    editArticle(service, objectMapper, Map.of("name", "meowmeow", "tags", Set.of("one"), "add", true, "commentContent", "meow"), "1");

    HttpResponse<String> response = getArticle(service, objectMapper, "1");
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