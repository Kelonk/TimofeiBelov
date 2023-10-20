package HW.concurrency.models;

import HW.concurrency.enums.EnrichType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {
  private static Message testMessage;

  private static void generateTestMessage(Map<String, String> content, Set<EnrichType> enrichTypes){
    if (testMessage != null) {
      return;
    }
    if (content == null) {
      content = Message.generateRandomContent();
    }
    if (enrichTypes == null) {
      enrichTypes = Set.of(EnrichType.MSISDN);
    }
    testMessage = new Message(content, enrichTypes);
  }

  @Test
  void getContent() {
    generateTestMessage(null, null);
    Map<String, String> content = testMessage.getContent();
    content.clear();
    Assertions.assertTrue(content.isEmpty() && !testMessage.getContent().isEmpty());
  }

  @Test
  void setContent() {
    generateTestMessage(null, null);
    Message messageCopy = new Message(testMessage.getContent(), testMessage.enrichTypes);
    Map<String, String> emptyContent = new HashMap<>();
    messageCopy.setContent(emptyContent);
    Assertions.assertTrue(messageCopy.getContent().isEmpty());
  }

  @Test
  void testToString() {
    generateTestMessage(null, null);
    Assertions.assertDoesNotThrow(() -> testMessage.toString());
  }
}