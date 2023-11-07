package HW.spark.models.spark;

import HW.spark.models.holders.Article;
import HW.spark.models.holders.ArticleRepository;
import HW.spark.models.holders.CommentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;
import spark.Service;

import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;

public class CommentController implements Controller {
  private final Logger log;
  private final ArticleRepository articleRepository;
  private final CommentRepository commentRepository;
  private final Service service;
  private final ObjectMapper objectMapper;

  public CommentController(Logger log,
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
    deleteCommentEndpoint();
    DefaultControllerFunctions.internalErrorHandle(service, log);
    DefaultControllerFunctions.parseExceptionHandle(service, log);
    DefaultControllerFunctions.notFoundExceptionHandle(service, log);
  }

  private void deleteCommentEndpoint(){
    service.get("/api/comments/delete/:commentID", (Request request, Response response) -> {
      log.debug("Delete comment by id");
      response.type("application/json");

      long id = DefaultControllerFunctions.parseLong(request.params("commentID"));
      var comment = commentRepository.findComment(id);
      if (comment.isEmpty()) {
        throw new DefaultControllerFunctions.NotLocated(id, "No comment with id: " + id);
      } else {
        Article newArticle;
        try {
          newArticle = articleRepository.findArticle(comment.get().articleID.getId()).get().deleteComment(id);
          articleRepository.replace(newArticle);
        } catch (NoSuchElementException e) {
          return objectMapper.writeValueAsString(Map.of("Message", "Comment removed but no attachment to article"));
        } finally {
          commentRepository.delete(comment.get().id);
        }
        return objectMapper.writeValueAsString(Map.of("Message", "Comment removed"));
      }
    });
  }

  @Override
  public void init() {
    service.init();
    service.awaitInitialization();
    log.debug("Comment controller started");
  }
}
