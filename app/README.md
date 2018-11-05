
- app/src/androidTest : for android UI test. It needs virtual or real device. (Espresso, UI automator)
- app/src/test : for android independent test.
```
testImplementation : adds dependency for test source set
androidTestImplementation : adds dependency for androidTest source set
```

res/drawable-xxx

px = dp * (dpi / 160)

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

## icon

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