package sample.generics.variance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class StaticModels {

  public static class BaseTextField {

    protected final String id, hint;

    public BaseTextField(String id, String hint) {
      this.id = id;
      this.hint = hint;
    }

    @Override
    public String toString() {
      return String.format("BaseTextField[id: %s, hint: %s]", id, hint);
    }
  }

  public static class TextField extends BaseTextField {

    public TextField(String id, String hint) {
      super(id, hint);
    }

    @Override
    public String toString() {
      return String.format("TextField[id: %s, hint: %s]", id, hint);
    }
  }

  public static class FlexibleTextField extends TextField {

    private final String title;

    public FlexibleTextField(String id, String hint, String title) {
      super(id, hint);
      this.title = title;
    }

    @Override
    public String toString() {
      return String.format("FlexibleTextField[id: %s, hint: %s, title: %s]", id, hint, title);
    }
  }

  public static class AutoCompleteTextField extends TextField {

    private final String label;

    public AutoCompleteTextField(String id, String label) {
      super(id, "");
      this.label = label;
    }

    @Override
    public String toString() {
      return String.format("AutoComplteteTextField[id: %s, label: %s]", id, label);
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
   * https://kotlinlang.org/docs/generics.html#declaration-site-variance
   *
   * <p>Joshua Bloch gives the name PRODUCERS to objects you only read from and CONSUMERS to those
   * you only write to. Herecommends:
   *
   * <p>For maximum flexibility, use wildcard types on input parameters that represent producers or
   * consumers", and proposes the following mnemonic
   *
   * <p>PECS - Producer-Extends, Consumer-Super
   *
   * <p>WriteOnly - ConsumerSuper
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
       Não é possivel recuperar (ler) um elemento T somente inserir (escrever)
    */
    T getValue() {
      // return values.size() > 0 ? values.get(values.size() - 1) : null;
      return null;
    }

    /*
       Só é possível recuperar a informação da colecao com um metodo que retorne
       Object por conta do wildcard <? super T> que limita (lower bound) que somente
       tipos que herdem de quem T herda possam ser inseridos na colecao.
       Assim o jeito mais simples de garantir que nao haja erros em execucao é retornar
       um Tipo que seja SuperTipo de qualquer classe no Java, o tipo Object, sendo ele é o limite
       superior máximo
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
     ReadOnly - ProducerExtends
  */
  public static class Producer<T> {

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
       nao é possivel adicionar(esrever)
    */
    void add(T data) {
      // Nothing
      // values.add(data);
    }

    /*
      Nem mesmo
    */

    void addValue(Object data) {
      // do nothing
      // readOnlyValues.add(data);
    }

    // somente recuperar
    T getLast() {
      return readOnlyValues.size() > 0 ? readOnlyValues.get(readOnlyValues.size() - 1) : null;
    }

    public List<? extends T> getReadOnlyValues() {
      return readOnlyValues;
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

  public static class TemplateProducer<T> {

    final ProviderReadOnlyCollection<T> providerReadOnlyCollection;

    final List<? extends T> readOnlyValues;

    public TemplateProducer(ProviderReadOnlyCollection<T> providerReadOnlyCollection) {
      this.providerReadOnlyCollection = providerReadOnlyCollection;
      readOnlyValues = providerReadOnlyCollection.getCollection();
    }

    public List<? extends T> getReadOnlyValues() {
      return readOnlyValues;
    }

    @Override
    public String toString() {
      if (readOnlyValues.isEmpty()) {
        return "Is Empty";
      }
      StringBuilder sb = new StringBuilder();
      for (T value : readOnlyValues) {
        sb.append(String.format("%s\n", value));
      }
      return sb.toString();
    }

    public interface ProviderReadOnlyCollection<T> {
      List<T> getCollection();
    }
  }
}
