Benchmarks for compare Java 8 stack walking and locating the caller's class. 
If you want to run them you will need to:

* install Java 8
* point the JAVA_HOME environment variable to the jdk 8 directory
* Build and run the benchmarks :

  ```bash
  cd java8
  ../apache-maven-3.2.5/bin/mvn clean package
  $JAVA_HOME/bin/java -jar target/benchmarks.jar -f 1
  ```
  
To get more help on the jmh command line arguments:

```bash
java -jar target/benchmarks.jar -help
```
