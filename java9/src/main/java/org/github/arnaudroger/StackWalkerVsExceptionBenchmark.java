package org.github.arnaudroger;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;


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
    private final static StackWalker walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
    private final static StackWalker walker2 = StackWalker.getInstance();
    private final static ClassPredicate finder = new ClassPredicate();

    @Benchmark
    public void exceptionStackTrace(Blackhole b) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        STACK_WALKER_TEST.stack1(() -> {
            for(StackTraceElement e : new Throwable().getStackTrace())
                b.consume(e);
        });
    }

    @Benchmark
    public void stackwalkerStackTrace(Blackhole b) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        final Mutable mutable = new Mutable();
        STACK_WALKER_TEST.stack1(() -> mutable.setStackFrame(walker2.walk(s -> s.filter(finder).findFirst())));
        b.consume(mutable.getStackFrame().get().toStackTraceElement());
    }

    static final class ClassPredicate implements Predicate<StackWalker.StackFrame> {

        private boolean next = true;

        @Override
        public boolean test(StackWalker.StackFrame f) {
            if ("org.github.arnaudroger.StackWalkerTest".equals(f.getClassName())) {
                next = true;
                return false;
            } else if (next) {
                next = false;
                return true;
            }
            return true;
        }
    }

    @Benchmark
    public void stackWalkerForEach(Blackhole b) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        STACK_WALKER_TEST.stack1(() -> walker2.forEach(b::consume));
    }

    @Benchmark
    public void stackWalkerForEachRetainClass(Blackhole b) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        STACK_WALKER_TEST.stack1(() -> walker.forEach(b::consume));
    }

    @Benchmark
    public void stackWalkerForEachToStackTraceElement(Blackhole b) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        STACK_WALKER_TEST.stack1(() -> walker2.forEach(f -> b.consume(f.toStackTraceElement())));
    }

    private static class Mutable {
        private Optional<StackWalker.StackFrame> stackFrame = Optional.empty();

        public Optional<StackWalker.StackFrame> getStackFrame() {
            return stackFrame;
        }

        public void setStackFrame(Optional<StackWalker.StackFrame> stackFrame) {
            this.stackFrame = stackFrame;
        }
    }
}
