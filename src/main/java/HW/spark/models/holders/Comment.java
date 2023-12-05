package HW.spark.models.holders;

import HW.spark.models.id.ArticleID;
import HW.spark.models.id.CommentID;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Comment {
  public final CommentID id;
  public final ArticleID articleID;
  public final String content;

  @JsonCreator Comment(
      @JsonProperty("id") CommentID id,
      @JsonProperty("articleID") ArticleID articleID,
      @JsonProperty("content") String content
  ){
    this.id = id;
    this.articleID = articleID;
    this.content = content;
  }

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
          && ((Comment) obj).content.equals(content)
          && ((Comment) obj).articleID.getId() == articleID.getId());
    } else {
      return false;
    }
  }
}
