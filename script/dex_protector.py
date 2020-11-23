"""
[Android应用加固原理](https://juejin.cn/post/6844903952345989134#heading-12)
[Android DEX加固方案与原理](https://blog.csdn.net/EthanCo/article/details/102867224)
[性能优化 (七) APK 加固之 Dex 加解密，反编译都看不到项目主要代码。](https://juejin.cn/post/6844903858582323208)
[支持加壳应用的Android非侵入式重打包方法研究](http://xbna.pku.edu.cn/fileup/0479-8023/HTML/2018-6-1147.html)

防反编译
- 签名校验
- apk文件校验
- [Dex文件的完整性校验](https://blog.csdn.net/quanshui540/article/details/48468211)
"""

raw_apk_path='/Users/hawks.jamesf/tech/Spacecraft/SpacecraftAndroid/app/build/outputs/apk/release/app-arm64-v8a-r·elease.apk'