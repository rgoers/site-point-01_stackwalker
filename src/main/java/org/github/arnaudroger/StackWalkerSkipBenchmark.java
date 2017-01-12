package org.github.arnaudroger;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/*
Benchmark                                       (skip)  Mode  Cnt   Score   Error  Units
StackWalkerSkipBenchmark.stackWalkerStreamSkip       1  avgt   20  14.797 ± 1.391  us/op
StackWalkerSkipBenchmark.stackWalkerStreamSkip       2  avgt   20  14.701 ± 1.604  us/op
StackWalkerSkipBenchmark.stackWalkerStreamSkip       4  avgt   20  14.650 ± 1.809  us/op
StackWalkerSkipBenchmark.stackWalkerStreamSkip       6  avgt   20  14.748 ± 2.129  us/op
StackWalkerSkipBenchmark.stackWalkerStreamSkip       8  avgt   20  14.709 ± 1.476  us/op
StackWalkerSkipBenchmark.stackWalkerStreamSkip      10  avgt   20  14.566 ± 1.697  us/op
StackWalkerSkipBenchmark.stackWalkerStreamSkip      12  avgt   20  14.423 ± 1.346  us/op
StackWalkerSkipBenchmark.stackWalkerStreamSkip      14  avgt   20  14.826 ± 1.622  us/op
StackWalkerSkipBenchmark.stackWalkerStreamSkip      16  avgt   20  14.774 ± 2.160  us/op

 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class StackWalkerSkipBenchmark {


    StackWalkerTest stackWalkerTest = new StackWalkerTest();

    @Param(value = {"1", "2", "4", "6", "8", "10", "12", "14", "16"})
    int skip;

    @Benchmark
    public void stackWalkerStreamSkip(Blackhole b) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        stackWalkerTest.stack1( () -> {
                    StackWalker.getInstance().walk( s -> {s.skip(skip).forEach(b::consume); return null;});
                }
        );
    }
}
