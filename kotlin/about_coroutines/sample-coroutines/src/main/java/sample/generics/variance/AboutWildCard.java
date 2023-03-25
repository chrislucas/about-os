package sample.generics.variance;

import static sample.generics.variance.StaticModels.*;

import java.util.Arrays;
import sample.generics.variance.StaticModels.TextField;

/*
   https://kotlinlang.org/docs/generics.html#variance
*/
public class AboutWildCard {

  private static void checkConsumerTextField() {
    WrapperCollection<TextField> consumer = new WrapperCollection<>();
    consumer.add(
        new FlexibleTextField("#1", "#1 flexible text field", "primeiro flexible text field"));
    consumer.add(new TextField("#1", "#1 text field"));
    System.out.println(consumer);
    consumer.addJustSame(
        Arrays.asList(
            new FlexibleTextField("#2", "#2 flexible text field", "segundo flexible text field"),
            new FlexibleTextField("#3", "#3 flexible text field", "terceiro flexible text field"),
            new TextField("#2", "#2 text field")));

    System.out.println(consumer);
    consumer.add(
        Arrays.asList(new TextField("#3", "#3 text field"), new TextField("#4", "#4 text field")));

    System.out.println(consumer);
  }

  private static void checkSuperConsumer() {
    Consumer<TextField> consumerTextField = new Consumer<>();
    consumerTextField.add(
        new FlexibleTextField("#1", "#1 flexible text field", "primeiro flexible text field"));
    consumerTextField.add(new TextField("#1", "#1 text field"));

    System.out.println(consumerTextField);
    consumerTextField.copyAll(
        Arrays.asList(
            new FlexibleTextField("#2", "#2 flexible text field", "segundo flexible text field"),
            new FlexibleTextField("#3", "#3 flexible text field", "terceiro flexible text field"),
            new TextField("#2", "#2 text field")));
    System.out.println(consumerTextField);
    consumerTextField.copyCollection(
        Arrays.asList(
            new FlexibleTextField("#4", "#4 flexible text field", "quarto flexible text field"),
            new TextField("#3", "#3 text field")));
    System.out.println(consumerTextField);
  }

  private static void checkConsumerFlexibleTextField() {
    WrapperCollection<FlexibleTextField> consumerFlexibleTextField = new WrapperCollection<>();
  }

  private static void checkProducer() {
    Producer<?> producer = new Producer<>();
    System.out.println(producer);
    Producer<TextField> anotherProducer = new Producer<>();
    System.out.println(anotherProducer);
  }

  private static void checkVariance() {
    WrapperCollection<String> str = new WrapperCollection<>();
    // Isso nao é possivel
    // Consumer<Object> obj = str;
    // porem isso é
    WrapperCollection<Object> obj = new WrapperCollection<>();
    obj.add("Text 1");
    obj.addJustSame(Arrays.asList("Text 2", "Text 3"));

    System.out.println(obj);

    WrapperCollection<FlexibleTextField> flexibleTextFieldConsumer = new WrapperCollection<>();
    flexibleTextFieldConsumer.add(new FlexibleTextField("1#", "first", "the first"));
    WrapperCollection<TextField> textFieldConsumer = new WrapperCollection<>();
    textFieldConsumer.add(flexibleTextFieldConsumer.getValues());
    /*
       Aqui vemos que a leitura da collecao de TextField pode retornar tanto
       um TextField quanto um FlexibleTextField (a subclasse), o que pode levar
       a ter que fazer casting ou testar o tipo
    */
    TextField textField = textFieldConsumer.getLast();
    System.out.println(textField);
  }

  public static void main(String[] args) {
    // checkConsumerTextField();
    // checkSuperConsumer();
    // checkProducer();
    // checkVariance();
    checkProducer();
  }
}
