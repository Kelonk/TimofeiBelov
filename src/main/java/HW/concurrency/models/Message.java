package HW.concurrency.models;

import HW.concurrency.enums.EnrichType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Message {
  private Map<String, String> content;
  public final Set<EnrichType> enrichTypes;

  public Message(Map<String, String> content, Set<EnrichType> enrichTypes) {
    this.content = content;
    this.enrichTypes = enrichTypes;
  }

  public Map<String, String> getContent() {
    return new HashMap<>(content);
  }

  public void setContent(Map<String, String> content) {
    this.content = content;
  }

  public static String toString(Message message){
    String answer = "Message with:\n";
    answer += "Following enrich types: " + message.enrichTypes.stream().map(Enum::toString).collect(Collectors.joining(" "));
    answer += "Following content:\n" + message.content.entrySet().stream().map(Object::toString).collect(Collectors.joining("\n"));
    return answer;
  }
}
