Learning Project for Java (Library Management System)

Topics:
1 - Java syntax
2 - Generics
3 - IO Stream
4 - JUnit

Project Structure (Maven single-module):
- `pom.xml` — project configuration
- `src/main/java/com/mahsan/library/...` — application sources
- `src/test/java/...` — unit tests (JUnit 5)
- `config/` — runtime config (JSON)
- `data/` — runtime data files (JSON)
- `doc/` — documentation

Build and Test:
```bash
mvn -DskipTests=false test
mvn package
```

Run (from the project root):
```bash
java -jar target/library-management-system-1.0-SNAPSHOT.jar
```

Note: The app expects `config/config.json` and `data/*.json` to be present next to the jar (or when running from the project root).
