# Spring Boot Gradle 工程模板

* 用于快速创建一个SpringBoot工程

| 工程名 | 说明 |
| ------ | ----- |
| web-demo | 大量的SpringBoot使用示例 |
| web-archetype | WEB子工程原型(Spring Boot) |
| module-archetype | 模块子工程原型(Java Library) |

<br>
<br>

# 新建

## 新建工程

### Windows环境新建工程

* 浏览器访问如下地址, 手动下载`sprintboot-startup.bat`脚本:

```text
https://gitee.com/shepherdviolet/spring-boot-template/raw/master/docs/utils/springboot-startup.bat
```

* 执行下载来的脚本`sprintboot-startup.bat`, 输入新工程名称, 控制台输出如下:

```text
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Startup a SpringBoot Gradle Project
1.Clone spring-boot-template from remote
2.Delete the original .git
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Enter new project name:new-project-name
Processing ...
Cloning into 'new-project-name'...
......
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Finished
Read 'new-project-name\README.md' to create sub-project automatically
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
请按任意键继续. . .
```

* Done!

### Linux环境新建工程

* 命令行执行如下命令, 其中`new-project-name`为新工程名称, P.S. Windows环境用cmder也可以这么做

```text
curl -L https://gitee.com/shepherdviolet/spring-boot-template/raw/master/docs/utils/springboot-startup.sh | sh -s new-project-name
```

* 输入新工程名称, 等待新工程创建完毕
* Done!

### 其他环境新建工程(手动)

* 克隆: `git clone https://gitee.com/shepherdviolet/spring-boot-template.git new-project-name`
* 删除新工程目录下的`.git`(删除原来模板工程的版本数据)
* Done!

<br>
<br>

## 根据原型生成子工程

* 在工程根目录`build.gradle`文件的末尾添加如下内容:

```text
// 子工程创建器配置
ext.project_creator = [
		// 需要创建的子工程清单
		'projects': [
				// [KEY修改] 子工程路径
				'web/web-new': [
						// [VALUE修改] 原型路径
						'archetype_path': 'web/web-archetype',
						'replace_context': [
								// [KEY/VALUE修改] 自定义参数
								// 包路径
								'java_package': 'com.test.webnew',
								// 主类名
								'application_class': 'WebNewApplication',
								// app.id
								'app_id': 'web-new'
						]
				],
				// [KEY修改] 子工程路径
				'module/module-new': [
						// [VALUE修改] 原型路径
						'archetype_path': 'module/module-archetype',
						'replace_context': [
								// [KEY/VALUE修改] 自定义参数
								// 包路径
								'java_package': 'com.test.modulenew'
						]
				]
		]
]

// [必须] 引入创建子工程的Gradle脚本
apply from: 'gradle/utils/project-creator.gradle'
```

* 控制台执行`gradlew createProject`, 生成子工程

* 修改工程根目录的`settings.json`文件, 将新生成的子工程配置到清单中

```text
{
  "web": [
    "web-new"
  ],
  "module": [
    "module-new"
  ]
}
```

* Done! 删除此前`build.gradle`末尾添加的内容

* `P.S.想要了解更多请查看gradle/utils/project-creator.gradle`

<br>
<br>

# 部署

## 编译

```text
gradlew bootJar
```

## 启动

* 启动参数按需设置

```text
java -Dspring.profiles.active=linux -Denv=dev -Dlog.path=/your-path/logs -Dserver.tomcat.basedir=/your-path/tmp -jar your-project-name.jar
```
