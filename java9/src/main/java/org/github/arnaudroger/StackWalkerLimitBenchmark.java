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
import java.util.function.Consumer;


/*
Benchmark                                         (limit)  Mode  Cnt   Score   Error  Units
StackWalkerLimitBenchmark.stackWalkerStreamLimit        1  avgt   20   3.233 ± 0.844  us/op
StackWalkerLimitBenchmark.stackWalkerStreamLimit        2  avgt   20   3.062 ± 0.490  us/op
StackWalkerLimitBenchmark.stackWalkerStreamLimit        4  avgt   20   3.341 ± 0.593  us/op
StackWalkerLimitBenchmark.stackWalkerStreamLimit        6  avgt   20   3.331 ± 0.453  us/op
StackWalkerLimitBenchmark.stackWalkerStreamLimit        8  avgt   20   9.663 ± 3.298  us/op
StackWalkerLimitBenchmark.stackWalkerStreamLimit       10  avgt   20   8.724 ± 1.399  us/op
StackWalkerLimitBenchmark.stackWalkerStreamLimit       12  avgt   20   8.946 ± 0.763  us/op
StackWalkerLimitBenchmark.stackWalkerStreamLimit       14  avgt   20   8.921 ± 0.808  us/op
StackWalkerLimitBenchmark.stackWalkerStreamLimit       16  avgt   20  14.480 ± 1.153  us/op
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class StackWalkerLimitBenchmark {

    StackWalkerTest stackWalkerTest = new StackWalkerTest();

    @Param(value = {"1", "2", "4", "6", "8", "10", "12", "14", "16"})
    int limit;

    @Benchmark
    public void stackWalkerStreamLimit(Blackhole b) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        stackWalkerTest.stack1( () -> {
                    StackWalker.getInstance().walk(s -> {s.limit(limit).forEach(b::consume); return null;});
                }
        );
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        StackWalkerTest stackWalkerTest = new StackWalkerTest();
        stackWalkerTest.stack1( () -> {
                    StackWalker.getInstance().walk( s -> {s.forEach(new Consumer<StackWalker.StackFrame>() {
                        int i = 0;
                        @Override
                        public void accept(StackWalker.StackFrame stackFrame) {
                            i++;
                            System.out.println(i +  " " + stackFrame);
                        }
                    }); return null;});
                }
        );
    }

}
