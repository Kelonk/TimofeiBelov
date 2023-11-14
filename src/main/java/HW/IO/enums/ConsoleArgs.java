package HW.IO.enums;

public enum ConsoleArgs {
  From("from"), To("to"), Filter("filter");

  public final String key;

  ConsoleArgs(String key) {
    this.key = key;
  }
}
