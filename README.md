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
|  java|156 |19352|
|  kotlin|168|8367|
|  c or c++|3|1792|

# To be Architect
> 驽马十驾功在不舍

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

编译期间(gradle plugin or kapt/annotationProcessor)的效率工具源码都在[spacecraft-android-gradle-plugin](https://github.com/JamesfChen/spacecraft-android-gradle-plugin)这个项目。

- [x] gradle plugin：
1. file lines counter plugin(./gradlew app:counter)
2. rename apk plugin
3. upload bugly mapping or so file plugin( ./gradlew reportMappingDebug  &&  ./gradlew reportSoDebug)
4. performance plugin: trace method(instrumentation)
5. check dangerous api plugin(./gradlew component:myhome:dangerousApiTask)
- [x] CI / CD
- [x] hook:jni hook、java hook


