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

Results on my MacBook Pro with jdk 9-ea+160

    Benchmark                                                              (limit)  (skip)  Mode  Cnt   Score   Error  Units
    StackWalkerGetCallerClass.securityManager                                  N/A     N/A  avgt   20   1.554 ± 0.089  us/op
    StackWalkerGetCallerClass.stackwalkerGetImmediate                          N/A     N/A  avgt   20   1.178 ± 0.013  us/op
    StackWalkerGetCallerClass.stackwalkerSearch                                N/A     N/A  avgt   20   5.969 ± 0.055  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                             1     N/A  avgt   20   2.331 ± 0.015  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                             2     N/A  avgt   20   2.384 ± 0.017  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                             4     N/A  avgt   20   2.617 ± 0.021  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                             6     N/A  avgt   20   2.799 ± 0.025  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                             8     N/A  avgt   20   7.082 ± 0.056  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                            10     N/A  avgt   20   7.093 ± 0.122  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                            12     N/A  avgt   20   7.297 ± 0.039  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                            14     N/A  avgt   20   7.477 ± 0.061  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                            16     N/A  avgt   20  11.789 ± 0.079  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit            1     N/A  avgt   20   2.290 ± 0.016  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit            2     N/A  avgt   20   2.253 ± 0.020  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit            4     N/A  avgt   20   2.481 ± 0.082  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit            6     N/A  avgt   20   2.442 ± 0.060  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit            8     N/A  avgt   20   3.151 ± 0.036  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit           10     N/A  avgt   20   3.854 ± 0.040  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit           12     N/A  avgt   20   4.554 ± 0.055  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit           14     N/A  avgt   20   5.139 ± 0.060  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit           16     N/A  avgt   20   5.846 ± 0.085  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A       1  avgt   20  12.020 ± 0.087  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A       2  avgt   20  11.995 ± 0.061  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A       4  avgt   20  12.273 ± 0.180  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A       6  avgt   20  12.076 ± 0.122  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A       8  avgt   20  12.143 ± 0.130  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A      10  avgt   20  12.164 ± 0.133  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A      12  avgt   20  12.335 ± 0.280  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A      14  avgt   20  11.849 ± 0.070  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A      16  avgt   20  11.984 ± 0.145  us/op
    StackWalkerVsExceptionBenchmark.exceptionStackTrace                        N/A     N/A  avgt   20  28.253 ± 0.782  us/op
    StackWalkerVsExceptionBenchmark.stackWalkerForEach                         N/A     N/A  avgt   20  12.084 ± 0.113  us/op
    StackWalkerVsExceptionBenchmark.stackWalkerForEachRetainClass              N/A     N/A  avgt   20  12.093 ± 0.144  us/op
    StackWalkerVsExceptionBenchmark.stackWalkerForEachToStackTraceElement      N/A     N/A  avgt   20  33.717 ± 0.312  us/op
