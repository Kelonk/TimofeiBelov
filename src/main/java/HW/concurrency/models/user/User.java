package HW.concurrency.models.user;

import java.util.HashMap;
import java.util.Map;

public class User {
  private Map<String, String> information;

  public User(Map<String, String> information) {
    this.information = new HashMap<>(information);
  }

  public String getInfoAtField(String field) {
    if (information.containsKey(field)) {
      return information.get(field);
    } else {
      throw new IllegalArgumentException("User doesn't contain field: " + field);
    }
  }

  public void updateInfo(String field, String value){
    information.put(field, value);
  }
}
