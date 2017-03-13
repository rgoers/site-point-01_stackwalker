package org.github.rgoers;

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
public class ExceptionBenchmark {


    private final static StackWalkerTest STACK_WALKER_TEST = new StackWalkerTest();

    @Benchmark
    public void exceptionStackTrace(Blackhole b) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        STACK_WALKER_TEST.stack1(() -> {
            for(StackTraceElement e : new Throwable().getStackTrace())
                b.consume(e);
        });
    }
}
