# Session Handling: setup and run

This JSP/Servlet project demonstrates URL rewriting, hidden form fields, cookies,
and HttpSession. Supports Windows, macOS, and Linux with **JDK 11 or newer**
(including 17, 21, and 25). Java 8 and earlier are unsupported.
No IDE, global Maven, or separate Tomcat installation is required for local use.

## Quick start

Open a terminal in this folder. Verify `java -version` and `javac -version`.
Install a JDK, rather than only a JRE. If setting JAVA_HOME, point it to the JDK
folder, not its bin folder. JAVA_HOME and PATH should select the same JDK.
The first run needs internet access to download Maven and dependencies.

Windows PowerShell or Command Prompt:

```powershell
.\mvnw.cmd compile exec:java
```

macOS / Linux / POSIX shell:

```sh
sh ./mvnw compile exec:java
```

Open **http://localhost:8080/SessionHandling/**. Stop with **Ctrl+C**.
The local server listens on loopback. Generated files are kept in target/.
After editing source files, stop the server and repeat the command.
If Maven is already installed, `mvn compile exec:java` works on all platforms.

To use another port:

```powershell
.\mvnw.cmd "-Dapp.port=9090" compile exec:java
```

```sh
sh ./mvnw -Dapp.port=9090 compile exec:java
```

Then open http://localhost:9090/SessionHandling/.

## Build and deploy a WAR

Windows:

```powershell
.\mvnw.cmd clean package
```

macOS / Linux:

```sh
sh ./mvnw clean package
```

Result: **target/SessionHandling.war**. For deployment to a separate server,
copy this file into **Tomcat 10.1.x**'s webapps directory. The server provides
Servlet and JSP dependencies. The WAR is not an executable JAR.

PowerShell (replace the example path):

```powershell
$tomcatDirectory = 'C:\tools\apache-tomcat-10.1'
Copy-Item target/SessionHandling.war "$tomcatDirectory/webapps/"
& "$tomcatDirectory/bin/catalina.bat" run
```

macOS / Linux:

```sh
TOMCAT_HOME='/path/to/apache-tomcat-10.1'
cp target/SessionHandling.war "$TOMCAT_HOME/webapps/"
sh "$TOMCAT_HOME/bin/catalina.sh" run
```

Open http://localhost:8080/SessionHandling/. Stop with Ctrl+C.
Tomcat 9 and older use javax.servlet and cannot run this Jakarta application.
See [Tomcat's Java compatibility table](https://tomcat.apache.org/whichversion.html).

## IDE setup

Import this directory as an existing Maven project in Eclipse, IntelliJ IDEA,
or VS Code. Select JDK 11+ and reload Maven dependencies. Use the terminal
commands above, or configure Tomcat 10.1 in the IDE and deploy the WAR.
In Eclipse: File > Import > Maven > Existing Maven Projects. For Run on Server,
select Tomcat 10.1 and context root SessionHandling.

## Check all four demonstrations

Enter a username on the home page, then use each button:

| Technique | Expected result |
| --- | --- |
| URL Rewriting | Welcome message; Continue carries the username in its URL. |
| Hidden Form Field | Continue submits the hidden username and displays a welcome message. |
| Cookies | Read Cookie displays the stored username, including spaces and Unicode. |
| HttpSession | Open again displays the same username and session ID. |

Tomcat adds `;jsessionid=...` to encoded URLs when it cannot yet rely on a session
cookie. Block cookies for localhost to observe this across requests; enable them
again for the cookie demo. Inspect hidden inputs in page source and cookies in
browser developer tools. These are teaching examples, not authentication.

## Troubleshooting

| Problem | Fix |
| --- | --- |
| java or javac missing | Install JDK 11+ and correct JAVA_HOME/PATH. |
| Unsupported class version / invalid release | Check wrapper `-v` output and the server's JDK; both need Java 11+. |
| Download failure | Check internet access to repo.maven.apache.org; configure proxies in your user .m2/settings.xml if needed. |
| Port occupied | Stop the other process or select app.port as above. |
| 404 | Use the exact /SessionHandling/ URL and check startup logs for errors. |
| Cookie missing | Enable cookies for localhost and use Cookies before Read Cookie. |
| Stale changes | Stop the server, run `clean compile exec:java` through the wrapper, and refresh. |

The wrapper pins Maven for consistent builds. Windows and POSIX require different
script launch commands, but use identical Maven goals and application URLs.
Offline runs require Maven and every needed dependency to be cached beforehand.
