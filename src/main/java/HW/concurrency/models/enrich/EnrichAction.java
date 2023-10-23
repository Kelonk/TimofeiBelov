package HW.concurrency.models.enrich;

import HW.concurrency.enums.EnrichType;
import HW.concurrency.models.Message;
import HW.concurrency.models.user.UserRepository;

import java.util.Map;

public interface EnrichAction {
  EnrichType enrichType();
  Map<String, String> enrich(UserRepository userRepository, Message message);
}
