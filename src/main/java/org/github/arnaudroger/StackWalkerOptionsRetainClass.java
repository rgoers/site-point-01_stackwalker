package org.github.arnaudroger;

public class StackWalkerOptionsRetainClass {

    public static void method1(Runnable r) {
        r.run();
    }

    public static void main(String[] args) throws Exception {
        try {
            System.out.println("---- Default StackWalker----");
            method1(() -> StackWalker.getInstance().forEach(f -> System.out.println("getDeclaringClass = " + f.getDeclaringClass() + " - " + f)));
        } catch(UnsupportedOperationException e) {
            e.printStackTrace();
            System.out.println("e = " + e);
        }


        System.out.println("---- Default StackWalker----");
        method1(() -> StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).forEach(f -> System.out.println("getDeclaringClass = " + f.getDeclaringClass() + " - " + f)));

    }

}
