## Current Libraries:
* org.xerial.sqlite-jdbc (SQLite JDBC library)
* javalin (lightweight Java Webserver)
* thymeleaf (HTML template) - https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html

Libraries required as dependencies:
* By javalin
   * slf4j-simple (lightweight logging)
* By xerial/jdbc
   * sqlite-jdbc

## Running the Main web server
You can run the main webserver program similar to the project workshop activities
1. Open this project within VSCode
2. Allow VSCode to read the pom.xml file
 - Allow the popups to run and "say yes" to VSCode configuring the build
 - Allow VSCode to download the required Java libraries
3. To Build & Run
 - Open the ``src/main/java/app/App.java`` source file, and select "Run" from the pop-up above the main function
4. Go to: http://localhost:7001
