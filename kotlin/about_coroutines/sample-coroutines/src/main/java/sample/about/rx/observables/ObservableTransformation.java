package sample.about.rx.observables;

import io.reactivex.rxjava3.core.Observable;

public class ObservableTransformation {

  /*
     https://www.baeldung.com/rx-java#operators
  */

  /*
     https://www.baeldung.com/rx-java#1-map/
     The map operator transforms items emitted by an Observable by applying a function to each item.
  */
  private static void checkMap() {
    final String[] alphabet = "abcdefghijklmnopqrstuvxwyz".split("");
    StringBuilder sb = new StringBuilder();
    Observable.fromArray(alphabet)
        .map(String::toUpperCase) // aplicar uma funcao de transformacao para cada item do array
        .subscribe(sb::append)
        .dispose();

    System.out.println(sb);
  }

  /*

  */
  private static void checkFlatMap() {
    Observable<String> mock = Observable.just("Mocking string");

    StringBuilder sb = new StringBuilder();
    Observable.just("1", "2")
        .flatMap(
            number ->
                // combine
                mock.map(str -> String.format("%s: %s", number, str)))
        .subscribe(
            s -> {
              if (sb.length() == 0) {
                sb.append(s);
              } else {
                sb.append(", ").append(s);
              }
            })
        .dispose();

    System.out.println(sb);
  }

  public static void main(String[] args) {
    checkFlatMap();
  }
}
