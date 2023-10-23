package HW.concurrency.models.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

  @Test
  void getInfoAtField() {
    String debugValue = "Meoooowww";
    User user = new User(Map.of(debugValue, debugValue));
    Assertions.assertThrows(IllegalArgumentException.class, () -> user.getInfoAtField(debugValue.toUpperCase()));
    Assertions.assertEquals(user.getInfoAtField(debugValue), debugValue);
  }

  @Test
  void updateInfo() {
    String debugValue = "Meoooowww";
    User user = new User(Map.of(debugValue, debugValue));
    Assertions.assertThrows(IllegalArgumentException.class, () -> user.getInfoAtField(debugValue.toUpperCase()));
    user.updateInfo(debugValue.toUpperCase(), debugValue);
    Assertions.assertEquals(user.getInfoAtField(debugValue.toUpperCase()), debugValue);
  }
}