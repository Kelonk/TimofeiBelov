package HW.concurrency.models.user;

public interface UserRepository {
  User findByField();
  void updateUserByMsisdn(String msisdn, User user);
}
