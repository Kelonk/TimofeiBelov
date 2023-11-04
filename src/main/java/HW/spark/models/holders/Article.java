package HW.spark.models.holders;

import HW.spark.models.id.ArticleID;
import HW.spark.models.id.CommentID;

import java.util.*;

public class Article {
  private final ArticleRepository articleRepository;
  public final String name;
  public final ArticleID id;
  public final Set<String> tags;
  public final List<Comment> comments;

  public Article(ArticleRepository articleRepository, String name, Set<String> tags, List<Comment> comments) {
    if (name == null || tags == null || comments == null || articleRepository == null) {
      throw new IllegalArgumentException("Null given to article");
    }
    this.articleRepository = articleRepository;
    this.name = name;
    this.id = new ArticleID();
    this.tags = tags;
    this.comments = comments;
    articleRepository.addArticle(this);
  }

  private Article(ArticleRepository articleRepository, String name, Set<String> tags, List<Comment> comments, ArticleID id) {
    if (name == null || tags == null || comments == null || id == null || articleRepository == null) {
      throw new IllegalArgumentException("Null given to article");
    }
    this.articleRepository = articleRepository;
    this.name = name;
    this.id = id;
    this.tags = tags;
    this.comments = comments;
    articleRepository.addArticle(this);
  }

  // overwrites ArticleID in repository
  public Article attachComment(Comment comment){
    if (comments.stream().filter(e -> e.id == comment.id).findAny().isPresent()) {
      return this;
    }
    List<Comment> newComments = new ArrayList<>();
    Collections.copy(newComments, comments);
    newComments.add(comment);
    Article newArticle = new Article(articleRepository, name, tags, newComments, id);
    // articles.put(id, newArticle);
    return newArticle;
  }

  public Article deleteComment(long cID){
    Optional<Comment> comment = comments.stream().filter(e -> e.id.checkID(cID)).findAny();
    if (comment.isEmpty()) {
      return this;
    }
    List<Comment> newComments = new ArrayList<>();
    Collections.copy(newComments, comments);
    newComments.remove(comment.get());
    comment.get().remove();
    return new Article(articleRepository, name, tags, newComments, id);
  }

  public Article newTags(Set<String> tags){
    if (tags == null) {
      throw new IllegalArgumentException("Null used as tags");
    }
    return new Article(articleRepository, name, tags, comments, id);
  }

  public Article newName(String name){
    if (name == null) {
      throw new IllegalArgumentException("Null used as name");
    }
    return new Article(articleRepository, name, tags, comments, id);
  }
}
