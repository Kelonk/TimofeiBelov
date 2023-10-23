package HW.concurrency.models.enrich;

import HW.concurrency.enums.EnrichType;
import HW.concurrency.models.Message;
import HW.concurrency.models.user.User;
import HW.concurrency.models.user.UserRepository;

import java.util.List;
import java.util.Map;

public class EnrichMSISDN implements EnrichAction{

  @Override
  public EnrichType enrichType() {
    return EnrichType.MSISDN;
  }

  @Override
  public Map<String, String> enrich(UserRepository userRepository, Message message) {
    if (userRepository == null || message == null) {
      throw new IllegalArgumentException("Null parameters were provided to enrich");
    }
    String filterField = enrichType().getMapField();
    Map<String, String> input = message.getContent();
    if (!input.containsKey(filterField)) {
      throw new RuntimeException("Provided message doesn't has necessary field");
    }
    User user = userRepository.findByField(filterField, input.get(filterField)); // will throw error if can't find
    for (String field : fieldsToEdit()) {
      input.put(field, user.getInfoAtField(field)); // will throw error if no such field
    }
    //Message newMessage = new Message(input, message.enrichTypes);
    //message.setContent(input);
    return input;
  }

  private List<String> fieldsToEdit() {
    return enrichType().getTargetFields();
  }
}
