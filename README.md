# animals

Test assignment.
Console application with almost no user interaction. The only exception is a pause between the days in the simulation.

Goal:
While writing this application I have kept in mind the following Clean Code principles as taught by Robert Martin:
* keep your methods short;
* one level of abstraction per method;
* keep your classes conceptually short;
* refactor mercilessly;
* DRY
* SRP
* no comments - use meaningful names for variables and methods instead;
* order the methods so that each method follows shortly after its first invokation - each method has a distance from the public methods, and methods with the same distance are grouped and ordered as they are invoked; methods with longer distance follow those with less distance, and so methods do not usually appear right after their first call, but the average distance between a method call and its implementation is short; 
* dependency inversion principle - classes depending on external functionality refer to type (interface) rather than particular implementation; here I have also tried to be reasonable - not every class needs to have an interface layer.

See also the file Desing.pdf in the repo.

Use
* git clone git@github.com:OttVilson/animals.git
* cd animals
* mvn package
* java -jar target/animals-1.0-SNAPSHOT.jar
