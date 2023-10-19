package HW.concurrency.models.user;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryWithList implements UserRepository{
  private List<User> users;

  public UserRepositoryWithList(List<User> users) {
    this.users = users == null ? new ArrayList<>() : users;
  }

  @Override
  public User findByField(String field, String value) {
    return null;
  }

  @Override
  public void updateUserByField(String field, String value, User user) {
    User locatedUser = findByField(field, value);
  }
}
