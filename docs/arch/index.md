# 模块演进(Module Evolution)

早期开发对于应用的来说只要一个模块就足以应付一切，代码通过分包的形式安装单一原则拆封网络、存储等包。随着人员增多，代码量膨胀，分包的形式变成了分模块，一些基础能力的功能被下沉到模块中，比如网络、存储这样的基础能力。当团队的规模来到了百人，代码量急剧庞大，并行的业务团队需要解耦，构建速度需要按需构建，业务包需要按需加载等等问题继续，组件化与插件化(如果没有系统禁止Hook系统Api，插件化就是唯一的解)。也正是系统禁止Hook系统Api且React Native、微信小程序的流行，转而都去用js引擎实现动态化。也正是这一变化，促成大前端时代的到来。


## 模块化
模块化我们主要从app framework 与 bundles 两个大方面来进行。app framework的模块通常有这些模块：common、 network、storage、image & graphic 、av(audio & video)、lbs(map & location)、ui component，bundles的模块通常有这些：user、im、code scanner、upgrade等

## 组件化/插件化
组件化的目的是优化构建速度与项目解耦，而插件化不仅拥有组件化的这些能力还能提高启动速度、降低包体积、优化内存。那么如何落地一个项目组件化与插件化，请移步这个项目[bundles-assembler](https://github.com/electrolyteJ/bundles-assembler)，欢迎star与提issue。

## 动态化
- Android
    - 插件化：[RePlugin](https://github.com/Qihoo360/RePlugin)
    - 热修复：[tinker](https://github.com/Tencent/tinker)、[Robust](https://github.com/Meituan-Dianping/Robust):前者需要重启应用patch才生效，后者实时生效。

- React Native
    - 热更新/热修复(code push,js bundle全量更新与增量更新)：[react-native-code-push](https://github.com/microsoft/react-native-code-push)/[appcenter-cli](https://github.com/microsoft/appcenter-cli)/[appcenter](https://appcenter.ms/)、[expo go](https://github.com/expo/expo/tree/main/apps/eas-expo-go)/[eas-cli
        ](https://github.com/expo/eas-cli)/[eas](https://expo.dev/eas)、[react-native-pushy](https://github.com/reactnativecn/react-native-pushy/)