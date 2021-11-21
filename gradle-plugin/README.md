## Gradle Plugin Collector

> ./gradlew app:counter

|  language  |  files |lines|
|---| --- |---|
|  java|187 |22399|
|  kotlin|190|9727|
|  c or c++|14|1377|

> lifecycle-plugin

老版插件管理
```gradle

//在rootProject的build.gradle添加
classpath 'com.github.jamesfchen:lifecycle-plugin:1.0.0'

//在模块build.gradle添加
apply plugin: 'com.jamesfchen.lifecycle-plugin'

api 'com.github.jamesfchen:lifecycle-api:1.0.0'
```

新版插件管理
```gradle
plugins {
    id 'com.jamesfchen.lifecycle-plugin' version '1.0.0'
}

api 'io.github.jamesfchen:lifecycle-api:1.0.0'
```

```java
@App  //在AppLicatioin子类添加@App注解
public class TApp extends Application {
    //必须继承onCreate，插桩代码会在这里插入代码
    @Override
    public void onCreate() {
        super.onCreate();
    }
    ....
}
@AppLifecycle // 在LifecycleObserver子类添加@AppLifecycle
public class AppLifecycleObserver implements LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void connectListener(LifecycleOwner lifecycleOwner) {
        Log.d("cjf", "AppLifecycleObserver connectListener");
    }
}
```

> perf-plugin
```gradle
//在rootProject的build.gradle添加
classpath 'io.github.jamesfchen:pref-plugin:1.0.0'

//在模块build.gradle添加
apply plugin: 'com.jamesfchen.perfplugin'
```

> base-plugin
```
//快速插入code到指定的类文件，无需先扫描文件在插入code到指定类文件，代码可以参考perf-plugin插件
FastInsertCodePlugin

//对于有些功能需要先扫描类文件，收集各个类的信息，然后在插入code到指定类文件中，代码可以参考lifecycle-plugin插件
ScanClassPlugin

implementation 'io.github.jamesfchen:base-plugin:1.0.0'
```

> ibc-plugin

```
implementation 'com.github.jamesfchen:ibc-api:1.0.0'

//页面路由
@Router(name = "bundle1router")
public class Bundle1Router implements INativeRouter {
}
```
`To Be Continue ...`


## maven local || maven central

jcenter被废弃之后有没有替代方案当时时有的，这里提供一个脚本(项目的script目录下的publisher.gradle)支持将模块发布到maven local 或者maven central

```gradle
apply from: "${rootDir}/script/publisher.gradle"
publish{
    name="lifecycle-api"
    groupId = rootProject.groupId
    artifactId = "lifecycle-api"
    version = "1.0.0"
    website = "https://github.com/JamesfChen/spacecraft-android-gradle-plugin"
}
```

而发布插件就更加简单了，gradle提供了两个插件来让我们发布插件com.gradle.plugin-publish和gradlePlugin
```gradle
plugins {
    id "com.gradle.plugin-publish" version "0.15.0"
}
pluginBundle {
    vcsUrl = 'https://github.com/JamesfChen/spacecraft-android-gradle-plugin'
    website = "https://github.com/JamesfChen/spacecraft-android-gradle-plugin"
    tags = ['app lifecycle']
    version = '1.0.0'
}

apply from: "${rootDir}/script/base.gradle"
apply plugin: "java-gradle-plugin"
gradlePlugin {
    plugins {
        lifecycleplugin {
            id = 'com.jamesfchen.lifecycle-plugin'
            implementationClass = 'com.jamesfchen.lifecycle.LifecyclePlugin'
            displayName = 'lifecycle plugin'
            description = "组件化之后，组件监听app的生命周期工具"
        }
    }
}
apply plugin: 'maven-publish'
```

使用./gradlew publishToMavenLocal发布到maven local(~/m2/repository/{group})
- 对于模块来说，会被打包到一个目录下
- 对于gradle插件来说，会被打包成两个目录下(相当于执行publishXxxPluginMarkerMavenPublicationToMavenLocal 和 publishPluginMavenPublicationToMavenLocal任务)

## 插桩(instrumentation)分类:

- 源代码插桩 Source Code Instrumentation(SCI)：kapt or java注解处理器生成java代码

- 二进制插桩（Binary Instrumentation）
    - 静态二进制插桩[Static Binary Instrumentation(SBI)]:插入额外的字节码，然后利用重打包技术 或者 asm在编译期间插入字节码
    - 动态二进制插桩[Dynamic Binary Instrumentation(DBI)]：hook需要修改的函数，在运行期间将本应该执行的函数指向篡改之后的函数

## reference
[aspectj](https://www.eclipse.org/aspectj/)

[asm](https://asm.ow2.io/index.html)

[ReDex](https://github.com/facebook/redex)

[Android字节码插桩](https://www.daimajiaoliu.com/daima/4795c92d31003fc)

[Jvm系列3—字节码指令](http://gityuan.com/2015/10/24/jvm-bytecode-grammar/)

[Advanced Java Bytecode ](https://www.jrebel.com/blog/java-bytecode-tutorial)

[bytecode with asm](https://courses.cs.ut.ee/MTAT.05.085/2016_spring/uploads/Main/Generating_bytecode.pdf)

[不得不学之「 ASM 」④ 加密字符串原理](https://www.yuque.com/mr.s/hs39hv/yrzlp5?language=zh-cn)

[JVM基础知识和ASM修改字节码](https://blog.csdn.net/sweatOtt/article/details/88114002)

[Introduction to Java Bytecode](https://dzone.com/articles/introduction-to-java-bytecode)

[Java MethodVisitor.visitTypeInsn方法代码示例](https://vimsky.com/examples/detail/java-method-org.objectweb.asm.MethodVisitor.visitTypeInsn.html)

[配置编译版本](https://developer.android.com/studio/build)

[Transform详解](https://www.jianshu.com/p/37a5e058830a)

[10分钟了解Android项目构建流程](https://juejin.cn/post/6844903555795517453#heading-8) 
