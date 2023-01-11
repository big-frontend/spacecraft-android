# 模块演进(Module Evolution)
从使用Gradle构建系统开始，Android的模块也开始了演变，由原来的模块化到插件化、组件化。模块化是插件化、组件化的基石，如果一个项目没有通过模块化将项目进行职责梳理，那么直接进行插件化、组件化项目的结构只会越弄越糟糕。


## 模块化
模块化我们主要从app framework 与 bundles 两个大方面来进行。app framework的模块通常有这些模块：common、 network、storage、image & graphic 、av(audio & video)、lbs(map & location)、ui component，bundles的模块通常有这些：user、im、code scanner、upgrade等

## 组件化/插件化
组件化的目的是优化构建速度与项目解耦，而插件化不仅拥有组件化的这些能力还能提高启动速度、降低包体积、优化内存。那么如何落地一个项目组件化与插件化，请移步这个项目[bundles-assembler](https://github.com/electrolyteJ/bundles-assembler)，欢迎star与提issue。

## 热修复
tinker & robous

## 动态化
React Native 、小程序、Flutter