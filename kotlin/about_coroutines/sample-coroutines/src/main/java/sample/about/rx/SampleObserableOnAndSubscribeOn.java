package sample.about.rx;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import sample.ThreadUtils;

/** https://medium.com/techshots/rxjava-schedulers-observeon-subscribeon-7d22ca481ecc */
public class SampleObserableOnAndSubscribeOn {

  public static void main(String[] args) {

    ObservableOnSubscribe<String> handler =
        emitter -> {
          System.out.printf(ThreadUtils.message("Subscribed"));
          emitter.onNext("Step 1");
          emitter.onComplete();
        };

    Observable.create(handler)
        .map(
            emitter -> {
              return emitter;
            })
        .subscribe(
            consumer -> {
              System.out.println(ThreadUtils.message(consumer));
            });
  }
}
