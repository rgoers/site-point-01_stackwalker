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
public class StackWalkerBenchmark {


    StackWalkerTest stackWalkerTest = new StackWalkerTest();
    @Benchmark
    public void testException(Blackhole b) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        stackWalkerTest.stack1(() -> {
            Exception e = new Exception();
            b.consume(e.getStackTrace());
        });
    }

    @Benchmark
    public void testStackwalkerDefaultFI(Blackhole b) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        stackWalkerTest.stack1( () -> {
            StackWalker.getInstance().forEach(b::consume);
        }
        );
    }

    @Benchmark
    public void testStackwalkerDefaultFIRetainClass(Blackhole b) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        stackWalkerTest.stack1( () -> {
                    StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).forEach(b::consume);
                }
        );
    }

    @Benchmark
    public void testStackwalkerDefaultSTE(Blackhole b) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        stackWalkerTest.stack1( () -> {
            StackWalker.getInstance().forEach(f -> b.consume(f.toStackTraceElement()));
        });
    }


    @Benchmark
    public void testStackwalkerDefaultFIStream(Blackhole b) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        stackWalkerTest.stack1( () -> {
                    StackWalker.getInstance().walk( s -> {s.forEach(b::consume); return null;});
                }
        );
    }
}
