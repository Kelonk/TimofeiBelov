package HW.spark.models.spark;

import HW.spark.models.holders.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;
import spark.Service;

import java.util.ArrayList;
import java.util.Map;
import org.slf4j.Logger;

public class ArticleController implements Controller{
  private final Logger log;
  private final ArticleRepository articleRepository;
  private final CommentRepository commentRepository;
  private final Service service;
  private final ObjectMapper objectMapper;

  public ArticleController(Logger log,
                           ArticleRepository articleRepository,
                           CommentRepository commentRepository,
                           Service service,
                           ObjectMapper objectMapper) {
    this.log = log;
    this.articleRepository = articleRepository;
    this.commentRepository = commentRepository;
    this.service = service;
    this.objectMapper = objectMapper;
    initEndpoints();
  }

  private void initEndpoints(){
    listArticlesEndpoint();
    searchArticleEndpoint();
    addArticleEndpoint();
    deleteArticleEndpoint();
    editArticleEndpoint();
  }

  private void listArticlesEndpoint(){
    service.get("/api/articles", (Request request, Response response) -> {
      try {
        log.debug("List of articles requested");
        response.type("application/json");
        return objectMapper.writeValueAsString(articleRepository.getArticles());
      } catch (Exception e) {
        log.error("Unknown error" + e.getMessage());
        response.status(500);
        return e.getMessage();
      }
    });
  }

  private void searchArticleEndpoint(){
    service.get("/api/articles/:articleID", (Request request, Response response) -> {
      try {
        log.debug("Article by id requested");
        long id;
        try {
          id = Long.parseLong(request.params("articleID"));
        } catch (NumberFormatException e) {
          response.status(404);
          log.warn("Unrecognisable id: " + request.params("articleID"));
          response.type("application/json");
          return objectMapper.writeValueAsString(Map.of("Message", "Unrecognisable article id"));
        }

        var article = articleRepository.findArticle(id);
        if (article.isEmpty()) {
          response.status(404);
          log.warn("No article with id: " + request.params("articleID"));
          response.type("application/json");
          return objectMapper.writeValueAsString(Map.of("Message", "No article with id " + id));
        } else {
          response.type("application/json");
          return objectMapper.writeValueAsString(article.get());
        }
      } catch (Exception e) {
        log.error("Unknown error" + e.getMessage());
        response.status(500);
        return e.getMessage();
      }
    });
  }

  private void addArticleEndpoint(){
    service.post("/api/articles/add", (Request request, Response response) -> {
      try {
        log.debug("Request to add an article");
        String body = request.body();
        ArticleRecord articleCreateRequest;
        try {
          articleCreateRequest = objectMapper.readValue(body, ArticleRecord.class);
        } catch (Exception e) {
          response.status(404);
          log.warn("Problems with article values: " + request.body());
          response.type("application/json");
          return objectMapper.writeValueAsString(Map.of("Message", "Couldn't process your add request"));
        }
        Article article = new Article(
            articleCreateRequest.name(),
            articleCreateRequest.tags(),
            new ArrayList<>());
        articleRepository.addArticle(article);
        response.type("application/json");
        return objectMapper.writeValueAsString(article);
      } catch (Exception e) {
        log.error("Unknown error" + e.getMessage());
        response.status(500);
        return e.getMessage();
      }
    });
  }

  private void deleteArticleEndpoint(){
    service.delete("/api/articles/delete/:articleID", (Request request, Response response) -> {
      try {
        log.debug("Article delete request");
        long id;
        try {
          id = Long.parseLong(request.params("articleID"));
        } catch (NumberFormatException e) {
          response.status(404);
          log.warn("Unrecognisable id: " + request.params("articleID"));
          response.type("application/json");
          return objectMapper.writeValueAsString(Map.of("Message", "Unrecognisable article id"));
        }

        var article = articleRepository.findArticle(id);
        if (article.isEmpty()) {
          response.status(404);
          log.warn("No article with id: " + request.params("articleID"));
          response.type("application/json");
          return objectMapper.writeValueAsString(Map.of("Message", "No article with id " + id));
        } else {
          articleRepository.delete(article.get().id, commentRepository);
          response.type("application/json");
          return objectMapper.writeValueAsString(Map.of("Message", "Successfully deleted article with id " + id));
        }
      } catch (Exception e) {
        log.error("Unknown error" + e.getMessage());
        response.status(500);
        return e.getMessage();
      }
    });
  }

  private void editArticleEndpoint(){
    service.post("/api/articles/edit/:articleID", (Request request, Response response) -> {
      try {
        log.debug("Request to edit article");
        String body = request.body();
        ArticleEditRecord articleEditRequest;
        try {
          articleEditRequest = objectMapper.readValue(body, ArticleEditRecord.class);
        } catch (Exception e) {
          response.status(404);
          log.warn("Problems with edit values: " + request.body());
          response.type("application/json");
          return objectMapper.writeValueAsString(Map.of("Message", "Couldn't process your edit request"));
        }

        long id;
        try {
          id = Long.parseLong(request.params("articleID"));
        } catch (NumberFormatException e) {
          response.status(404);
          log.warn("Unrecognisable id: " + request.params("articleID"));
          response.type("application/json");
          return objectMapper.writeValueAsString(Map.of("Message", "Unrecognisable article id"));
        }

        var article = articleRepository.findArticle(id);
        if (article.isEmpty()) {
          response.status(404);
          log.warn("No article with id: " + request.params("articleID"));
          response.type("application/json");
          return objectMapper.writeValueAsString(Map.of("Message", "No article with id " + id));
        } else {
          Article newArticle = article.get();
          if (articleEditRequest.name() != null) {
            newArticle = newArticle.newName(articleEditRequest.name());
          }
          if (articleEditRequest.tags() != null) {
            newArticle = newArticle.newTags(articleEditRequest.tags());
          }
          if (articleEditRequest.add()) {
            Comment comment = new Comment(
                newArticle.id,
                articleEditRequest.commentContent());
            newArticle = newArticle.attachComment(comment);
          }
          articleRepository.replace(newArticle);
          response.type("application/json");
          return objectMapper.writeValueAsString(newArticle);
        }
      } catch (Exception e) {
        log.error("Unknown error" + e.getMessage());
        response.status(500);
        return e.getMessage();
      }
    });
  }

  @Override
  public void init() {
    service.init();
    service.awaitInitialization();
    log.debug("Article controller started");
  }
}
