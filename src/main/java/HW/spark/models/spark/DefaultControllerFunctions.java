package HW.spark.models.spark;

import org.slf4j.Logger;
import spark.Request;
import spark.Response;
import spark.Service;

import java.util.Map;
import java.util.NoSuchElementException;

public class DefaultControllerFunctions {
  public static class ParseError extends RuntimeException{
    public final Object value;
    public final String message;

    public ParseError(Object value, String message) {
      this.value = value;
      this.message = message;
    }
  }

  public static class NotLocated extends RuntimeException{
    public final Object value;
    public final String message;

    public NotLocated(Object value, String message) {
      this.value = value;
      this.message = message;
    }
  }

  public static long parseLong (String value) throws ParseError{
    try {
      return Long.parseLong(value);
    } catch (NumberFormatException e) {
      throw new ParseError(value, "Unable to parse value to long");
    }
  }

  public static void internalErrorHandle(Service service, Logger log){
    service.internalServerError(((Request request, Response response) -> {
      log.error("Unknown error happened " + response.body());
      response.status(500);
      response.type("application/json");
      return response.body();
    }));
  }

  public static void parseExceptionHandle(Service service, Logger log){
    service.exception(ParseError.class, (exception, request, response) -> {
      log.warn("Unrecognisable value: " + exception.value.toString());
      response.status(404);
      response.type("application/json");
      response.body(exception.message);
    });
  }

  public static void notFoundExceptionHandle(Service service, Logger log){
    service.exception(NotLocated.class, (exception, request, response) -> {
      log.warn("Didn't find object: " + exception.value.toString());
      response.status(404);
      response.type("application/json");
      response.body(exception.message);
    });
  }
}
