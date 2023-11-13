package HW.spark.models.id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentID {
  private final long id;

  @JsonCreator
  public CommentID(@JsonProperty("id") long id){
    this.id = id;
  }

  public boolean checkID(long id) {
    return id == this.id;
  }

  public long getId() {
    return id;
  }
}
