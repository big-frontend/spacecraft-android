ui 的基本元素
- 动画(页面、元素转场，控件动画)
- 交互(手势)
- 测绘(自定义View)

## 手机屏幕
下面这张表格解释了什么是dpi？

屏幕密度|range| ratio| 常见的resolutions/aspect ratio
---|---|---|---
ldpi    |      ~120dpi   | 0.75dip=1px |
mdpi	|120dpi~160dpi  | 1dip=1px(标准线)    |
hdpi	| 160dpi~240dpi |  1dip=1.5px |
xhdpi	| 240dpi~320dpi | 1dip=2px(2x)    | 720p(1280x720,16:9),standard HD
xxhdpi	| 320dpi~480dpi | 1dip=3px(3x)    | 1080p(1920×1080,16:9),full HD
xxxhdpi	| 480dpi~640dpi |  1dip=4px(4x)   |1440p(2560 x 1440,16:9)
                          xxx|xxx|xxx|4k,2160p(3840x2160,16∶9),UHD

全面屏手机：
- MIX 3:2340x1080,19.5:9
- MIX 2s:1080p(2160x1080,18:9),FullHD+

## Menu Icons/ Action Bar Icons/ Status Bar Icons/ Tab Icons

屏幕密度|图标尺寸
---|---
  ldpi   |18 x 18 px
mdpi|24 x 24 px
hdpi |	32 x 32 px
 xhdpi(2x)|	48 x 48 px
xxhdpi(3x) |xxx
xxxhdpi	|xxx

## Dialog Icons/ List View Icons

屏幕密度|图标尺寸
---|---
ldpi|24 x 24 px
mdpi |	32 x 32 px
hdpi |	48 x 48 px
xhdpi(2x) |	64 x 64 px
xxhdpi(3x) |xxx
xxxhdpi	|xxx

性能优化
1. 尽量使用硬件加速
2. create view的优化(xml 随机读写io时间、解析xml时间、生成对象时间)
    - 使用代码创建：注重性能，修改不频繁的场景
    - 异步创建
    - activity/fragment 之间的view复用：需要清除view的状态
3.measure/layout ：减少ui布局层次，使用ViewStub、Merage 标签；优化layout开销，使用ConstraintLayout；不要重复设置主题背景；
4. ui进阶
- Litho：异步布局、界面扁平化、优化Recyclerview(按照text、image、audio类型缓存)
- flutter：自己的布局+渲染引擎
- RenderThread实现异步动画、RenderScript
