package sample.about.rx.observables;

import io.reactivex.rxjava3.core.Observable;

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

  public static void main(String[] args) {
    checkStaticFactoryFunction();
  }
}
