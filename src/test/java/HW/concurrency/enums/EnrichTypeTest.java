package HW.concurrency.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnrichTypeTest {

  @Test
  void getMapField() {
    for (EnrichType enrichType : EnrichType.values()) {
      Assertions.assertNotNull(enrichType.getMapField());
    }
  }

  @Test
  void getTargetFields() {
    for (EnrichType enrichType : EnrichType.values()) {
      Assertions.assertNotNull(enrichType.getTargetFields());
    }
  }
}