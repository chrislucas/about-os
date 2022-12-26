package sample;

public class ThreadUtils {

  public static String message(String message) {
    return String.format("%s, %s", Thread.currentThread().getName(), message);
  }
}
