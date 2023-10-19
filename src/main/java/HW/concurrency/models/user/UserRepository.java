package HW.concurrency.models.user;

public interface UserRepository {
  User findByField(String field, String value);
  void updateUserByField(String field, String value, User user);
}
