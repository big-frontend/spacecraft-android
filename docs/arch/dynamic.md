# 动态化
随着插件化的落幕，React Native 、小程序、Flutter跨平台框架开始中原逐鹿。

## React Native动态化

要实现React Native动态化落地到项目中，需要客户端具备多bundle加载 + 打包工具(打出js bundle带版本号、能push bundle到分发平台等新能力) + 分发平台。

在expo项目中对应如下

- [expo go](https://github.com/expo/expo/tree/main/apps/eas-expo-go)：多bundle加载
- [eas-cli](https://github.com/expo/eas-cli)：发布bundle到eas平台
- [eas](https://expo.dev/eas)：分发平台

那么对于客户端如何实现多个bundle加载的功能 ？