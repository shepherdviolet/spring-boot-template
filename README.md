# Spring Boot Gradle 工程模板

* git clone https://github.com/shepherdviolet/spring-boot-template.git your_project_name
* execute init-template.bat
* gradlew.bat build

# Package to spring boot jar

> gradlew.bat bootJar

# Startup (jar or jars)

* VM Options

```text
-Dspring.profiles.active=linux -Denv=dev -Dlog.path=/yourpath/logs -Dserver.tomcat.basedir=/yourpath/tmp
```

```text
java -jar name.jar
```

```text
LIB_PATH=/home/yourname/libs
for file in $LIB_PATH/*jar; do
	JAR_PATH=$JAR_PATH:$file
done

java -cp ${JAR_PATH} org.springframework.boot.loader.JarLauncher
```

# Startup (Intellij IDEA)

> Run template.BootApplication class
