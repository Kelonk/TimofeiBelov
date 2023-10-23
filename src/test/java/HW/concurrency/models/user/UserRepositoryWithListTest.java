package HW.concurrency.models.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryWithListTest {

  @Test
  void findByField() {
    UserRepository userRepository = new UserRepositoryWithList(null);
    UserRepositoryTestDefault.findByField(userRepository);
  }

  @Test
  void updateUserByField() {
    UserRepository userRepository = new UserRepositoryWithList(null);
    UserRepositoryTestDefault.updateUserByField(userRepository);
  }

  @Test
  void addUser() {
    UserRepository userRepository = new UserRepositoryWithList(null);
    UserRepositoryTestDefault.addUser(userRepository);
  }
}