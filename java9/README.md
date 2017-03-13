Benchmarks for the [SitePoint article on Java 9's stack-walking API](https://www.sitepoint.com/deep-dive-into-java-9s-stack-walking-api).
If you want to run them you will need to:

* install [Java 9 EA](https://jdk9.java.net/download/)
* point the JAVA_HOME environment variable to the jdk 9 directory
 * on MacOS `export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-9.jdk/Contents/Home`
 * on Ubuntu with the [PPA Install](https://launchpad.net/~webupd8team/+archive/ubuntu/java/+index?field.series_filter=xenial) `export JAVA_HOME=/usr/lib/jvm/java-9-oracle`
* Build and run the benchmarks :

  ```bash
  cd java9
  ../apache-maven-3.2.5/bin/mvn clean package
  $JAVA_HOME/bin/java -jar target/benchmarks.jar -f 1
  ```
  
To get more help on the jmh command line arguments:

```bash
java -jar target/benchmarks.jar -help
```

Results on my MacBook Pro

    Benchmark                                                              (limit)  (skip)  Mode  Cnt   Score   Error  Units
    StackWalkerGetCallerClass.securityManager                                  N/A     N/A  avgt   20   1.498 ± 0.014  us/op
    StackWalkerGetCallerClass.stackwalkerGetImmediate                          N/A     N/A  avgt   20   1.124 ± 0.009  us/op
    StackWalkerGetCallerClass.stackwalkerSearch                                N/A     N/A  avgt   20   7.083 ± 0.723  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                             1     N/A  avgt   20   2.640 ± 0.318  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                             2     N/A  avgt   20   2.635 ± 0.214  us/op 
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                             4     N/A  avgt   20   2.779 ± 0.284  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                             6     N/A  avgt   20   3.051 ± 0.274  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                             8     N/A  avgt   20   7.555 ± 0.902  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                            10     N/A  avgt   20   7.824 ± 0.965  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                            12     N/A  avgt   20  12.369 ± 6.191  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                            14     N/A  avgt   20   8.861 ± 2.315  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                            16     N/A  avgt   20  13.643 ± 1.141  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit            1     N/A  avgt   20   2.754 ± 0.367  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit            2     N/A  avgt   20   2.669 ± 0.386  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit            4     N/A  avgt   20   2.722 ± 0.274  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit            6     N/A  avgt   20   2.812 ± 0.222  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit            8     N/A  avgt   20   3.555 ± 0.532  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit           10     N/A  avgt   20   4.259 ± 0.493  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit           12     N/A  avgt   20   6.149 ± 5.423  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit           14     N/A  avgt   20   5.707 ± 0.629  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit           16     N/A  avgt   20   6.336 ± 0.928  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A       1  avgt   20  13.079 ± 0.991  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A       2  avgt   20  13.321 ± 1.130  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A       4  avgt   20  13.379 ± 1.243  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A       6  avgt   20  13.054 ± 1.193  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A       8  avgt   20  13.041 ± 0.764  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A      10  avgt   20  13.359 ± 0.659  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A      12  avgt   20  12.941 ± 1.195  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A      14  avgt   20  13.212 ± 1.131  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A      16  avgt   20  13.737 ± 2.162  us/op
    StackWalkerVsExceptionBenchmark.exceptionStackTrace                        N/A     N/A  avgt   20  28.247 ± 0.461  us/op
    StackWalkerVsExceptionBenchmark.stackWalkerForEach                         N/A     N/A  avgt   20  13.589 ± 1.477  us/op
    StackWalkerVsExceptionBenchmark.stackWalkerForEachRetainClass              N/A     N/A  avgt   20  13.391 ± 1.032  us/op
    StackWalkerVsExceptionBenchmark.stackWalkerForEachToStackTraceElement      N/A     N/A  avgt   20  35.373 ± 1.311  us/op
