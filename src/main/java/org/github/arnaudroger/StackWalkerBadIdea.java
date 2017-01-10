package org.github.arnaudroger;

import java.util.stream.Stream;

public class StackWalkerBadIdea {

    public static void method1(Runnable r) throws Exception {
        StackWalkerBadIdea.class.getMethod("method2", Runnable.class).invoke(null, r);
    }

    public static void method2(Runnable r) {
        r.run();
    }

    public static void main(String[] args) throws Exception {
        method1(() -> {
            Stream<StackWalker.StackFrame> stream = StackWalker.getInstance().walk(s -> s);
            stream.forEach(System.out::println);
        });

    }

}
