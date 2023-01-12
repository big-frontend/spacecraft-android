# 稳定性(Crash & ANR)

应用退出的情况

1. 主动退出，Process.killProcess,exit等
2. 崩溃
3. 系统重启:系统出现异常、断点、用户主动重启等
4. 被系统杀死:被lmk杀掉，从系统任务栏划掉
5. ANR

## 指标
- UV崩溃率=发生崩溃的UV/登录UV
- PV崩溃率/启动崩溃率/重复崩溃率
- UV异常率(包括应用退出的情况3,4,5) = 发生异常退出或崩溃的 UV / 登录 UV

## 监控

|js crasher| desc
|---|---|
ErrorUtils.js|react native提供的捕获类
[Sentry](https://develop.sentry.dev/)|js code,sentry提供server+client采集方案

| java/kotlin crasher| desc
|---|---|
bugly |业界标杆
[xCrash](https://github.com/iqiyi/xCrash)| 
[acra](https://github.com/ACRA/acra)| 捕获前端与收集后端功能完整
Dropbox|Android系统
[Sentry](https://develop.sentry.dev/)| java sentry,sentry提供server+client采集方案

| c/cc crasher| desc
|---|---|
[breakpad](https://github.com/google/breakpad)|权威，跨平台，代码量大
[coffeecatch](https://github.com/xroche/coffeecatch)|实现简介，兼容性问题
[xCrash](https://github.com/iqiyi/xCrash)| 
Dropbox|Android系统
[Sentry](https://develop.sentry.dev/)| cpp sentry,sentry提供server+client采集方案

## 案例

- [字节ANR剖析](https://mp.weixin.qq.com/mp/appmsgalbum?__biz=MzI1MzYzMjE0MQ==&action=getalbum&album_id=1780091311874686979&scene=173&from_msgid=2247488243&from_itemidx=1&count=3&nolastread=1#wechat_redirect)
- [Fix the problems](https://developer.android.com/topic/performance/vitals/anr#fix)

## 参考资料
[得物App Android Crash治理演进](https://juejin.cn/post/7001060315056046117)

[得物App ANR监控平台设计](https://juejin.cn/post/7009297034440081422)

