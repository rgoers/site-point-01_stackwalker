package org.github.arnaudroger;

public class StackWalkerOptions {

    public static void method1(Runnable r) throws Exception {
        StackWalkerOptions.class.getMethod("method2", Runnable.class).invoke(null, r);
    }

    public static void method2(Runnable r) {
        r.run();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("---- Default StackWalker----");
        method1(() -> StackWalker.getInstance().forEach(System.out::println));

        System.out.println("---- SHOW_REFLECT_FRAMES StackWalker----");
        method1(() -> StackWalker.getInstance(StackWalker.Option.SHOW_REFLECT_FRAMES).forEach(System.out::println));

        System.out.println("---- SHOW_HIDDEN_FRAMES StackWalker----");
        method1(() -> StackWalker.getInstance(StackWalker.Option.SHOW_HIDDEN_FRAMES).forEach(System.out::println));
    }

}
