Benchmarks to compare stack walking and locating the caller's class in Java 8 vs Java 9.
If you want to run them you will need to:

* install [Maven 3.2.5](https://archive.apache.org/dist/maven/maven-3/3.2.5/binaries/) (3.3.9 seems to fail on Java 9).

  ```bash
  wget https://archive.apache.org/dist/maven/maven-3/3.2.5/binaries/apache-maven-3.2.5-bin.tar.gz
  tar xzvf apache-maven-3.2.5-bin.tar.gz
  ```

* clone the repo, build and run the benchmarks :

  ```bash
  git clone https://github.com/rgoers/stackwalker-vs-Reflection_getCallerClass
  cd stackwalker-vs-Reflection_getCallerClass
  cd to the java 8 directory and follow the instructions in the README there.
  cd to the java 9 directory and follow the instructions in the README there.
  ```

