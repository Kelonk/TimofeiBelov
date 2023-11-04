package HW.spark.models.holders;

import HW.spark.models.id.ArticleID;
import HW.spark.models.id.CommentID;

public class Comment {
  public final CommentID id;
  public final ArticleID articleID;
  public final String content;

  public Comment(ArticleID articleID, String content) {
    if (articleID == null || content == null) {
      throw new IllegalArgumentException("Null given to Comment");
    }
    this.id = new CommentID();
    this.articleID = articleID;
    this.content = content;
  }

  private Comment(ArticleID articleID, String content, CommentID id) {
    if (articleID == null || content == null) {
      throw new IllegalArgumentException("Null given to Comment");
    }
    this.id = id;
    this.articleID = articleID;
    this.content = content;
  }

  public Comment newContent(String content){
    if (content == null) {
      throw new IllegalArgumentException("Null given to Comment");
    }
    return new Comment(articleID, content, id);
  }
}
