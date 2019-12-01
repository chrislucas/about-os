package sample;

import java.util.concurrent.atomic.AtomicLong;

public class StudyAtomicClass {

    private static final AtomicLong atomicLong = new AtomicLong(0);

    private static void incrementAtomicLong() {
        atomicLong.incrementAndGet();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1001 ; i++) {
            new Thread(StudyAtomicClass::incrementAtomicLong) .start();
        }

        System.out.println(atomicLong.get());
    }
}
