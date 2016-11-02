Guide to get the project up and running.

1) Download the source code and import it in eclipse as a maven project.
2) Copy all the .java files into a temporary folder.
3) Create a class in src/main called App.java
4) Paste the code below in App.java
public class App{
  public static void main(String[] args) {
    System.out.println("Hello World!");
  }
}

5) Save the file as App.java
6) do a maven clean install using maven build for the pom.xml
7) The project should build now.
8) Add the .java files back into the original folder structure.
9) Add javaml-0-1-7.jar and all jars in the /lib folder as external libraries into the build path.
10) You can now EXECTURE the ML classes as java applications in eclipse. 
11) You can not however build the pom.xml through Maven because Maven does not know where to get the repositories/dependencies from.
12) As long as you donot do a build. But only execute the files as a Java Application - the project should work.
13) This is obviously a temporary work around until we figure out how to get the Maven dependencies fixed.
