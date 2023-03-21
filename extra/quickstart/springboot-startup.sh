#!/bin/bash

echo "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
echo "Startup a SpringBoot Gradle Project"
echo "1.Clone spring-boot-template from remote"
echo "2.Delete the original .git"
echo "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"

NEW_PROJECT_NAME=$1

if [ "x$NEW_PROJECT_NAME" == "x" ]; then
    echo "ERROR: New project name must be specified by arg1, For example:"
    echo "1) sh springboot-startup.sh new-project-name"
    echo "2) curl -L https://raw.githubusercontent.com/shepherdviolet/spring-boot-template/master/extra/quickstart/springboot-startup.sh | sh -s new-project-name"
    exit 1
fi

echo "Processing ..."
git clone https://github.com/shepherdviolet/spring-boot-template.git $NEW_PROJECT_NAME && cd $NEW_PROJECT_NAME && rm -rf .git && echo "Finished"

echo "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
echo "Read '$NEW_PROJECT_NAME\README.md' to create sub-project automatically"
echo "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
