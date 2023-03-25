package sample.generics.variance;

public class DeclarativeSiteVariance {

  public interface AbstractProducer<T> {
    /*
       Nao temos metodos que recebem T so que retornam. É uma interface que PRODUZ e nao CONSOME
    */
    T get();
  }

  public static class WrapperAbstractProducer<T> implements AbstractProducer<T> {

    private final T value;

    public WrapperAbstractProducer(T value) {
      this.value = value;
    }

    @Override
    public T get() {
      return value;
    }
  }

  interface MutableAbstractProducer<T> {
    T get();

    <O extends T> void set(O value);
  }

  static class MutableWrapperProducer<T> implements MutableAbstractProducer<T> {

    private T value;

    MutableWrapperProducer(T value) {
      this.value = value;
    }

    @Override
    public T get() {
      return value;
    }

    @Override
    public <O extends T> void set(O value) {
      this.value = value;
    }
  }

  private static void notAllowedSet(AbstractProducer<String> abstractProducer) {
    /*
       Nem se fosse Box<String> box
       Mesmo nao avendo consumer-methods (add, set) como o compilador nao pode prever
       se um dia nao vai ter, entao nao permite
    */
    // AbstractProducer<Object> obj = abstractProducer;

    /*
       Para podermos fazer isso precisamos do wildcar <? extends T>
       <?> == <? extends Object>
    */
    AbstractProducer<?> obj = abstractProducer;
    System.out.println(obj.get());
  }

  private static <T> void allowedSet(AbstractProducer<? extends T> data) {
    /*
       A linha abaixo nao é permitida
       AbstractProducer<Object> obj = data;
    */
    /*
       para corrigir isso usamos
    */
    AbstractProducer<? extends Object> obj1 = data;
    System.out.println(obj1.get());
    /*
       ou a sintaxe resumida
    */
    AbstractProducer<?> obj2 = data;
    System.out.println(obj2.get());
  }

  private static void checkedSetValue() {
    MutableAbstractProducer<StaticModels.TextField> mutableAbstractProducer =
        new MutableWrapperProducer<>(new StaticModels.TextField("#1", "textfield 1"));

    System.out.println(mutableAbstractProducer.get());

    mutableAbstractProducer.set(
        new StaticModels.FlexibleTextField("#2", "#2 FlexibleTextField", "Empty Title"));

    /*
       Inferred type 'O' for type parameter 'O' is not within its bound;
       should extend 'sample.generics.variance.StaticModels.TextField'
    */
    // mutableAbstractProducer.set(Integer.parseInt("1"));

    System.out.println(mutableAbstractProducer.get());
  }

  public static void main(String[] args) {
    // unsafeSet(new Wrapper(1));
    // checkedSetValue();
    // notAllowedSet(new WrapperAbstractProducer<>("teste"));

    allowedSet(new WrapperAbstractProducer<>(new StaticModels.FlexibleTextField("", "", "Empty")));
  }
}
