---
comments: true
---

# 包体积(Appsize)

- Android包:n、dex、资源
- js bundle:js.bundle 

好处

- 提高下载转化率
- 减少rom占用
- 优化运行时内存占用
- 缩短apk安装时间

## 开发提醒、包体积监控

- 包体积分析：[react-native-bundle-visualizer](https://github.com/IjzerenHein/react-native-bundle-visualizer)
- lint:少用枚举用@IntDef替代、AnimatedDrawable替换为AnimatedVectorDrawable

## 优化

### sdk去重
- Glide(500k) 与 Fresco(2-3M)
- Okhttp、Volley、Cronet
 
### 拆包
- 动态下发(replugin、aab、[so](https://github.com/IMFWorks/Android-So-Handler))：dex、so、资源
- 分解业务(Gradle Product Flavor)：tv、watch、phone
- Gradle resConfig、split

### Minify(Shrink缩减、Obfuscate混淆、Optimize优化)

代码与资源都可以通过shrink、obfuscate、Optimize进行包体积减小。
- Code Minify：代码的ProGuard或者R8是基于摇树优化(tree-shaking)进行缩减与字段简单命名进行混淆，除了缩减与混淆，我们还能进行代码的优化，基于redex和bytex能做R Filed内联、常量内联 、access 内联、方法删除等
- Resource Minify：资源缩减用清除无用资源、资源去重、png/webp/gif/jpg图片压缩、png/jpg转webp等方法,且混淆
- So Minify :去除符号表、非必要去除exception库

### 案例
- 模块
 - 去掉 Fresco(2-3M)
- 拆包
 - 非核心做成bundle：log、qrcode
 - 按需下发资源bundle：splits

- 代码
 - 有些代码没有被混淆
 - R field内联

- 资源
 - 文件去重：
   通过计算MD5值计算重复文件，然后修改resource.arsc资源位置。
 - 无用res去除、无用assets去除
 - 混淆：安装包立减1M–微信Android资源混淆打包工具
- so(https://tech.meituan.com/2022/06/02/meituans-technical-exploration-and-practice-of-android-so-volume-optimization.html)
 - 去除32位，GP强制要求上64位 so
- 压缩
 - 7zip压缩apk
 - png优化(更优压缩Pngquant 或者转webp)
 - jpg优化(packJPG 和 guetzli 等工具)
 - non-alpha png图片:对于不含alpha通道的png文件，可以转成jpg格式来减少文件的大小

## 技术选型

|   |matrix|booster|bytex
|--|--|--|--|
R field inline|  |☑️|☑️
const inline  |  |   | ☑️|
access inline |  |   |☑️| 
资源去重       |☑️|  |   |
`无用res、assets资源删除`|☑️| | |
`资源混淆`       |☑️| | |
png优化(更优压缩Pngquant 或者转webp)||☑️|
压缩           |☑️|☑️|

> AGP新资源缩减器(enableNewResourceShrinker)：7.1.0-alpha09试用，8.0正式使用
> AGP(enableResourceOptimizations): 4.2支持资源混淆

## 更多阅读

- [ProGuard and R8: Comparing Optimizers](https://www.guardsquare.com/blog/proguard-and-r8)
- [redex](https://github.com/facebook/redex)
- [ByteX](https://github.com/bytedance/ByteX)
- [Smaller APKs with resource optimization](https://jakewharton.com/smaller-apks-with-resource-optimization/)
- [Android App包瘦身优化实践](https://tech.meituan.com/2017/04/07/android-shrink-overall-solution.html)
- [缩减应用大小](https://developer.android.com/topic/performance/reduce-apk-size?hl=zh-cn)
- [性能优化：得物App包体积治理之路](https://mp.weixin.qq.com/s/1aAgY4OPnZl650Q8vD3LNA)
- [Booster](https://booster.johnsonlee.io/zh/guide/)
- [浅谈Android中的R文件作用以及将R资源inline减少包大小](https://yuweiguocn.github.io/android-r-inline/)
- [R 之初体验](https://medium.com/@morefreefg/%E5%85%B3%E4%BA%8E-r-%E7%9A%84%E4%B8%80%E5%88%87-355f5049bc2c)
- [Android对so体积优化的探索与实践](https://tech.meituan.com/2022/06/02/meituans-technical-exploration-and-practice-of-android-so-volume-optimization.html)
