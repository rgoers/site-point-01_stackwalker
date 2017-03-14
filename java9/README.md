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
    StackWalkerGetCallerClass.securityManager                                  N/A     N/A  avgt   20   1.478 ± 0.013  us/op
    StackWalkerGetCallerClass.stackwalkerGetImmediate                          N/A     N/A  avgt   20   1.185 ± 0.012  us/op
    StackWalkerGetCallerClass.stackwalkerSearch                                N/A     N/A  avgt   20   2.335 ± 0.017  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                             1     N/A  avgt   20   2.397 ± 0.043  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                             2     N/A  avgt   20   2.424 ± 0.027  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                             4     N/A  avgt   20   2.757 ± 0.028  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                             6     N/A  avgt   20   3.045 ± 0.031  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                             8     N/A  avgt   20   7.804 ± 0.247  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                            10     N/A  avgt   20   7.711 ± 0.062  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                            12     N/A  avgt   20   7.984 ± 0.038  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                            14     N/A  avgt   20   8.537 ± 0.342  us/op
    StackWalkerLimitBenchmark.stackWalkerStreamLimit                            16     N/A  avgt   20  13.232 ± 0.142  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit            1     N/A  avgt   20   2.640 ± 0.049  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit            2     N/A  avgt   20   2.514 ± 0.014  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit            4     N/A  avgt   20   2.749 ± 0.179  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit            6     N/A  avgt   20   2.627 ± 0.015  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit            8     N/A  avgt   20   3.368 ± 0.028  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit           10     N/A  avgt   20   4.156 ± 0.033  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit           12     N/A  avgt   20   4.857 ± 0.093  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit           14     N/A  avgt   20   5.461 ± 0.065  us/op
    StackWalkerLimitWithEstimatedSizeBenchmark.stackWalkerStreamLimit           16     N/A  avgt   20   6.104 ± 0.088  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A       1  avgt   20  12.613 ± 0.046  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A       2  avgt   20  12.705 ± 0.156  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A       4  avgt   20  12.484 ± 0.173  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A       6  avgt   20  12.275 ± 0.063  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A       8  avgt   20  12.552 ± 0.175  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A      10  avgt   20  12.349 ± 0.167  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A      12  avgt   20  12.564 ± 0.153  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A      14  avgt   20  13.128 ± 0.476  us/op
    StackWalkerSkipBenchmark.stackWalkerStreamSkip                             N/A      16  avgt   20  13.246 ± 0.532  us/op
    StackWalkerVsExceptionBenchmark.exceptionStackTrace                        N/A     N/A  avgt   20  28.558 ± 0.343  us/op
    StackWalkerVsExceptionBenchmark.stackWalkerForEach                         N/A     N/A  avgt   20  12.873 ± 0.171  us/op
    StackWalkerVsExceptionBenchmark.stackWalkerForEachRetainClass              N/A     N/A  avgt   20  12.828 ± 0.236  us/op
    StackWalkerVsExceptionBenchmark.stackWalkerForEachToStackTraceElement      N/A     N/A  avgt   20  35.724 ± 0.123  us/op
    StackWalkerVsExceptionBenchmark.stackwalkerStackTrace                      N/A     N/A  avgt   20   3.763 ± 0.022  us/op
