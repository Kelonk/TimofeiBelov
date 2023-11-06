package HW.spark.models.holders;

import HW.spark.models.id.ArticleID;
import HW.spark.models.id.CommentID;

import java.util.*;

public class Article {
  public final String name;
  public final ArticleID id;
  public final Set<String> tags;
  public final List<Comment> comments;

  public Article(String name, Set<String> tags, List<Comment> comments, long id) {
    if (name == null || tags == null || comments == null) {
      throw new IllegalArgumentException("Null given to article");
    }
    this.name = name;
    this.id = generateID(id);
    this.tags = tags;
    this.comments = comments;
  }

  private Article(String name, Set<String> tags, List<Comment> comments, ArticleID id) {
    if (name == null || tags == null || comments == null || id == null) {
      throw new IllegalArgumentException("Null given to article");
    }
    this.name = name;
    this.id = id;
    this.tags = tags;
    this.comments = comments;
  }

  private ArticleID generateID(long id){
    return new ArticleID(id);
  }

  // overwrites ArticleID in repository
  public Article attachComment(Comment comment){
    if (comments.stream().filter(e -> e.id == comment.id).findAny().isPresent()) {
      return this;
    }
    List<Comment> newComments = new ArrayList<>();
    Collections.copy(newComments, comments);
    newComments.add(comment);
    // articles.put(id, newArticle);
    return new Article(name, tags, newComments, id);
  }

  public Article deleteComment(long cID){
    Optional<Comment> comment = comments.stream().filter(e -> e.id.checkID(cID)).findAny();
    if (comment.isEmpty()) {
      return this;
    }
    List<Comment> newComments = new ArrayList<>();
    Collections.copy(newComments, comments);
    newComments.remove(comment.get());
    return new Article(name, tags, newComments, id);
  }

  public Article newTags(Set<String> tags){
    if (tags == null) {
      throw new IllegalArgumentException("Null used as tags");
    }
    return new Article(name, tags, comments, id);
  }

  public Article newName(String name){
    if (name == null) {
      throw new IllegalArgumentException("Null used as name");
    }
    return new Article(name, tags, comments, id);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Article){
      return (((Article) obj).id.getId() == id.getId()
          && Objects.equals(((Article) obj).name, name)
          && ((Article) obj).tags.equals(tags)
          && ((Article) obj).comments.equals(comments));
    } else {
      return false;
    }
  }
}
