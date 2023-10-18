package HW.concurrency.models;

import HW.concurrency.enums.EnrichType;

import java.util.Map;
import java.util.Set;

public class Message {
  private Map<String, String> content;
  private Set<EnrichType> enrichTypes;

  public static String toString(Message message){
    return "";
  }
}
