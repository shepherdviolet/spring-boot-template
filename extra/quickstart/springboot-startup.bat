@echo off

echo ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
echo Startup a SpringBoot Gradle Project
echo 1.Clone spring-boot-template from remote
echo 2.Delete the original .git
echo ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

set /p NEW_PROJECT_NAME=Enter new project name:

echo Processing ...
git clone https://github.com/shepherdviolet/spring-boot-template.git %NEW_PROJECT_NAME% && cd %NEW_PROJECT_NAME% && rd /q /s .git && echo Finished

echo ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
echo Read '%NEW_PROJECT_NAME%\README.md' to create sub-project automatically
echo ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
pause
