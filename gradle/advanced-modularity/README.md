# Gradle Advanced Modularity | Gradle 高级模块化

### 说明

* 本脚本增加了组别的概念, 能够在Gradle脚本中对指定组别进行通用配置
* 本脚本支持子工程多层目录结构(Gradle默认子工程只能在根目录下)
* 本脚本支持子工程放在任意目录下(Gradle默认子工程名称为目录名)

### 注意

* 子工程名全局唯一, 即使在不同的组别中, 也不允许重复
* 子工程相互依赖时, 使用"子工程名", 而不是目录名
* 子工程上传到Maven仓库时, 构建名默认是"子工程名"

### Instruction

* This script adds the concept of groups, which can be used to configure the specified groups in the Gradle script.
* This script supports multi-layer directory structure of sub-projects
* This script supports sub-projects placed in any directory

### Notice

* Subproject names are globally unique, even in different groups, they are not allowed to be repeated
* When subprojects depend on each other, use "subproject name" instead of directory name
* When the subproject is uploaded to the Maven repository, the default build name is "subproject name"

<br>
<br>

## How to configure subprojects in 'settings.json'? | 如何在 'settings.json' 中配置子工程?

* 子工程清单请配置在settings.json中, 格式如下:
* Please configure the subproject list in settings.json, the format is as follows:

```text
{
    "组别1": {
        "子工程名1":"子工程1的文件路径",
        "子工程名2":"子工程2的文件路径"
    },
    "组别2": {
        "子工程名3":"子工程3的文件路径"
    }
}
```

```text
{
     "Group 1": {
         "Subproject name 1": "File path of subproject 1",
         "Subproject name 2": "File path of subproject 2"
     },
     "Group 2": {
         "Subproject name 3": "File path of subproject 3"
     }
}
```

### Sample 1

* 子工程都在项目根目录下, 且子工程名称与文件路径一致
* The subprojects are all in the project root directory, and the name of the subproject is consistent with the file path

```text
{
    "group1": {
        "project1":"project1",
        "project2":"project2"
    },
    "group2": {
        "project3":"project3"
    }
}
```

### Sample 2

* 子工程都在项目根目录下, 但子工程名称与文件路径不一致
* The subprojects are all in the project root directory, but the subproject name is inconsistent with the file path

```text
{
    "group1": {
        "project1":"dir1",
        "project2":"dir2"
    },
    "group2": {
        "project3":"dir3"
    }
}
```

### Sample 3

* 子工程放在以组别命名的目录下
* The subproject is placed in a directory named after the group

```text
{
    "group1": {
        "project1":"group1/project1",
        "project2":"group1/project2"
    },
    "group2": {
        "project3":"group2/project3"
    }
}
```

### Sample 4

* 子工程放在任意路径下
* Place the subproject in any path

```text
{
    "group1": {
        "project1":"dir1",
        "project2":"dir2/dir3"
    },
    "group2": {
        "project3":"dir4/dir5/dir6"
    }
}
```

## What is the use of project groups? | 工程组别有什么用?

* Sample | 示例

```text
configure(subprojects.findAll { it.PROJECT_GROUP == 'web' }) {

    apply plugin: 'java'
    apply plugin: 'org.springframework.boot'

    sourceCompatibility = 1.8
    targetCompatibility = 1.8

    [compileJava, compileTestJava, javadoc]*.options*.encoding = "UTF-8"

    bootJar {
        mainClassName = main_class
    }

    dependencies {
        // Import BOMs
        implementation platform("com.github.shepherdviolet.glacimon:glacimon-bom:$version_glacimon")

        // Common dependencies
        implementation "ch.qos.logback:logback-classic:$version_logback"
    }

}

configure(subprojects.findAll { it.PROJECT_GROUP in ['module'] }) {

    apply plugin: 'java-library'

    sourceCompatibility = 1.8
    targetCompatibility = 1.8

    [compileJava, compileTestJava, javadoc]*.options*.encoding = "UTF-8"

    dependencies {
        // Import BOMs
        api platform("com.github.shepherdviolet.glacimon:glacimon-bom:$version_glacimon")

        // Common dependencies
        api "ch.qos.logback:logback-classic:$version_logback"
    }

}
```

<br>
<br>

## How to carry out advanced modular transformation of a project? | 如何对一个项目进行高级模块化改造?

* 将下列脚本放到工程指定目录下
* Put the following script in the project directory

```text
project-dir/gradle/advanced-modularity/README.md
                                      /before-build.gradle
                                      /on-settings.gradle
```

* 修改 `settings.gradle`, 删除所有内容, 只留下下面的代码
* Edit `settings.gradle`, delete everything, and leave only the following code

```text
// Gradle Advanced Modularity | Gradle 高级模块化
// [DO NOT MODIFY] In 'settings.gradle' script, parse the list of subprojects declared in 'settings.json' and add them to the gradle build.
// [请勿修改] 在 'settings.gradle' 脚本中, 解析 'settings.json' 中声明的子工程清单, 将它们加入gradle构建.
apply from: 'gradle/advanced-modularity/on-settings.gradle'

```

* 创建文件 `settings.json`, 用于配置子工程清单, 格式如下: 
* Create a file `settings.json` to configure the list of subprojects, the format is as follows:

```text
{
    "group1": {
        "project1":"dir1",
        "project2":"dir2"
    },
    "group2": {
        "project3":"dir3"
    }
}
```

* 修改工程根目录下的 `build.gradle`, 在文件的最上面增加如下代码:
* Edit `build.gradle` which in the root directory of the project, and add the following code at the top of the file:

```text
// Gradle Advanced Modularity | Gradle 高级模块化
// [DO NOT MODIFY] Initialization script before build
// [请勿修改] 构建前的初始化脚本
apply from: 'gradle/advanced-modularity/before-build.gradle'
```

* 修改工程根目录下的 `build.gradle`, 在`buildscript`块中, 增加对`groovy`的依赖 (否则Gradle脚本里对groovy包的引用会报错)
* Edit `build.gradle` which in the root directory of the project, in the `buildscript` block, add a dependency on `groovy`

```text
buildscript {
    ......
    dependencies {
        classpath localGroovy() // [Gradle Advanced Modularity] Depends on groovy, avoid importing groovy package errors
        ......
    }
}
```

* 完成!
* Complete!
