

# 大前端 👋

一款跨平台框架必然涉及到两部分，前端与后端，前端为基于js或者dart语言创建的框架，后端为系统平台，提供硬件能力。

占大多数市场的桌面客户端系统有Windows、macOS、Android、iOS，浏览器分为手机浏览器与电脑浏览器，这些客户端统称为大前端，而在这些平台上面又衍生出了微信小程序、Flutter、React Native等跨平台的框架，这些框架主要是通过bright，将前端框架与系统平台连接起来从而实现跨平台。还有一种跨平台，通过编译将一种语言翻译到各个系统平台能识别的机器码或者字节码从而实现跨平台，Kotlin 与 Swift。

所以大前端基本涉及到这么一些关键字: React 全家桶(jsx、React、React Native、Expo)、微信小程序(js)、Flutter(dart)、Jetpack Compose(kotlin)、SwiftUI(swift)、Android、iOS等。

## 混合开发框架

- [dcloudio/uni-app](https://github.com/dcloudio/uni-app)、[taro](https://github.com/NervJS/taro)：uni-app、taro都是为了抹平React Native、各家小程序js框架的差异
- [expo](https://github.com/expo/expo/tree/master) & [facebook/react-native](https://github.com/facebook/react-native) & [react-navigation](https://github.com/react-navigation/react-navigation):Expo是React Native的工程化落地项目，更适合做业务
- [flutter](https://github.com/flutter/flutter):相比较于React Native项目，Flutter整个生态更加完整，工程化做得更好，可以用一个粗糙公式表示Flutter = Expo + React Native+生态贡献的代码。
- 微信小程序的前端框架：[tina](https://github.com/tinajs/tina)

## UI

- MD
  - [mui/material-ui for react](https://github.com/mui/material-ui)
  - [xotahal/react-native-material-ui](https://github.com/xotahal/react-native-material-ui)
  - [material-components](https://github.com/material-components/material-components)：google官方基于android 、iOS 、flutter、Web(HTML5)定制的MD组件

- Ant Design
  - [ant-design](https://github.com/ant-design/ant-design)：蚂蚁官方基于Vue和React两种框架定制的UI组件

- WeChat UI
  - [weui](https://github.com/Tencent/weui/blob/master/README_cn.md)：微信小程序官方UI组件
  - [wux-weapp](https://github.com/wux-weapp/wux-weapp)
  
- 各种自定义Android原生UI
  - [awesome-github-android-ui](https://github.com/opendigg/awesome-github-android-ui)
  - [awesome-android-ui](https://github.com/wasabeef/awesome-android-ui)

## 技术分享主题

Android技术已经走过了十多年了，从技术的增长期到技术的爆炸期，以及现在的稳定期，大量的技术变化带来了效率与性能的提高。在这个项目中，我们会讨论如今Android、跨平台(React Native 、Flutter、微信小程序)项目的工程化，拆分为三个主题：Architecture、DevOps、Performance

- [Architecture](https://big-frontend.github.io/pisces/arch/): 模块化、插件化、组件化、热修复、动态化
- [DevOps](https://big-frontend.github.io/pisces/devops/)：编码、测试、发布、运营
- [Performance](https://big-frontend.github.io/pisces/performance/)：Application Performance Monitoring 、Profiling 、 Optimization
- [bundles-assembler 项目wiki](https://github.com/electrolyteJ/bundles-assembler/wiki)

## Healthy Condition

| branches  | Build Status   |
|---| --- |
|  github/pisces main branch |  [![Pisces CI](https://github.com/big-frontend/pisces/actions/workflows/pisces.yml/badge.svg)](https://github.com/big-frontend/pisces/actions/workflows/pisces.yml)  |
|  gitlab/pisces main branch |  [![pipeline status](https://gitlab.com/big-frontend/pisces/badges/master/pipeline.svg)](https://gitlab.com/big-frontend/pisces/-/commits/master) |
|  apk  |  [download url](https://www.pgyer.com/rDcO) |

```
--- android 项目
--- arch # 关注项目架构：开发效率、项目耦合
--- vi  #performance  monitoring & profiling & optimization
```

|  language  |  files |lines|
|---| --- |---|
|  java|201 |24923|
|  kotlin|256|14222|
|  c or c++|46|9095|














 



