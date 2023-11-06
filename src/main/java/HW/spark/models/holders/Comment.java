package HW.spark.models.holders;

import HW.spark.models.id.ArticleID;
import HW.spark.models.id.CommentID;

import java.util.Objects;

public class Comment {
  public final CommentID id;
  public final ArticleID articleID;
  public final String content;

  public Comment(ArticleID articleID, String content, long id) {
    if (articleID == null || content == null) {
      throw new IllegalArgumentException("Null given to Comment");
    }
    this.id = generateID(id);
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

  private CommentID generateID(long id){
    return new CommentID(id);
  }

  public Comment newContent(String content){
    if (content == null) {
      throw new IllegalArgumentException("Null given to Comment");
    }
    return new Comment(articleID, content, id);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Comment){
      return (((Comment) obj).id.getId() == id.getId()
          && Objects.equals(((Comment) obj).content, content)
          && ((Comment) obj).articleID.equals(articleID));
    } else {
      return false;
    }
  }
}
