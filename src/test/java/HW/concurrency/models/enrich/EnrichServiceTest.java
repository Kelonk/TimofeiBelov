package HW.concurrency.models.enrich;

import HW.concurrency.enums.EnrichType;
import HW.concurrency.models.Message;
import HW.concurrency.models.user.User;
import HW.concurrency.models.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class EnrichServiceTest {

  private static boolean checkListContainsAnother(List<Map<String, String>> list1, List<Map<String, String>> list2){
    List<Map<String,String>> unMatchedRecords = list1.parallelStream().filter(map ->
            list2.parallelStream().noneMatch(compareMap ->
                    map.entrySet().stream().noneMatch(value1 ->
                            compareMap.entrySet().stream().noneMatch(value2 ->
                                    (value1.getKey().equals(value2.getKey()) &&
                                            value1.getValue().equals(value2.getValue()))))))
            .toList();

    //list1.containsAll(list2);
    return unMatchedRecords.isEmpty();
  }

  @Test
  void enrich() {
    Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new EnrichService(null, null));

    EnrichType enrichType = EnrichType.MSISDN;
    String uniField = enrichType.getMapField();

    UserRepository userRepository = EnrichTestDefault.emptyUserRepository();
    Map<String, String> information1 = new HashMap<>();
    Map<String, String> information2 = new HashMap<>();
    String uni1 = "UNIQUE1";
    String uni2 = "UNIQUE2";
    String uni3 = "UNIQUE3";
    String val1 = "Purrrr";
    String val2 = "Rahr";
    information1.put(uniField, uni1);
    information2.put(uniField, uni2);
    for (String field : enrichType.getTargetFields()) {
      information1.put(field, val1);
      information2.put(field, val2);
    }
    User user1 = new User(information1);
    User user2 = new User(information2);
    userRepository.addUser(user1);
    userRepository.addUser(user2);

    List<Message> messageList = List.of(
            new Message(
                    Map.of(uniField, uni1, "content", "Queque", "action", "Fly"),
                    Set.of(enrichType)), // test that can enrich
            new Message(
                    Map.of(uniField, uni1, "content", "Queque", enrichType.getTargetFields().get(0), "Fly"),
                    Set.of(enrichType)), // test that overwrites fields
            new Message(
                    Map.of(uniField, uni1, "content", "Queque", "action", "Fly"),
                    Set.of()), // test that won't change if message indicates so
            new Message(
                    Map.of(uniField, uni2, "content", "Queque", "action", "Fly"),
                    Set.of(enrichType)), // test that differentiates between users
            new Message(
                    Map.of(uniField, uni3, "content", "Queque", "action", "Fly"),
                    Set.of(enrichType)) // test that works when refers to unknown user
    );
    List<Map<String, String>> messageMapsBeforeTest = messageList.stream().map(Message::getContent).toList();
    /*
    List<Map<String, String>> messageMapsBeforeTest = List.of(
            new HashMap<>(Map.of(uniField, uni1, "content", "Queque", "action", "Fly")),
            new HashMap<>(Map.of(uniField, uni1, "content", "Queque", enrichType.getTargetFields().get(0), "Fly")),
            new HashMap<>(Map.of(uniField, uni1, "content", "Queque", "action", "Fly")),
            new HashMap<>(Map.of(uniField, uni2, "content", "Queque", "action", "Fly")),
            new HashMap<>(Map.of(uniField, uni3, "content", "Queque", "action", "Fly"))
    );
    */
    List<Map<String, String>> messageMapsToTest = messageMapsBeforeTest
            .stream()
            .map(e -> (Map<String, String>) new HashMap<>(e))
            .toList();
    messageMapsToTest.get(0).putAll(information1);
    messageMapsToTest.get(1).putAll(information1);
    messageMapsToTest.get(3).putAll(information2);
    List<Message> enrichedMessages = new ArrayList<>();

    EnrichService enrichService = new EnrichService(List.of(new EnrichMSISDN()), userRepository);

    for (Message message : messageList) {
      enrichedMessages.add(enrichService.enrich(message));
    }
    List<Map<String, String>> enrichedMessageMaps = enrichedMessages.stream().map(Message::getContent).toList();

    Assertions.assertTrue(checkListContainsAnother(
            messageMapsBeforeTest,
            messageList.stream().map(Message::getContent).toList()));
    Assertions.assertTrue(checkListContainsAnother(
            messageMapsToTest,
            enrichedMessageMaps));
    Assertions.assertThrows(IllegalArgumentException.class, () -> enrichService.enrich(null));
  }

  @Test
  void enrichConcurrent() {
    Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new EnrichService(null, null));

    EnrichType enrichType = EnrichType.MSISDN;
    String uniField = enrichType.getMapField();

    UserRepository userRepository = EnrichTestDefault.emptyUserRepository();
    Map<String, String> information1 = new HashMap<>();
    Map<String, String> information2 = new HashMap<>();
    String uni1 = "UNIQUE1";
    String uni2 = "UNIQUE2";
    String uni3 = "UNIQUE3";
    String val1 = "Purrrr";
    String val2 = "Rahr";
    information1.put(uniField, uni1);
    information2.put(uniField, uni2);
    for (String field : enrichType.getTargetFields()) {
      information1.put(field, val1);
      information2.put(field, val2);
    }
    User user1 = new User(information1);
    User user2 = new User(information2);
    userRepository.addUser(user1);
    userRepository.addUser(user2);

    List<Message> messageList = List.of(
            new Message(
                    Map.of(uniField, uni1, "content", "Queque", "action", "Fly"),
                    Set.of(enrichType)), // test that can enrich
            new Message(
                    Map.of(uniField, uni1, "content", "Queque", enrichType.getTargetFields().get(0), "Fly"),
                    Set.of(enrichType)), // test that overwrites fields
            new Message(
                    Map.of(uniField, uni1, "content", "Queque", "action", "Fly"),
                    Set.of()), // test that won't change if message indicates so
            new Message(
                    Map.of(uniField, uni2, "content", "Queque", "action", "Fly"),
                    Set.of(enrichType)), // test that differentiates between users
            new Message(
                    Map.of(uniField, uni3, "content", "Queque", "action", "Fly"),
                    Set.of(enrichType)) // test that works when refers to unknown user
    );
    List<Map<String, String>> messageMapsBeforeTest = messageList.stream().map(Message::getContent).toList();
    /*
    List<Map<String, String>> messageMapsBeforeTest = List.of(
            new HashMap<>(Map.of(uniField, uni1, "content", "Queque", "action", "Fly")),
            new HashMap<>(Map.of(uniField, uni1, "content", "Queque", enrichType.getTargetFields().get(0), "Fly")),
            new HashMap<>(Map.of(uniField, uni1, "content", "Queque", "action", "Fly")),
            new HashMap<>(Map.of(uniField, uni2, "content", "Queque", "action", "Fly")),
            new HashMap<>(Map.of(uniField, uni3, "content", "Queque", "action", "Fly"))
    );
    */
    List<Map<String, String>> messageMapsToTest = messageMapsBeforeTest
            .stream()
            .map(e -> (Map<String, String>) new HashMap<>(e))
            .toList();
    messageMapsToTest.get(0).putAll(information1);
    messageMapsToTest.get(1).putAll(information1);
    messageMapsToTest.get(3).putAll(information2);
    // to make its actual state be only in test thread
    List<Message> enrichedMessages = new CopyOnWriteArrayList<>();

    EnrichService enrichService = new EnrichService(List.of(new EnrichMSISDN()), userRepository);

    // concurrent process
    ExecutorService executorService = Executors.newFixedThreadPool(messageList.size());
    CountDownLatch latch = new CountDownLatch(messageList.size());
    for (Message message : messageList) {
      executorService.submit(() -> {
        enrichedMessages.add(enrichService.enrich(message));
        latch.countDown();
      });
    }
    try {
      latch.await();
    } catch (InterruptedException e) {
      System.out.println("Test was interrupted during concurrent process");
    }

    // test of successful work
    List<Map<String, String>> enrichedMessageMaps = enrichedMessages.stream().map(Message::getContent).toList();

    Assertions.assertTrue(checkListContainsAnother(
            messageMapsBeforeTest,
            messageList.stream().map(Message::getContent).toList()));
    Assertions.assertTrue(checkListContainsAnother(
            messageMapsToTest,
            enrichedMessageMaps));
    Assertions.assertThrows(IllegalArgumentException.class, () -> enrichService.enrich(null));
  }
}