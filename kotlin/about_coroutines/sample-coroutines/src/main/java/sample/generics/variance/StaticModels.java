package sample.generics.variance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class StaticModels {

  static class BaseTextField {

    protected final String id, hint;

    BaseTextField(String id, String hint) {
      this.id = id;
      this.hint = hint;
    }

    @Override
    public String toString() {
      return String.format("BaseTextField[%s, %s]", id, hint);
    }
  }

  static class TextField extends BaseTextField {

    TextField(String id, String hint) {
      super(id, hint);
    }

    @Override
    public String toString() {
      return String.format("TextField[%s, %s]", id, hint);
    }
  }

  static class FlexibleTextField extends TextField {

    private final String title;

    FlexibleTextField(String id, String hint, String title) {
      super(id, hint);
      this.title = title;
    }

    @Override
    public String toString() {
      return String.format("FlexibleTextField[%s, %s, %s]", id, hint, title);
    }
  }

  static class WrapperCollection<T> {
    private final List<T> values = new ArrayList<>();

    /*
      O codigo abaixo nao compila
       Response<T> {
           T data;
       }

       Response<String> responseA = new Response();
       Response<Object> responseB = str

       Caso fosse possivel fazê-lo, seria incerto o valor "data" de responseB que hora
       poderia ser object ora qualquer outra coisa. A leitura seria insegura.
       Para resolver esse problema o Java tem o wildcard "? extends T"


       Esse metodo aceita uma colecao T e de subtipos de T.
       o WildCard <? extends T> permite criar TIPOS PARAMETRIZADOS que aceitem por exemplo
       um Tipo T ou descendente de T


       Response<String> responseA
       Response<Object> responseB
       responseA nao pode receber responseB porem podemos criar um metodo receba
       Response cujo valor parametrizado seja filho de Object e essa valor pode
       ser repassado para Object

       void set(Response<? extends T> responseA) {
           responseB.data = responseA.data
       }

       O wildcard extend-bound/upper-bound transforma o tipo covariante

    */

    void add(Collection<? extends T> collection) {
      values.addAll(collection);
    }

    void addJustSame(Collection<T> collection) {
      values.addAll(collection);
    }

    void add(T data) {
      values.add(data);
    }

    T getLast() {
      return values.size() > 0 ? values.get(values.size() - 1) : null;
    }

    public List<T> getValues() {
      return values;
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      for (T value : values) {
        sb.append(String.format("%s\n", value));
      }
      return sb.toString();
    }
  }

  /**
   * https://kotlinlang.org/docs/generics.html#declaration-site-variance Joshua Bloch gives the name
   * Producers to objects you only read from and Consumers to those you only write to. He
   * recommends:
   *
   * <p>WriteOnly
   */
  static class Consumer<T> {

    /*
       Lower Bound/Limitador inferior - só aceita tipos que sao "pais" de T
    */
    private final List<? super T> writeOnlyValues = new ArrayList<>();

    /*
       Upper Bound / Limitador superior - aceita T e filhor de T
    */
    void copyCollection(Collection<? extends T> collection) {
      writeOnlyValues.addAll(collection);
    }

    void copyAll(Collection<T> collection) {
      writeOnlyValues.addAll(collection);
    }

    void add(T data) {
      writeOnlyValues.add(data);
    }

    /*
       Não é possivel recuperar um elemento T somente inserir
    */
    T getValue() {
      // return values.size() > 0 ? values.get(values.size() - 1) : null;
      return null;
    }

    /*
       Só é possível recuperar um Object
    */
    Object getLastValue() {
      return writeOnlyValues.size() > 0 ? writeOnlyValues.get(writeOnlyValues.size() - 1) : null;
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      for (Object value : writeOnlyValues) {
        sb.append(String.format("%s\n", value));
      }
      return sb.toString();
    }
  }

  /*
     ReadOnly
  */
  static class Producer<T> {

    /*
       Limitador superior, só aceita T ou filhos de T
       Somente Leitura
    */
    private final List<? extends T> readOnlyValues =
        Arrays.asList(
            (T)
                new FlexibleTextField(
                    "#1", "#1 flexible text field", "primeiro flexible text field"),
            (T) new TextField("#1", "#1 text field"));

    /*
       nao é possivel adicionar/
    */
    void addData(Collection<? super T> collection) {
      // Do Nothing
      // values.addAll(collection);
    }

    /*
       nao é possivel adicionar
    */
    void addCollections(Collection<? extends T> collection) {
      // values.addAll(collection);
    }

    /*
       nao é possivel adicionar
    */
    void add(T data) {
      // Nothing
      // values.add(data);
    }

    // somente recuperar
    T getLast() {
      return readOnlyValues.size() > 0 ? readOnlyValues.get(readOnlyValues.size() - 1) : null;
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      for (T value : readOnlyValues) {
        sb.append(String.format("%s\n", value));
      }
      return sb.toString();
    }
  }
}
