package org.github.arnaudroger;

import java.util.Arrays;

public class StackWalkerOptions {

    public static void delegateViaReflection(Runnable task) throws Exception {
        StackWalkerOptions.class.getMethod("runTask", Runnable.class).invoke(null, task);
    }

    public static void runTask(Runnable r) {
        r.run();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("---- Default StackWalker----");
        delegateViaReflection(() -> StackWalker.getInstance().forEach(System.out::println));

        System.out.println("---- SHOW_REFLECT_FRAMES StackWalker----");
        delegateViaReflection(() -> StackWalker.getInstance(StackWalker.Option.SHOW_REFLECT_FRAMES).forEach(System.out::println));

        System.out.println("---- SHOW_HIDDEN_FRAMES StackWalker----");
        delegateViaReflection(() -> StackWalker.getInstance(StackWalker.Option.SHOW_HIDDEN_FRAMES).forEach(System.out::println));

        System.out.println("---- Exception ---");
        delegateViaReflection(() -> Arrays.stream(new Throwable().getStackTrace()).forEach(System.out::println));
    }

}
