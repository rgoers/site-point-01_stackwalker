package org.github.arnaudroger;

import jdk.internal.reflect.CallerSensitive;
import jdk.internal.reflect.Reflection;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.lang.reflect.InvocationTargetException;
import java.lang.StackWalker.StackFrame;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Stream;


@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)

/*
Benchmark                                      Mode  Cnt  Score   Error  Units
StackWalkerGetCallerClass.exceptionStackTrace  avgt   20  1.387 ± 0.036  us/op
StackWalkerGetCallerClass.securityManager      avgt   20  1.840 ± 0.033  us/op

 */
public class StackWalkerGetCallerClass {
    static MySecurityManager securityManager = new MySecurityManager();
    private final static StackWalkerTest STACK_WALKER_TEST = new StackWalkerTest();
    private final static StackWalker walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
    private final static ClassPredicate finder = new ClassPredicate();

    @Benchmark
    public Object stackwalkerGetImmediate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();

    }

    @Benchmark
    public Class<?> stackwalkerSearch() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        final Mutable mutable = new Mutable(false);
        STACK_WALKER_TEST.stack1(() -> mutable.setStackFrame(walker.walk(s -> s.filter(finder).findFirst())));
        return mutable.getStackFrame().get().getDeclaringClass();
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
    public Object securityManager() {
        return securityManager.p_getClassContext()[1];
    }

    public static class MySecurityManager extends SecurityManager {
        public Class<?>[] p_getClassContext() {
            return getClassContext();
        }
    }

    private static class Mutable {
        private boolean value;
        private Optional<StackFrame> stackFrame = Optional.empty();

        public Mutable(boolean value) {
            this.value = value;
        }

        public boolean isTrue() {
            return value;
        }

        public void setValue(boolean value) {
            this.value = value;
        }

        public Optional<StackFrame> getStackFrame() {
            return stackFrame;
        }

        public void setStackFrame(Optional<StackFrame> stackFrame) {
            this.stackFrame = stackFrame;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(Arrays.toString(securityManager.p_getClassContext()));
        StackWalkerGetCallerClass clazz = new StackWalkerGetCallerClass();
        Class<?> caller = clazz.stackwalkerSearch();
        System.out.println(caller.toString());
    }

}
