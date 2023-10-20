package HW.concurrency.models.enrich;

import HW.concurrency.enums.EnrichType;
import HW.concurrency.models.Message;
import HW.concurrency.models.user.User;
import HW.concurrency.models.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EnrichTestDefault {

  public static UserRepository emptyUserRepository(){
    UserRepository userRepository = new UserRepository() {
      private List<User> users;

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
        return;
      }

      @Override
      public void addUser(User user) {
        users.add(user);
      }
    };
    return userRepository;
  }

  public static void enrichType(EnrichAction enrichActionToTest, EnrichType enrichTypeToAssert) {
    Assertions.assertTrue(enrichActionToTest.enrichType() == enrichTypeToAssert);
  }

  public static void enrich(EnrichAction enrichActionToTest) {
    String debugValue = "MeowMeow";
    UserRepository userRepository = emptyUserRepository();
    Map<String, String> information = new HashMap<>();
    information.put(enrichActionToTest.enrichType().getMapField(), debugValue);
    for (String field : enrichActionToTest.enrichType().getTargetFields()) {
      information.put(field, debugValue);
    }

    userRepository.addUser(new User(information));
    Assertions.assertThrows(IllegalArgumentException.class, () -> enrichActionToTest.enrich(null, null));
    Assertions.assertThrows(RuntimeException.class, () -> enrichActionToTest.enrich(userRepository, new Message(null, null)));
    Assertions.assertThrows(RuntimeException.class, () -> enrichActionToTest.enrich(userRepository, new Message(new HashMap<>(), null)));
    Assertions.assertTrue(enrichActionToTest.enrich(
            userRepository,
            new Message(
                    Map.of(enrichActionToTest.enrichType().getMapField(), debugValue),
                    null))
            .equals(information));
  }
}
