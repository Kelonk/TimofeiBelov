package HW.spark.models.holders;

import HW.spark.models.id.ArticleID;
import HW.spark.models.id.CommentID;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.*;
import java.util.stream.Collectors;

public class Article {
  public static int trendingThreshold = loadThresholdProperty();

  public final String name;
  public final ArticleID id;
  public final boolean trending;
  public final Set<String> tags;
  public final List<Comment> comments;

  @JsonCreator Article(
      @JsonProperty("name") String name,
      @JsonProperty("id") ArticleID id,
      @JsonProperty("tags") Set<String> tags,
      @JsonProperty("comments") List<Comment> comments
  ){
    this.name = name;
    this.id = id;
    this.trending = comments.size() > trendingThreshold;
    this.tags = tags;
    this.comments = comments;
  }

  public Article(String name, Set<String> tags, List<Comment> comments, long id) {
    if (name == null || tags == null || comments == null) {
      throw new IllegalArgumentException("Null given to article");
    }
    this.name = name;
    this.id = generateID(id);
    this.trending = comments.size() > trendingThreshold;
    this.tags = tags;
    this.comments = comments;
  }

  private Article(String name, Set<String> tags, List<Comment> comments, ArticleID id) {
    if (name == null || tags == null || comments == null || id == null) {
      throw new IllegalArgumentException("Null given to article");
    }
    this.name = name;
    this.id = id;
    this.trending = comments.size() > trendingThreshold;
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
    List<Comment> newComments = new ArrayList<>(comments);
    newComments.add(comment);
    // articles.put(id, newArticle);
    return new Article(name, tags, newComments, id);
  }

  public Article deleteComment(long cID){
    Optional<Comment> comment = comments.stream().filter(e -> e.id.checkID(cID)).findAny();
    if (comment.isEmpty()) {
      return this;
    }
    List<Comment> newComments = comment.stream().filter(e -> !e.equals(comment.get())).collect(Collectors.toList());
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

  private static int loadThresholdProperty(){
    Config config = ConfigFactory.load();
    return config.getInt("app.rules.article.trending.threshold");
  }
}
