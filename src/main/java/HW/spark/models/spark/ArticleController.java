package HW.spark.models.spark;

import HW.spark.models.holders.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;
import spark.Service;

import java.text.ParseException;
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
    DefaultControllerFunctions.internalErrorHandle(service, log);
    DefaultControllerFunctions.parseExceptionHandle(service, log);
    DefaultControllerFunctions.notFoundExceptionHandle(service, log);
  }

  private void listArticlesEndpoint(){
    service.get("/api/articles", (Request request, Response response) -> {
      log.debug("List of articles requested");
      response.type("application/json");
      return objectMapper.writeValueAsString(articleRepository.getArticles());
    });
  }

  private void searchArticleEndpoint(){
    service.get("/api/articles/:articleID", (Request request, Response response) -> {
      log.debug("Article by id requested");
      response.type("application/json");

      long id = DefaultControllerFunctions.parseLong(request.params("articleID"));
      var article = articleRepository.findArticle(id);
      if (article.isEmpty()) {
        throw new DefaultControllerFunctions.NotLocated(id, "No article with id: " + id);
      } else {
        return objectMapper.writeValueAsString(article.get());
      }
    });
  }

  private void addArticleEndpoint(){
    service.post("/api/articles/add", (Request request, Response response) -> {
      log.debug("Request to add an article");
      response.type("application/json");

      String body = request.body();
      ArticleRecord articleCreateRequest;
      try {
        articleCreateRequest = objectMapper.readValue(body, ArticleRecord.class);
      } catch (Exception e) {
        throw new DefaultControllerFunctions.ParseError(request.body(), "Couldn't make article from body");
      }
      Article article = new Article(
          articleCreateRequest.name(),
          articleCreateRequest.tags(),
          new ArrayList<>(),
          articleRepository.getNewID());
      articleRepository.addArticle(article);
      return objectMapper.writeValueAsString(article);
    });
  }

  private void deleteArticleEndpoint(){
    service.delete("/api/articles/delete/:articleID", (Request request, Response response) -> {
      log.debug("Article delete request");
      response.type("application/json");

      long id = DefaultControllerFunctions.parseLong(request.params("articleID"));
      var article = articleRepository.findArticle(id);
      if (article.isEmpty()) {
        throw new DefaultControllerFunctions.NotLocated(id, "No article with id: " + id);
      } else {
        articleRepository.delete(article.get().id, commentRepository);
        return objectMapper.writeValueAsString(Map.of("Message", "Successfully deleted article with id " + id));
      }
    });
  }

  private void editArticleEndpoint(){
    service.post("/api/articles/edit/:articleID", (Request request, Response response) -> {
      log.debug("Request to edit article");
      response.type("application/json");

      String body = request.body();
      ArticleEditRecord articleEditRequest;
      try {
        articleEditRequest = objectMapper.readValue(body, ArticleEditRecord.class);
      } catch (Exception e) {
        throw new DefaultControllerFunctions.ParseError(request.body(), "Couldn't make article from body");
      }

      long id = DefaultControllerFunctions.parseLong(request.params("articleID"));

      var article = articleRepository.findArticle(id);
      if (article.isEmpty()) {
        throw new DefaultControllerFunctions.NotLocated(id, "No article with id: " + id);
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
              articleEditRequest.commentContent(),
              commentRepository.getNewID());
          newArticle = newArticle.attachComment(comment);
          commentRepository.addComment(comment);
        }
        articleRepository.replace(newArticle);
        return objectMapper.writeValueAsString(newArticle);
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
