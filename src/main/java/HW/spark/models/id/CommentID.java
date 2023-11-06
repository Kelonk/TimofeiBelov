package HW.spark.models.id;

public class CommentID implements ID{
  private final long id;

  public CommentID(long id){
    this.id = id;
  }

  @Override
  public boolean checkID(long id) {
    return id == this.id;
  }

  public long getId() {
    return id;
  }
}
