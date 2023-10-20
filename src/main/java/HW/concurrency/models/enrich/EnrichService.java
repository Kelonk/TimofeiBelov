package HW.concurrency.models.enrich;

import HW.concurrency.enums.EnrichType;
import HW.concurrency.models.Message;
import HW.concurrency.models.user.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EnrichService {
  private final Map<EnrichType, EnrichAction> enrichActionMap;
  private final List<EnrichAction> enrichActionList;
  private final UserRepository userRepository;

  public EnrichService(List<EnrichAction> enrichActionList, UserRepository userRepository) {
    if (enrichActionList == null || enrichActionList.size() == 0 || userRepository == null) {
      throw new IllegalArgumentException("EnrichService has requirements for its values: " +
              " not null enrichActionList " + Boolean.toString(enrichActionList == null) +
              " not empty enrichActionList " + Boolean.toString(enrichActionList.size() == 0) +
              " not null userRepository " + Boolean.toString(userRepository == null));
    }
    this.enrichActionList = enrichActionList;
    this.userRepository = userRepository;
    Set<EnrichType> enrichSet = new HashSet<>();
    this.enrichActionMap = enrichActionList.stream()
            .filter(e -> enrichSet.add(e.enrichType()))
            .collect(Collectors.toMap(e -> e.enrichType(), e -> e));
  }

  public Message enrich(Message message){
    if (message == null) {
      throw new IllegalArgumentException("Tried to enrich null");
    }
    Message messageCopy = null;
    synchronized (message) {
      messageCopy = new Message(message.getContent(), message.enrichTypes);
      int enrichCounter = 0;
      for (EnrichType enrichType : message.enrichTypes) {
        EnrichAction enrichAction = enrichActionMap.getOrDefault(enrichType, null);
        try {
          messageCopy.setContent(enrichAction.enrich(userRepository, message));
          enrichCounter += 1;
        } catch (RuntimeException e) {
          System.out.println(e.getMessage());
        }
      }
      System.out.printf("Out of %d enrichments to make %d were successful%n", message.enrichTypes.size(), enrichCounter);
    }
    return messageCopy;
  }
}
