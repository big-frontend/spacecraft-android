# Healthy Condition

| branches  | Build Status   |
|---| --- |
| Spacecraft-Plan/SpacecraftAndroid master branch  | [![Build Status](https://travis-ci.com/Spacecraft-Plan/SpacecraftAndroid.svg?branch=master)](https://travis-ci.com/Spacecraft-Plan/SpacecraftAndroid)   |
|  JamesfChen/SpacecraftAndroid master branch |  [![Build Status](https://travis-ci.org/JamesfChen/Spacecraft.svg?branch=master)](https://travis-ci.org/JamesfChen/Spacecraft)  |
|  gitlab/SpacecraftAndroid master branch |  [![pipeline status](https://gitlab.com/spacecraft-plan/spacecraftandroid/badges/master/pipeline.svg)](https://gitlab.com/spacecraft-plan/spacecraftandroid/-/commits/master) |
| feature/kotlin-gradle branch   |  [![Build Status](https://travis-ci.org/JamesfChen/Spacecraft.svg?branch=feature/kotlin-gradle)](https://travis-ci.org/JamesfChen/Spacecraft) |
|  apk  |  [download url](https://www.pgyer.com/rDcO) |

```
--- android 组件化项目
--- composite-builds
--- vi  #apm
    - viapm 项目性能监控系统
    - viopt 项目性能优化
    - dashboard apm表盘
```

# To be Architect
> 驽马十驾功在不舍

![architecture](./art/architecture.png)
 
|  language  |  files |lines|
|---| --- |---|
|  java|201 |24923|
|  kotlin|256|14222|
|  c or c++|46|9095|

## Foundation
### Common
- [x] utils
- [ ] mvp、mvvm：20%
- [x] asynchronize:kotlin corountine、RxJava、AsyncTask
- [ ] serialization:persistence(json/protobuf)、memory(Parcelable/Serializable interface)

### Network
~- [x] 提供mock环境、real环境、real local server环境~
- [x] network service : Retrofit+RxJava2
- [x] mock:stetho + mockserver

### Storage
- [ ] Room/Realm,SharedPreference

### Image & Graphic

### Audio & Video
- [x] screen recorder

### LBS(Location & Map)
- [x] collection location 、 wifi 、 cell info

### 1.UI Component
- [x] provide ui component , es.video component

## Business Bundle
- [ ] native bundle
    - [x] account-module
    - [ ] im-module
- [ ] hybrid bundle
- [ ] ReactNative/Flutter bundle

## Improve Development Efficiency

为了提高项目的编译速度，我将该项目组件化，组件化的模板代码可以看[bundles-assembler](https://github.com/JamesfChen/bundles-assembler)这个项目。

- [x] gradle plugin：
1. file lines counter plugin(./gradlew app:counter)
3. upload bugly mapping or so file plugin( ./gradlew reportMappingDebug  &&  ./gradlew reportSoDebug)
4. performance plugin: trace method(instrumentation)
- [x] CI / CD
- [x] hook:jni hook、java hook

#  项目管理
## 代码管理
- gerrit
- Gogs
- gitlab

## 任务管理
- redmine
- Phabricator
- jira

## git 工作流程
- git flow(版本发布)

存在两种长期分支
1. master:面向用户，稳定版本
2. develop:面向开发者，不稳定版本

存在三种临时分支
1. feature分支在于开发功能的时候创建的，由develop分支衍生出来的，开发完成会被并入develop分支
2. release分支用于发布版本之前创建的分支，由develop分支衍生出来的，如果测试有bug需要在此上面修复，然后会被并入master分支和develop分支；如果测试没有bug直接并入。
3.hotfix分支，在上线之后出现bug，由master分支衍生出来。修复之后并入master分支和develop分支。

很多手机厂商都是使用这种工作流程，比如小米手机既有稳定版本也有面试开发者的版本；google AOSP团队在发布正式版本之前都会开放预览版本，本质上和开发者版本相同。而对于Android App就没有听过面向开发者的版本，一般都做了隐藏，只公布稳定版供用户使用。为什么App没有开发者版本呢？理由很简单，竞争太他妈激烈了。有开发者版本不就让竞争对手知道了自己下一步的动作了吗。所以对于有开发者版本的公司，瞬间就让我肃然起敬。


- github flow（持续发布）

github flow的开发模式相对来说就更好理解了，只有master这个长期分支。通过提交pr让大家讨论pr从而促进团队交流。其实AOSP团队在使用gerrit的时候也使用这个机制。

- gitlab flow(前两者的有点结合)

Chromium项目采用该工作流程

参考文章：[Git 工作流程](http://www.ruanyifeng.com/blog/2015/12/git-workflow.html)

## 开发模式

- 瀑布流式
- 敏捷开发


