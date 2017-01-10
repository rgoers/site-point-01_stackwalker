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
Benchmark                                                      Mode  Cnt   Score   Error  Units
StackWalkerBenchmark.testException                             avgt   20  32.796 ± 0.570  us/op
StackWalkerBenchmark.testStackwalkerDefaultFI                  avgt   20  16.032 ± 1.189  us/op
StackWalkerBenchmark.testStackwalkerDefaultFIRC                avgt   20  16.414 ± 1.700  us/op
StackWalkerBenchmark.testStackwalkerDefaultFIStream            avgt   20  15.893 ± 1.119  us/op
StackWalkerBenchmark.testStackwalkerDefaultFIStreamLimit2      avgt   20   3.886 ± 0.359  us/op
StackWalkerBenchmark.testStackwalkerDefaultFIStreamLimit2Est2  avgt   20   3.925 ± 0.386  us/op
StackWalkerBenchmark.testStackwalkerDefaultSTE                 avgt   20  40.043 ± 1.126  us/op

 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class StackWalkerBenchmarkLimit {


    StackWalkerTest stackWalkerTest = new StackWalkerTest();

    @Param(value = {"1", "2", "4", "6", "8", "10", "12", "14", "16"})
    int limit;

    @Benchmark
    public void testStackwalkerDefaultFIStreamLimit(Blackhole b) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        stackWalkerTest.stack1( () -> {
                    StackWalker.getInstance().walk( s -> {s.limit(limit).forEach(b::consume); return null;});
                }
        );
    }

    @Benchmark
    public void testStackwalkerDefaultFIStreamLimitEstimatedSize(Blackhole b) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        stackWalkerTest.stack1( () -> {
                    StackWalker.getInstance(Set.of(), limit + 2).walk(s -> {s.limit(limit).forEach(b::consume); return null;});
                }
        );
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        new StackWalkerTest().stack1( () -> {
                    StackWalker.getInstance().walk( s -> { System.out.println("s.count() = " + s.count());return null;});
                }
        );
    }

}
