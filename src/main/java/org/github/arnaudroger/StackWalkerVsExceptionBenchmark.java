package org.github.arnaudroger;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;


@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class StackWalkerVsExceptionBenchmark {

    /*
Benchmark                                                              Mode  Cnt   Score   Error  Units
StackWalkerVsExceptionBenchmark.exceptionStackTrace                    avgt   20  31.929 ± 0.407  us/op
StackWalkerVsExceptionBenchmark.stackWalkerForEach                      avgt   20  15.350 ± 3.808  us/op
StackWalkerVsExceptionBenchmark.stackWalkerForEachRetainClass          avgt   20  14.764 ± 1.366  us/op
StackWalkerVsExceptionBenchmark.stackWalkerForEachToStackTraceElement  avgt   20  39.675 ± 1.219  us/op
     */

    private final static StackWalkerTest STACK_WALKER_TEST = new StackWalkerTest();

    @Benchmark
    public void exceptionStackTrace(Blackhole b) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        STACK_WALKER_TEST.stack1(() -> {
            for(StackTraceElement e : new Exception().getStackTrace())
                b.consume(e);
        });
    }

    @Benchmark
    public void stackWalkerForEach(Blackhole b) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        STACK_WALKER_TEST.stack1(() -> StackWalker.getInstance().forEach(b::consume));
    }

    @Benchmark
    public void stackWalkerForEachRetainClass(Blackhole b) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        STACK_WALKER_TEST.stack1(() -> StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).forEach(b::consume));
    }

    @Benchmark
    public void stackWalkerForEachToStackTraceElement(Blackhole b) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        STACK_WALKER_TEST.stack1(() -> StackWalker.getInstance().forEach(f -> b.consume(f.toStackTraceElement())));
    }
}
