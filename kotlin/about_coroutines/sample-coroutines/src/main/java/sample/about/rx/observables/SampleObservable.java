package sample.about.rx.observables;

import io.reactivex.rxjava3.core.Observable;
import java.util.Arrays;

/**
 * https://github.com/mgp/effective-rxjava/blob/master/items/understand-observable-and-observer-chains.md
 */
public class SampleObservable {

  private static void testChain() {
    Observable.just(1, 2, 3, 4, 5, 6)
        .filter(x -> (x & 1) == 1)
        .map(x -> x * x)
        .subscribe(System.out::println)
        .dispose();
  }

  private static void testZip() {
    Observable<Integer> o1 =
        Observable.just(1, 2, 3, 4, 5, 6).filter(x -> (x & 1) == 1).map(x -> x * x);

    Observable<Integer> o2 =
        Observable.just(1, 2, 3, 4, 5, 6).filter(x -> (x & 1) == 0).map(x -> x * x);

    Observable.zip(
            o1,
            o2,
            (obs1, obs2) -> {
              System.out.println(obs1);
              System.out.println(obs2);
              return Observable.empty();
            })
        .doOnDispose(
            () -> {
              System.out.println("dispose");
            });
  }

  private static void checkStaticFactoryFunction() {
    Observable<Integer> o1 = Observable.just(1, 2, 3, 4, 5, 6);
    Observable<Integer> o2 = o1.filter(x -> (x & 1) == 1);
    Observable<Integer> o3 = o2.map(x -> x * x);
    o3.subscribe(System.out::println).dispose();
  }

  private static void limitArgsObservableZip() {
    Observable.zip(
            Arrays.asList(
                Observable.just(1),
                Observable.just(2),
                Observable.just(3),
                Observable.just(4),
                Observable.just(5),
                Observable.just(6),
                Observable.just(7),
                Observable.just(8),
                Observable.just(9),
                Observable.just(10),
                Observable.just(11)),
            observables -> {
              for (Object o : observables) {
                System.out.println(o);
              }
              return Observable.just(observables);
            })
        .subscribe();
  }

  /*
     https://www.baeldung.com/rx-java#:~:text=about%20back%2Dpressure-,here.,-4.3.%20Create%20Observable
  */

  private static void checkCreationOfObservable() {
    /*
       When we want to get information out of an Observable,
        we implement an observer interface and then call subscribe on the desired Observable:
    */

    Observable<String> observable = Observable.just("Observale Simples String");
    observable
        .subscribe(
            s -> {
              System.out.printf("Ola, sou o conteudo do observable = %s", s);
            })
        .dispose();
  }

  public static void main(String[] args) {
    checkCreationOfObservable();
  }
}
