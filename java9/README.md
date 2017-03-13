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
