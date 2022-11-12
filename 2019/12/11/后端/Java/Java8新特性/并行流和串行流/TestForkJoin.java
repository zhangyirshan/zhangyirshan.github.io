import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class TestForkJoin {
    @Test
    public void test1() {
        long start = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinCalculate(0, 10000000000L);
        Long sum = pool.invoke(task);
        System.out.println(sum);
        long end = System.currentTimeMillis();
        System.out.println("耗费时间：" + (end - start) + "ms");
    }

    @Test
    public void test2() {
        Instant start = Instant.now();
        long sum = 0;
        for (long i = 0; i <= 100000000000L; i++) {
            sum += i;
        }
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗费时间：" + Duration.between(start, end).toMillis() + "ms");
    }

    /**
     * java8并行流
     */
    @Test
    public void test3() {
        Instant start = Instant.now();
        LongStream.rangeClosed(0, 10000000000L).parallel().reduce(0, Long::sum);
        Instant end = Instant.now();
        System.out.println("耗费时间：" + Duration.between(start, end).toMillis() + "ms");
    }
}
