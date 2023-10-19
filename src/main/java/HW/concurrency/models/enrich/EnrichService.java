package HW.concurrency.models.enrich;

import HW.concurrency.enums.EnrichType;
import HW.concurrency.models.Message;
import HW.concurrency.models.user.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EnrichService {
  private final Map<EnrichType, List<EnrichAction>> enrichActionMap;
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
    this.enrichActionMap = enrichActionList.stream().collect(Collectors.groupingBy(EnrichAction::enrichType));
  }

  public void enrich(Message message){
    if (message == null) {
      throw new IllegalArgumentException("Tried to enrich null");
    }
    synchronized (message) {
      int enrichCounter = 0;
      for (EnrichType enrichType : message.enrichTypes) {
        for (EnrichAction enrichAction : enrichActionMap.getOrDefault(enrichType, List.of())) {
          try {
            enrichAction.enrich(userRepository, message);
            enrichCounter += 1;
            break;
          } catch (RuntimeException e) {
            System.out.println(e.getMessage());
          }
        }
      }
      System.out.printf("Out of %d enrichments to make %d were successful%n", message.enrichTypes.size(), enrichCounter);
    }
  }
}
