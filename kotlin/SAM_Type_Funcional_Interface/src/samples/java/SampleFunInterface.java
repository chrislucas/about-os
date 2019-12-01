package samples.java;

import org.w3c.dom.ls.LSOutput;

public class SampleFunInterface {

    public interface Callable<T> {
        T call();
    }

    public interface SecurityOperation {
        void execute();
    }

    private static void testLambdaExpression1() {
        Callable<Integer>  callable = () -> 0xff & 0x0f;
        System.out.println(callable.call());
    }

    public static class CreditCardOperation {

        private final SecurityOperation securityOperation;

        public CreditCardOperation(SecurityOperation securityOperation) {
            this.securityOperation = securityOperation;
        }

        public void executePayment() {
            this.securityOperation.execute();
        }
    }

    private static void testLambdaExpression2() {
        new CreditCardOperation(() -> System.out.println(0xff)).executePayment();

        SecurityOperation s = () -> System.out.println(0x0f);

        new CreditCardOperation(s).executePayment();
    }

    private static void testLambdaExpression3() {
        for (int i = 0; i < 10; i++) {
            System.out.printf("Contagem %d%n", i);
            Runnable r = () -> {
                for (int j = 1; j < 11; j++) {
                    System.out.printf("%s"
                            , j == 1 ? String.format("%d", j) : String.format(" %d", j));
                }
                System.out.println("");
            };
            new Thread(r).start();
        }
    }

    public static void main(String[] args) {
        testLambdaExpression3();
    }
}
