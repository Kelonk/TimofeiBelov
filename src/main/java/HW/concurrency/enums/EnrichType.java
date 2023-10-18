package HW.concurrency.enums;

public enum EnrichType {
  MSISDN("msisdn");

  private final String mapField;

  EnrichType(String mapField){
    this.mapField = mapField;
  }

  public String getMapField(){
    return mapField;
  }
}
