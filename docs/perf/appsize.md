---
comments: true
---

# 包体积(Appsize)


- Android包:so、dex、资源
- js bundle:js.bundle 


## 监控

- 包体积分析：[react-native-bundle-visualizer](https://github.com/IjzerenHein/react-native-bundle-visualizer)

## 优化

### 拆包
- 动态下发：replugin、aab
- Gradle Product Flavor：分解业务

### Minify

#### Code Minify & Resource Minify

代码与资源都可以通过shrink、obfuscate进行包体积减小，代码主要使用[ProGuard and R8: Comparing Optimizers](https://www.guardsquare.com/blog/proguard-and-r8)，摇树优化(tree-shaking)进行缩减，字段简单命名开启混淆。资源进行无用资源、重复资源缩减、采用[安装包立减1M--微信Android资源混淆打包工具](https://mp.weixin.qq.com/s?__biz=MzAwNDY1ODY2OQ==&mid=208135658&idx=1&sn=ac9bd6b4927e9e82f9fa14e396183a8f#rd)进行混淆

#### Optimize 
   - lint:少用枚举用@IntDef替代、AnimatedDrawable替换为AnimatedVectorDrawable
   - R Filed内联、常量内联 、access 内联、方法删除(redex、bytex)
#### 更多阅读
- [redex](https://github.com/facebook/redex)
- [Smaller APKs with resource optimization](https://jakewharton.com/smaller-apks-with-resource-optimization/)
- [Android App包瘦身优化实践](https://tech.meituan.com/2017/04/07/android-shrink-overall-solution.html)
- [缩减应用大小](https://developer.android.com/topic/performance/reduce-apk-size?hl=zh-cn)
- [性能优化：得物App包体积治理之路](https://mp.weixin.qq.com/s/1aAgY4OPnZl650Q8vD3LNA)
