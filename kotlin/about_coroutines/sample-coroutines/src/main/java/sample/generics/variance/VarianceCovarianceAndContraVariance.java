package sample.generics.variance;

/*
   https://kotlinlang.org/docs/generics.html#variance
*/

import java.util.Comparator;
import sample.generics.variance.StaticModels.BaseTextField;
import sample.generics.variance.StaticModels.FlexibleTextField;
import sample.generics.variance.StaticModels.TextField;

public class VarianceCovarianceAndContraVariance {
  static class Response<T> {

    private T data;

    Response(T data) {
      this.data = data;
    }

    private Response() {}

    static <T> Response<T> createEmpty() {
      return new Response<T>();
    }

    /*
       O wildcard extend-bound/upper-bound transforma o tipo covariante
    */
    void set(Response<? extends T> response) {
      this.data = response.data;
    }

    void setExactly(Response<T> response) {
      this.data = response.data;
    }

    public T get() {
      return data;
    }

    @Override
    public String toString() {
      return data.toString();
    }
  }

  private static void checkResponse() {
    Response<FlexibleTextField> flexibleTextFieldResponse =
        new Response<>(
            new FlexibleTextField("#1", "#1 flexible text field", "flexible text field 1"));

    Response<TextField> textFieldResponse = new Response<>();
    textFieldResponse.set(flexibleTextFieldResponse);

    System.out.println(textFieldResponse);
  }

  /*
     https://kotlinlang.org/docs/generics.html#variance
     Uma forma de criar subtipos de supertipos parametrizaveis usando o wildcard <? extends T>
     Covariancia - <? extends T>
         - upper bound só aceitam classes que sejam "filhas" de T ou o proprio T
  */
  private static void checkCreateSubtypes() {

    Response<? extends TextField> response;

    Response<FlexibleTextField> flexibleTextFieldResponse =
        new Response<>(
            new FlexibleTextField("#1", "#1 flexible text field", "flexible text field 1"));

    response = flexibleTextFieldResponse;

    System.out.println(response);

    Response<TextField> anotherResponse =
        new Response<>(
            new FlexibleTextField("#2", "#2 flexible text field", "flexible text field 2"));

    // Nao eh possivel
    // anotherResponse = response;
    // Tambem nao eh possivel
    // anotherResponse = flexibleTextFieldResponse;

    System.out.println(anotherResponse);
  }

  /*
     https://kotlinlang.org/docs/generics.html#variance
     Contravariancia <? super T>
         - lower bound, so aceita classes que sejam "pais" de T ou o Proprio T

     Response<? super FlexibleTextField> um SUPER TIPO de Response<TextField>

    Traducao livre da explicadao de covariancia e contravariancia
    "O ponto central para entender porque <? extends T> permite criar subtipos é:

        Se temos uma estrutura de dados que SOMENTE PODEMOS LER, entao ESCREVER um TIPO A e
        LERr um SUPER TIPO de A não é um problema

             Collection<String> a
             Object b = a.get(0) //  por exemplo não é um problema

        Se temos uma estrutura que SOMENTE PODEMOS ESCREVER, então tudo bem se a estrutura aceita/é
        SUPER TIPO A e ESCREVEMOS um SUBTIPO de A

             Collection<Object> o;
             o.add(String a)
    "

  */
  private static void checkCreateSuperType() {
    Response<? super FlexibleTextField> justSuperTypeFlexibleTextField;

    Response<TextField> textFieldResponse = new Response<>(new TextField("#1", "#1 TextField"));
    justSuperTypeFlexibleTextField = textFieldResponse;
    System.out.println(justSuperTypeFlexibleTextField);

    Response<BaseTextField> baseTextFieldResponse =
        new Response<>(new BaseTextField("#1", "#1 BaseTextField"));
    justSuperTypeFlexibleTextField = baseTextFieldResponse;
    System.out.println(justSuperTypeFlexibleTextField);
  }

  static class SimpleIntComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer p, Integer q) {
      return p.compareTo(q);
    }
  }

  private static void checkUpperBoundLimitation(int p, int q) {
    Response<? extends Comparator<Integer>> comparatorResponse;

    comparatorResponse = new Response<>(new SimpleIntComparator());

    System.out.println(comparatorResponse.get().compare(p, q));
  }

  public static void main(String[] args) {
    // checkCreateSubtypes();
    checkCreateSuperType();
    // check();
  }
}
