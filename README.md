# Healthy Condition
SpacecraftAndroid

| branches  | Build Status   |
|---| --- |
| Spacecraft-Plan/SpacecraftAndroid master branch  | [![Build Status](https://travis-ci.com/Spacecraft-Plan/SpacecraftAndroid.svg?branch=master)](https://travis-ci.com/Spacecraft-Plan/SpacecraftAndroid)   |
|  JamesfChen/SpacecraftAndroid master branch |  [![Build Status](https://travis-ci.org/JamesfChen/Spacecraft.svg?branch=master)](https://travis-ci.org/JamesfChen/Spacecraft)  |
|  gitlab/SpacecraftAndroid master branch |  [![pipeline status](https://gitlab.com/spacecraft-plan/spacecraftandroid/badges/master/pipeline.svg)](https://gitlab.com/spacecraft-plan/spacecraftandroid/-/commits/master) |
| feature/kotlin-gradle branch   |  [![Build Status](https://travis-ci.org/JamesfChen/Spacecraft.svg?branch=feature/kotlin-gradle)](https://travis-ci.org/JamesfChen/Spacecraft) |
|  apk  |  [download url](https://www.pgyer.com/rDcO) |

|  language  |  files |lines|
|---| --- |---|
|  java|187 |22399|
|  kotlin|190|9727|
|  c or c++|14|1377|


# To be Architect
> 驽马十驾功在不舍

ps:下面会提到一些相关名词，这里先定义一下。bundle是依附于app framework的native bundle(静态组件，动态插件)、flutter、react native、hybrid，其能够被app framework动态加载；module指app framework的功能模块，赋予上层能力的module，更像是一些用来快速开发页面的toolkits。

## Foundation Module
### 1. Common Module
- [x] utils
- [ ] mvp、mvvm：20%
- [x] asynchronize:kotlin corountine、RxJava、AsyncTask
- [ ] serialization:persistence(json/protobuf)、memory(Parcelable/Serializable interface) 
### 2. Network Module
~- [x] 提供mock环境、real环境、real local server环境~
- [x] network service : Retrofit+RxJava2
- [x] mock:stetho + mockserver

### 3. Storage Module
- [ ] Room/Realm,SharedPreference

### 4. LBS(Location & Map) Module
- [x] collection location 、 wifi 、 cell info
### 5. Image & Graphic Module

### 6. Audio & Video Module
- [x] screen recorder

## Business Module
### 1.UI Component Module
- [x] provide ui component , es.video component
### 2. Login/Logout Module
- [x] login
### 3. IM Module
- [ ] IM

## Business Bundle
通信方式url 路由和rpc调用

- [ ] native bundle
- [ ] hybrid
- [ ] ReactNative/Flutter

## Improve Development Efficiency
- [x] gradle plugin：
1. file lines counter plugin(./gradlew app:counter)
2. rename apk plugin
3. upload bugly mapping or so file plugin( ./gradlew reportMappingDebug  &&  ./gradlew reportSoDebug)
4. performance plugin: trace method(instrumentation)
- [x] CI / CD
- [x] hook:jni hook、java hook


