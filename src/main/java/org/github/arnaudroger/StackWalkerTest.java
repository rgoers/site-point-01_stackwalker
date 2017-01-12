package org.github.arnaudroger;

import java.lang.StackWalker.StackFrame;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class StackWalkerTest {

    static final Method stack2;

    static {
        try {
            stack2 = StackWalkerTest.class.getMethod("stack2", Runnable.class);
        } catch (NoSuchMethodException e) {
            throw new Error(e);
        }
    }


    public void stack1(Runnable task) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        stack2.invoke(this, task);
    }

    public void stack2(Runnable task) {
        stack3(task);
    }

    public void stack3(Runnable task) {
        stack4(task);
    }

    public void stack4(Runnable task) {
        stack5(task);
    }

    public void stack5(Runnable task) {
        stack6(task);
    }

    public void stack6(Runnable task) {
        stack7(task);
    }

    public void stack7(Runnable task) {
        stack8(task);
    }

    public void stack8(Runnable task) {
        stack9(task);
    }

    public void stack9(Runnable task) {
        stack10(task);
    }

    public void stack10(Runnable task) {
        stack11(task);
    }

    public void stack11(Runnable task) {
        task.run();
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        StackWalkerTest stackWalkerTest = new StackWalkerTest();


        stackWalkerTest.stack1(() -> {
                List<String> m = StackWalker
                                .getInstance()
                                .walk(s -> s.filter(f -> f.getMethodName().startsWith("m")).map(StackFrame::getMethodName).collect(Collectors.toList()));
                System.out.println("m = " + m);
            }

        );

        stackWalkerTest.stack1(() -> {
                    Optional<StackFrame> m = StackWalker
                            .getInstance()
                            .walk(s -> s.findFirst());
                    System.out.println("m = " + m);
                }

        );

    }
}
