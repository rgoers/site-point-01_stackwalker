package org.github.arnaudroger;

import java.lang.StackWalker.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class StackWalkerSamples {


    public static void main(String[] args) throws Exception {
        {
            StackWalker stackWalker1 =
                    StackWalker.getInstance();
            StackWalker stackWalker2 =
                    StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE);
            StackWalker stackWalker3 =
                    StackWalker.getInstance(Set.of(Option.RETAIN_CLASS_REFERENCE, Option.SHOW_HIDDEN_FRAMES));
            StackWalker stackWalker4 =
                    StackWalker.getInstance(Set.of(Option.RETAIN_CLASS_REFERENCE), 32); // with estimated size
        }


        StackWalker stackWalker =
                StackWalker.getInstance();

        {
            // for each
            // print the frame
            stackWalker.forEach(System.out::println); // print out all the stack frame
            stackWalker.forEach(stackFrame -> System.out.println(stackFrame)); // using lambda
            stackWalker.forEach(new Consumer<StackWalker.StackFrame>() { // using anonymous inner class
                @Override
                public void accept(StackWalker.StackFrame stackFrame) {
                    System.out.println(stackFrame);
                }
            });
        }

        {
            // collect the frames
            final List<StackWalker.StackFrame> frames = new ArrayList<>();
            stackWalker.forEach(frames::add);
            stackWalker.forEach(stackFrame -> frames.add(stackFrame));
            stackWalker.forEach(new Consumer<StackWalker.StackFrame>() {
                @Override
                public void accept(StackWalker.StackFrame stackFrame) {
                    frames.add(stackFrame);
                }
            });
        }

        {
            // stream
            // collect the frames
            List<StackWalker.StackFrame> frames = stackWalker.walk(s -> s.collect(Collectors.toList()));

            // count the number of frames
            long nbFrames = stackWalker.walk(s -> s.count());

        }

        //System.out.println(StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE).getCallerClass());


        List<StackWalker.StackFrame> caller = stackWalker.walk(s -> s.limit(2).collect(Collectors.toList()));
        OtherClass.whoCalledMe();
    }

    public static class OtherClass {
        public static void whoCalledMe() {
            StackWalker stackWalker = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE);
            Optional<StackWalker.StackFrame> caller = stackWalker.walk(s -> s.skip(1).findFirst());
            System.out.println(caller);
            System.out.println(stackWalker.getCallerClass());
        }
    }

}
