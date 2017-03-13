package org.github.rgoers;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)


public class StackWalkerGetCallerClass {

    static final int JDK_7u25_OFFSET;
    // CHECKSTYLE:OFF

    private static final boolean SUN_REFLECTION_SUPPORTED;
    private static final Method GET_CALLER_CLASS;
    private static final MySecurityManager SECURITY_MANAGER;
    private static final String fqcn = "org.github.rgoers.StackWalkerTest";

    private final static StackWalkerTest STACK_WALKER_TEST = new StackWalkerTest();

    static {
        Method getCallerClass;
        int java7u25CompensationOffset = 0;
        try {
            final Class<?> sunReflectionClass = StackWalkerGetCallerClass.class.forName("sun.reflect.Reflection");
            getCallerClass = sunReflectionClass.getDeclaredMethod("getCallerClass", int.class);
            Object o = getCallerClass.invoke(null, 0);
            final Object test1 = getCallerClass.invoke(null, 0);
            if (o == null || o != sunReflectionClass) {
                getCallerClass = null;
                java7u25CompensationOffset = -1;
            } else {
                o = getCallerClass.invoke(null, 1);
                if (o == sunReflectionClass) {
                    java7u25CompensationOffset = 1;
                }
            }
        } catch (final Exception | LinkageError e) {
            getCallerClass = null;
            java7u25CompensationOffset = -1;
        }

        SUN_REFLECTION_SUPPORTED = getCallerClass != null;
        GET_CALLER_CLASS = getCallerClass;
        JDK_7u25_OFFSET = java7u25CompensationOffset;

        MySecurityManager psm;
        try {
            final SecurityManager sm = System.getSecurityManager();
            if (sm != null) {
                sm.checkPermission(new RuntimePermission("createSecurityManager"));
            }
            psm = new MySecurityManager();
        } catch (final SecurityException ignored) {
            psm = null;
        }
        SECURITY_MANAGER = psm;
    }

    @Benchmark
    public Object exceptionGetImmediate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return sun.reflect.Reflection.getCallerClass(1 + JDK_7u25_OFFSET);

    }

    @Benchmark
    public void reflectionSearch(Blackhole bh) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        STACK_WALKER_TEST.stack1(() -> {
            boolean next = false;
            Class<?> clazz;
            for (int i = 2; null != (clazz = sun.reflect.Reflection.getCallerClass(i)); i++) {
                if (fqcn.equals(clazz.getName())) {
                    next = true;
                    continue;
                }
                if (next) {
                    bh.consume(clazz);
                    break;
                }
            }
        });
    }


    @Benchmark
    public Object securityManager() {
        return SECURITY_MANAGER.p_getClassContext()[1];
    }

    public static class MySecurityManager extends SecurityManager {
        public Class<?>[] p_getClassContext() {
            return super.getClassContext();
        }
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(SECURITY_MANAGER.p_getClassContext()));
    }

}
