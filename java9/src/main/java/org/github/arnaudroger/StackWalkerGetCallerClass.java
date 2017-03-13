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

    @Benchmark
    public Object stackwalkerGetImmediate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();

    }

    @Benchmark
    public Optional<StackFrame> stackwalkerSearch() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        StackWalker walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        ClassComparator comparator = new ClassComparator();
        return walker.walk(s -> s.reduce(comparator));
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

    public static class ClassComparator implements BinaryOperator<StackFrame> {
        private boolean next = false;

        public StackFrame apply(StackFrame found, StackFrame element) {
            if (found != null) {
                return found;
            }
            if ("org.github.arnaudroger.StackWalkerTest".equals(element.getClassName())) {
                next = true;
                return found;
            } else if (next) {
                return element;
            }
            return found;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(Arrays.toString(securityManager.p_getClassContext()));
        StackWalkerGetCallerClass clazz = new StackWalkerGetCallerClass();
        Optional<StackFrame> optional = clazz.stackwalkerSearch();
        System.out.println(optional.orElse(null));
    }

}
