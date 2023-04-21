性能有业务与技术之分，业务性能我们能肉眼感知到的，技术性能就比较底层，一个业务性能的问题产生往往是由多个技术性能排列组合而成，比如卡顿，其产生原因有慢函数、内存泄漏、网络延时等构成。

- 业务性能：`卡顿` 、`启动`、 `渲染` 、`稳定性`、包体积(so、dex、资源)
- 技术性能：`cpu(tracer)`、memory(heap dumper)、io、`网络`、存储、电量、线程

## 参考资料
[干货 | 携程无线APM升级实践](https://mp.weixin.qq.com/s?__biz=MjM5MDI3MjA5MQ==&mid=2697269379&idx=1&sn=1227a77caf29ae0e732d976f3f909540&scene=21#wechat_redirect)

[React Native在美团外卖客户端的实践](https://tech.meituan.com/2019/12/19/meituan-mrn-practice.html)

[React Native Performance](https://reactnative.dev/docs/performance)
