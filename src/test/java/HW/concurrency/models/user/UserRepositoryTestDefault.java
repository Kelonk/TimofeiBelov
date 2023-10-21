package HW.concurrency.models.user;

import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.Map;

class UserRepositoryTestDefault {

  public static void findByField(UserRepository userRepositoryToTest){
    String debugField = "MeowMeow";
    User user = new User(Map.of(debugField, debugField));
    userRepositoryToTest.addUser(user);

    Assertions.assertThrows(IllegalArgumentException.class,
            () -> userRepositoryToTest.findByField(debugField.toUpperCase(), debugField));
    Assertions.assertEquals(user, userRepositoryToTest.findByField(debugField, debugField));
  }

  public static void updateUserByField(UserRepository userRepositoryToTest){
    String debugField = "MeowMeow";
    Assertions.assertDoesNotThrow(() -> userRepositoryToTest.updateUserByField(debugField, debugField, new User(new HashMap<>())));

    User user = new User(Map.of(debugField, debugField));
    userRepositoryToTest.addUser(user);
    User newUser = new User(Map.of(debugField, debugField, debugField.toUpperCase(), debugField));
    userRepositoryToTest.updateUserByField(debugField, debugField, newUser);

    Assertions.assertEquals(newUser, userRepositoryToTest.findByField(debugField, debugField));
  }

  public static void addUser(UserRepository userRepositoryToTest){
    String debugField = "MeowMeow";
    User user = new User(Map.of(debugField, debugField));
    userRepositoryToTest.addUser(user);

    Assertions.assertDoesNotThrow(() -> userRepositoryToTest.findByField(debugField, debugField));
  }
}