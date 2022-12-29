package sample.about.rx.observables;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;

public class ObservableMethods {

  /**
   * 4.4. OnNext, OnError, and OnCompleted
   *
   * <p>OnNext: is called on our observer each time a new event is published to the attached
   * Observable. This is the method where we'll perform some action on each event
   *
   * <p>OnCompleted is called when the sequence of events associated with an Observable is complete,
   * indicating that we should not expect any more onNext calls on our observer
   *
   * <p>OnError is called when an unhandled exception is thrown during the RxJava framework code or
   * our event handling code
   */
  private static void check() {
    String[] letters = {"a", "b", "c", "d", "e", "f", "g"};
    Observable<String> observable = Observable.fromArray(letters);

    Consumer<String> onNext = System.out::println;

    Consumer<Throwable> onError = Throwable::printStackTrace;

    Action onComplete =
        () -> {
          System.out.println("OnComplete");
        };
    observable.subscribe(onNext, onError, onComplete).dispose();

    StringBuilder sb = new StringBuilder();
    observable.subscribe(sb::append, Throwable::printStackTrace, () -> {}).dispose();
  }

  private static void check1() {
    String[] letters = {"a", "b", "c", "d", "e", "f", "g"};
    Observable<String> observable = Observable.fromArray(letters);

    Consumer<Throwable> onError = Throwable::printStackTrace;

    StringBuilder sb = new StringBuilder();
    Consumer<String> onNext = sb::append;
    Action onComplete = () -> sb.append("_completed");

    observable.subscribe(onNext, onError, onComplete).dispose();
    System.out.println(sb);
  }

  public static void main(String[] args) {
    check1();
  }
}
