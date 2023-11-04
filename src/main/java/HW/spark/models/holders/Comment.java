package HW.spark.models.holders;

import HW.spark.models.id.ArticleID;
import HW.spark.models.id.CommentID;

public class Comment {
  private final ArticleRepository articleRepository;
  private final CommentRepository commentRepository;
  public final CommentID id;
  public final ArticleID articleID;
  public final String content;

  public Comment(ArticleRepository articleRepository, CommentRepository commentRepository, ArticleID articleID, String content) {
    if (articleID == null || content == null || articleRepository == null  || commentRepository == null) {
      throw new IllegalArgumentException("Null given to Comment");
    }
    this.articleRepository = articleRepository;
    this.commentRepository = commentRepository;
    this.id = new CommentID();
    this.articleID = articleID;
    this.content = content;
    commentRepository.addComment(this);
  }

  // removes it from associated repository
  public void remove(){
    commentRepository.delete(id);
  }
}
