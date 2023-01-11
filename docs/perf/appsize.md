# 包体积(appsize)


- Android包:so、dex、资源
- js bundle:js.bundle 

## 优化
minify
- Code shrinking(or tree-shaking，摇树优化) 、obfuscated(混淆) 
   - [ProGuard and R8: Comparing Optimizers](https://www.guardsquare.com/blog/proguard-and-r8)
- Code Optimize 
   - lint:少用枚举用@IntDef替代、AnimatedDrawable替换为AnimatedVectorDrawable

- Resource shrinking(缩减)、obfuscated(混淆)
   - [Smaller APKs with resource optimization](https://jakewharton.com/smaller-apks-with-resource-optimization/)
   - [安装包立减1M--微信Android资源混淆打包工具](https://mp.weixin.qq.com/s?__biz=MzAwNDY1ODY2OQ==&mid=208135658&idx=1&sn=ac9bd6b4927e9e82f9fa14e396183a8f#rd)

- Optimize 
   - R Filed内联等
   - [redex](https://github.com/facebook/redex)

- [Android App包瘦身优化实践](https://tech.meituan.com/2017/04/07/android-shrink-overall-solution.html)
- [缩减应用大小](https://developer.android.com/topic/performance/reduce-apk-size?hl=zh-cn)
- [性能优化：得物App包体积治理之路](https://mp.weixin.qq.com/s/1aAgY4OPnZl650Q8vD3LNA)