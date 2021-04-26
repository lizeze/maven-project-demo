
# 创建项目

打开`idea`创建一个新的项目，选择`Spring Initailizr`,下一步下一步，创建好一个项目。
![image.png](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/f21bde8ce39e496ea13a633994eb24eb~tplv-k3u1fbpfcp-watermark.image)


![image.png](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/23005e3a93284b8baedfe0073dd89556~tplv-k3u1fbpfcp-watermark.image)

创建好之后只保留`pom`文件，其他全部删除


![image.png](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/4216b9c9906245c3bfc4c0c68014a1e4~tplv-k3u1fbpfcp-watermark.image)
# 新增模块

在根目录上右键新增模块，如下图，选择`Module`之后和创建项目一样，下一步下一步。



![image.png](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/f66a07c796f246d893a4404f27af8478~tplv-k3u1fbpfcp-watermark.image)

……

创建好了三个项目，`project`项目保留启动类，其他两个项目只保留`src`目录和`pom`文件

![image.png](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/803be399483e4ef3b9074156ddff7305~tplv-k3u1fbpfcp-watermark.image)

# 配置项目

## root-server

 * 修改打包方式

 ```xml
    <packaging>pom</packaging>
 ```
 * 增加子模块配置
 ```xml
 
     <modules>
        <module>project-service</module>
        <module>project-app</module>
        <module>project-start</module>
    </modules>
 ```
 
 * 打包配置
 
 ```xml
 <build>
        <plugins>
            <!--打包jar-->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <configuration>
                    <!--不打包资源文件-->
                    <excludes>
                        <exclude>config/**</exclude>
                        <exclude>*.xml</exclude>
                        <exclude>*.yml</exclude>
                    </excludes>
                    <archive>
                        <manifest>
                            <addClasspath>true</addClasspath>
                            <!--MANIFEST.MF 中 Class-Path 加入前缀-->
                            <classpathPrefix>lib/</classpathPrefix>
                            <!--jar包不包含唯一版本标识-->
                            <useUniqueVersions>false</useUniqueVersions>
                            <!--指定入口类-->
                            <mainClass>com.lzz.project.start.ProjectStartApplication</mainClass>
                        </manifest>
                        <manifestEntries>
                            <!--MANIFEST.MF 中 Class-Path 加入资源文件目录-->
                            <Class-Path>./resources/</Class-Path>
                        </manifestEntries>
                    </archive>
                    <outputDirectory>${project.build.directory}</outputDirectory>
                </configuration>
            </plugin>

            <!--拷贝依赖 copy-dependencies-->
            <plugin>
                <!-- https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-dependency-plugin -->

                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-dependency-plugin</artifactId>
                <version>3.1.2</version>
                <executions>
                    <execution>
                        <id>copy-dependencies</id>
                        <phase>package</phase>
                        <goals>
                            <goal>copy-dependencies</goal>
                        </goals>
                        <configuration>
                            <outputDirectory>
                                ${project.build.directory}/lib/
                            </outputDirectory>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
 ```
 > 注意指定入口类
 ## 修改子模块
 
  * 修改`parent`节点
  
  ```xml
     <parent>
       <artifactId>root-server</artifactId>
        <groupId>com.lzz</groupId>
        <version>0.0.1-SNAPSHOT</version>
        <relativePath>../pom.xml</relativePath>
    </parent>
  ```
  
  * 指定打包方式
  ```xml
      <packaging>jar</packaging>
  ```
  * 删除build节点
   
   完整配置
   
   ```
   <?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
       <artifactId>root-server</artifactId>
        <groupId>com.lzz</groupId>
        <version>0.0.1-SNAPSHOT</version>
        <relativePath>../pom.xml</relativePath>
    </parent>
    <packaging>jar</packaging>
    <groupId>com.lzz</groupId>
    <artifactId>project-app</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>project-app</name>
    <description>Demo project for Spring Boot</description>
    <properties>
        <java.version>1.8</java.version>
    </properties>
</project>

   ```
   
   > 以上配置适用所有子模块,复制到所有子模块
   
   #  运行代码
   ## 在service中增加一个class
   
   ```java
   @Service
public class ProjectService {
    public String sendMessage() {
        return "maven  test";
    }
}
   ```
   
   ## 在app模块中增加一个Controller
   ```java
   @RestController
@RequestMapping("/api/")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping("test")
    public String mess() {

        return projectService.sendMessage();
    }
}
   ```
   
   `pom`文件中新增
   ```xml
     <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>com.lzz</groupId>
            <artifactId>project-service</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
    </dependencies>
   ```
   
   ## 启动项目
   
 *   修改`pom`文件
   ```xml
   <dependencies>
        <dependency>
            <groupId>com.lzz</groupId>
            <artifactId>project-app</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
    </dependencies>
   ```
   
  *  修改启动类
   
   ```java
   @SpringBootApplication(scanBasePackages = "com.lzz")
   ```
   
![image.png](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/2d52644e14a7497aa4d0f16157c60ebe~tplv-k3u1fbpfcp-watermark.image)

*  多环境配置
```yml
spring:
  profiles:
    active: dev
```
   
   
![image.png](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/0fd1c2146ce64b3690a2c71ea460f004~tplv-k3u1fbpfcp-watermark.image)

`dev`和`prod`要放到直接放到`resources`中，不能放在子目录中，不知道为什么

 启动代码，访问 `http://localhost:8080/api/test`
 
 
![image.png](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/b1e27898130a4fac86546952a348f0d2~tplv-k3u1fbpfcp-watermark.image)

# 获取代码

[获取完整代码](https://github.com/lizeze/maven-project-demo)
