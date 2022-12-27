package sample.about.rx;

import io.reactivex.rxjava3.core.Flowable;

public class HelloWorld {

  public static void main(String[] args) {
    /** https://github.com/ReactiveX/RxJava */
    Flowable.just("Olar").subscribe(System.out::println).dispose();
  }
}
