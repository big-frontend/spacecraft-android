# 高效开发

- mock平台:mitmproxy 
- 清晰的分包结构与代码高可复用：util、widget

# 高质量交付

## 代码设计(code design)

[CODEDESIGN](https://big-frontend.github.io/spacecraft-android/docs/CODEDESIGN.md)

## 代码评审(code review)

### 测试

- 覆盖率：[Android增量代码测试覆盖率工具](https://tech.meituan.com/2017/06/16/android-jacoco-practace.html)、JaCoCo
- 自动化测试：单元测试、集成测试等，[自动化测试在美团外卖的实践与落地](https://tech.meituan.com/2022/09/15/automated-testing-in-meituan.html)

### 质量扫描 、安全扫描(源代码、第三方组件) 、开源合规

- 质量扫描
    - 代码静态分析：[使用 lint 检查改进您的代码](https://developer.android.com/studio/write/lint.html?hl=zh-cn#commandline)  、 [Android Lint API Guide](https://googlesamples.github.io/android-custom-lint-rules/api-guide.html)  、 [美团外卖Android Lint代码检查实践](https://tech.meituan.com/2018/04/13/waimai-android-lint.html)  、 [UAST](https://plugins.jetbrains.com/docs/intellij/uast.html#using-uast-in-plugins)
    - Android Style : [Android 开发规范（完结版）](https://github.com/Blankj/AndroidStandardDevelop)
    - Kotlin Style : [Android Kotlin Style](https://developer.android.com/kotlin/style-guide?hl=zh-tw) 、[JB Kotlin Style](https://kotlinlang.org/docs/coding-conventions.html)
  
- 安全扫描
    - 风险代码 

## 性能与稳定性监控

# 部署 & 渠道

- [蒲公英分发](https://www.pgyer.com/manager/dashboard/app/747e76f865ef67134972fc6e54b7edbd)
- [firebase app distribution](https://console.firebase.google.com/project/spacecraft-22dc1/appdistribution/app/android:com.electrolytej.pisces/releases?hl=zh-cn)












 



