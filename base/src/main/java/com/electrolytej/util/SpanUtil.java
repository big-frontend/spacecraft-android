package com.electrolytej.util;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @since: Nov/01/2018  Thu
 * <p>
 * 首先得知道什么是Span？简单来说它是一种标记(mark up),比如整段字符串中出现部分高亮(@功能)，这部分高亮就可以理解为是一种标记。
 * 它们的颜色不同于其他字符；可点击；大小不同于其他的字符，简单来说他足够骚，吸引眼球。用它可以实现markdown语法
 * <p>
 * 能用来显示span的字符串有三种
 * <p>
 * Class	            Mutable text	Mutable markup	Data structure
 * SpannedString	        No	            No	        Linear array
 * SpannableString	        No	            Yes	        Linear array
 * SpannableStringBuilder	Yes	            Yes	        Interval tree
 * <p>
 * String        线程安全
 * StringBuilder 线程不安全
 * StringBuffer 线程安全
 * <p>
 * span有20种（在android.text.style包下）：
 * #### text appearance affecting spans or text metrics affecting spans(character affection spans):继承CharacterStyle
 * <p>
 * 1.主要是appearance这块
 * - BackgroundColorSpan,背景色
 * - ForegroundColorSpan,前景色
 * - MaskFilterSpan,
 * - EmbossMaskFilter 浮雕效果
 * - BlurMaskFilter 模糊字体效果
 * - StrikethroughSpan, 删除线
 * - UnderlineSpan 下划线
 * - TypefaceSpan,字体样式family，楷书，行书
 * - StyleSpan, Typeface.BOLD/Typeface.ITALIC/Typeface.BOLD_ITALIC/Typeface.NORMAL
 * <p>
 * 2.主要是处理metrics这块
 * - MetricAffectingSpan,
 * - RelativeSizeSpan
 * - AbsoluteSizeSpan,
 * - LocaleSpan, 本地化字符
 * - SuperscriptSpan, 上标
 * - SubscriptSpan, 下标
 * - ScaleXSpan,字符横向拉伸
 * <p>
 * - TextAppearanceSpan,包括color、size、style、typeface
 * 3.其他
 * - ReplacementSpan,
 * - DynamicDrawableSpan,
 * - ImageSpan 支持四种Bitmap/Drawable/Uri/resourceId动态加载图片的方式
 * - EmojiSpan /TypefaceEmojiSpan
 * <p>
 * - SuggestionSpan,
 * <p>
 * - ClickableSpan, 可点击
 * - URLSpan
 * - TextLinkSpan,
 * <p>
 * #### paragraph affecting spans：继承ParagraphStyle
 * - AlignmentSpan,
 * - AlignmentSpan.Standard,文字对其方式
 * - TabStopSpan,
 * - TabStopSpan.Standard,每行距离左边的距离
 * - LeadingMarginSpan.LeadingMarginSpan2,
 * - LeadingMarginSpan,
 * - LeadingMarginSpan.Standard, 首行缩进
 * - DrawableMarginSpan,插入Drawable对象
 * - IconMarginSpan,插入Bitmap对象
 * - LineHeightSpan,
 * - LineHeightSpan.WithDensity,
 * QuoteSpan,在行首标识段落为引用
 * <p>
 * - BulletSpan,每行的小原点
 */
public class SpanUtil {
}
