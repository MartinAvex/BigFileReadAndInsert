import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: some desc
 * @author: ex_wuzr11
 * @date: 2024/11/1 9:03
 */
public class CallableTest {

    @Test
    public void returnValue() throws ExecutionException, InterruptedException {
        int maximumPoolSize = Runtime.getRuntime().availableProcessors() * 2;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(maximumPoolSize, maximumPoolSize,
                3, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10), new ThreadPoolExecutor.CallerRunsPolicy());
        List<Future<Integer>> futureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Future<Integer> submit = threadPoolExecutor.submit(() -> {

                return 10;
            });
            futureList.add(submit);
        }
        Integer sum = 0;
        for (Future<Integer> future : futureList) {
            sum += future.get();
        }
        System.out.println(sum);
    }

}
