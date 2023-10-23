package HW.concurrency.models.user;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserRepositoryWithList implements UserRepository{
  private CopyOnWriteArrayList<User> users;

  public UserRepositoryWithList(List<User> users) {
    this.users = new CopyOnWriteArrayList<>(users != null ? users : List.of());
  }

  @Override
  public User findByField(String field, String value) {
    Optional<User> locatedUser = users.parallelStream().filter(user -> user.getInfoAtField(field).equals(value)).findFirst();
    if (locatedUser.isPresent()){
      return locatedUser.get();
    } else {
      throw new IllegalArgumentException("Can't locate user with field: " + field + " value: " + value);
    }
  }

  @Override
  public void updateUserByField(String field, String value, User user) {
    try {
      User locatedUser = findByField(field, value);
      users.set(users.indexOf(locatedUser), user);
    } catch (IllegalArgumentException e) {
      System.out.println("No user to update");
    }
  }

  @Override
  public void addUser(User user) {
    if (!users.contains(user)) {
      users.add(user);
    }
  }
}
