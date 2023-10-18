package HW.concurrency.models.enrich;

import HW.concurrency.models.Message;
import HW.concurrency.models.user.UserRepository;

import java.util.List;

public class EnrichService {
  private final List<EnrichAction> enrichActionList;
  private final UserRepository userRepository;

  public EnrichService(List<EnrichAction> enrichActionList, UserRepository userRepository) {
    this.enrichActionList = enrichActionList;
    this.userRepository = userRepository;
  }

  public Message enrich(Message message){
    return message;
  }
}
