package HW.concurrency.enums;

import java.util.List;

public enum EnrichType {
  MSISDN("msisdn", List.of("firstName", "lastName"));

  private final String mapField;
  private final List<String> targetFields;

  EnrichType(String mapField, List<String> targetFields){
    this.mapField = mapField;
    this.targetFields = targetFields;
  }

  public String getMapField(){
    return mapField;
  }

  public List<String> getTargetFields(){
    return targetFields;
  }
}
