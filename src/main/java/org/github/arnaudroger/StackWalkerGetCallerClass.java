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
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


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
    @Benchmark
    public Object exceptionStackTrace() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();

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

    public static void main(String[] args) {
        System.out.println(Arrays.toString(securityManager.p_getClassContext()));
    }

}
