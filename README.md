A Service that takes in Swedish personal numbers, co-ordination numbers, and organization numbers, and validates them

Compiles to run on Java 11. To run locally:

1. Run `mvn clean install`
2. `java -jar target/ValidityChecker-1.0-SNAPSHOT.jar $numberToValidate...`

Example: `java -jar target/ValidityChecker-1.0-SNAPSHOT.jar 194510168885 857202-7566`
Application will log all numbers that fail validation, and all numbers that pass validation