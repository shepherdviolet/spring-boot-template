@echo off
echo ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
echo Spring Boot gradle project template initialize tool
echo 1.Set group and name in gradle.properties
echo 2.Delete .git / .idea / *.iml
echo ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
echo %cd%|findstr "spring\-boot\-template" >nul
if %errorlevel% equ 0 ( 
	echo ERROR
	echo Don't execute init-template.bat on "spring-boot-template" project!
	pause
	exit
)

if exist "i-am-spring-boot-template" (
	echo WELCOME
	echo This is Spring Boot gradle project template
	echo ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
) else (
	echo ERROR
	echo Don't execute init-template.bat, it's not a Spring Boot gradle project template!
	pause
	exit
)

set /p groupId=Set module groupId:
set /p name=Set module name:
echo ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
echo writing "gradle.properties" ...
echo project_group_id=%groupId%>gradle.properties
echo writing "settings.gradle" ...
echo include ':%name%'>settings.gradle
echo rename module ...
ren web %name%
echo deleting ".git" ...
rd /q /s .git
echo deleting ".idea" ...
rd /q /s .idea
echo deleting ".gradle" ...
rd /q /s .gradle
echo deleting "*.iml" ...
del "*.iml" /f /s /q /a
echo deleting "i-am-spring-boot-template" ...
del i-am-spring-boot-template
echo Complete!

pause
del init-template.bat