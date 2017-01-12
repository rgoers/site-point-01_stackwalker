package org.github.arnaudroger;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;


/*
Benchmark                                                          (limit)  Mode  Cnt  Score   Error  Units
StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit        1  avgt   20  3.371 ± 0.549  us/op
StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit        2  avgt   20  3.112 ± 0.361  us/op
StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit        4  avgt   20  3.101 ± 0.347  us/op
StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit        6  avgt   20  3.245 ± 0.410  us/op
StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit        8  avgt   20  3.897 ± 0.403  us/op
StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit       10  avgt   20  4.897 ± 0.527  us/op
StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit       12  avgt   20  5.627 ± 0.622  us/op
StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit       14  avgt   20  6.458 ± 0.785  us/op
StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit       16  avgt   20  6.986 ± 0.732  us/op

 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class StackWalkerLimitWithEstimatedSizeBenchmark {

    // the frame buffer starting position is 2
    public static final int BATCH_OFFSET = 2;

    StackWalkerTest stackWalkerTest = new StackWalkerTest();

    @Param(value = {"1", "2", "4", "6", "8", "10", "12", "14", "16"})
    int limit;


    @Benchmark
    public void stackWalkerStreamLimit(Blackhole b) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        stackWalkerTest.stack1( () -> {
                    StackWalker.getInstance(Set.of(StackWalker.Option.SHOW_HIDDEN_FRAMES), limit + BATCH_OFFSET).walk(s -> {s.limit(limit).forEach(b::consume); return null;});
                }
        );
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        StackWalkerTest stackWalkerTest = new StackWalkerTest();
        int limit = 12;
        stackWalkerTest.stack1(() -> {
            try {
                stackWalkerTest.stack1(
                        () ->
                                StackWalker
                                        .getInstance(Set.of(StackWalker.Option.SHOW_HIDDEN_FRAMES), limit + 2)
                                        .walk(
                                                s -> {
                                                    s
                                                            .limit(limit)
                                                            .forEach(System.out::println);
                                                    return null;
                                                }
                                        )
                );
            } catch (Exception e) {
                throw new Error(e);
            }
        });
    }

}
