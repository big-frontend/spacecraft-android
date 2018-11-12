package com.hawksjamesf.common.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineHeightSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ReplacementSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.text.style.UpdateAppearance;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntDef;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

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
 * text appearance affecting spans or text metrics affecting spans(character affection spans):继承CharacterStyle
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
 * - StyleSpan,
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
 * - EmojiSpan
 * -TypefaceEmojiSpan
 * <p>
 * - SuggestionSpan,
 * <p>
 * - ClickableSpan, 可点击
 * - URLSpan
 * - TextLinkSpan,
 * <p>
 * paragraph affecting spans：继承ParagraphStyle
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
    private static final int COLOR_DEFAULT = 0xFEFFFFFF;
    public static final int ALIGN_BOTTOM = 0;
    public static final int ALIGN_BASELINE = 1;
    public static final int ALIGN_CENTER = 2;
    public static final int ALIGN_TOP = 3;

    @IntDef({ALIGN_BOTTOM, ALIGN_BASELINE, ALIGN_CENTER, ALIGN_TOP})
    @Retention(value = RetentionPolicy.SOURCE)
    private @interface Align {
    }


    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private CharSequence mText;
    private int flag;
    private int foregroundColor;
    private int backgroundColor;
    private int lineHeight;
    private int alignLine;
    private int quoteColor;
    private int stripeWidth;
    private int quoteGapWidth;
    private int first;
    private int rest;
    private int bulletColor;
    private int bulletRadius;
    private int bulletGapWidth;
    private int fontSize;
    private boolean fontSizeIsDp;
    private float proportion;
    private float xProportion;
    private boolean isStrikeThrough;
    private boolean isUnderline;
    private boolean isSuperscript;
    private boolean isSubscript;
    private boolean isBold;
    private boolean isItalic;
    private boolean isBoldItalic;
    private String fontFamily;
    private Typeface typeface;
    private Layout.Alignment alignment;
    private ClickableSpan clickSpan;
    private String url;
    private float blurRadius;
    private BlurMaskFilter.Blur style;
    private Shader shader;
    private float shadowRadius;
    private float shadowDx;
    private float shadowDy;
    private int shadowColor;
    private Object[] spans;

    private Bitmap imageBitmap;
    private Drawable imageDrawable;
    private Uri imageUri;
    private int imageResourceId;
    private int alignImage;

    private int spaceSize;
    private int spaceColor;

    private SpannableStringBuilder mBuilder;

    private int mType;
    private static final int TYPE_CHARSEQUENCE = 0;
    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_SPACE = 2;

    @IntDef({TYPE_CHARSEQUENCE, TYPE_IMAGE, TYPE_SPACE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public SpanUtil() {
        mBuilder = new SpannableStringBuilder();
        mText = "";
        setDefault();
    }

    private void setDefault() {
        flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
        foregroundColor = COLOR_DEFAULT;
        backgroundColor = COLOR_DEFAULT;
        lineHeight = -1;
        quoteColor = COLOR_DEFAULT;
        first = -1;
        bulletColor = COLOR_DEFAULT;
        fontSize = -1;
        proportion = -1;
        xProportion = -1;
        isStrikeThrough = false;
        isUnderline = false;
        isSuperscript = false;
        isSubscript = false;
        isBold = false;
        isItalic = false;
        isBoldItalic = false;
        fontFamily = null;
        typeface = null;
        alignment = null;
        clickSpan = null;
        url = null;
        blurRadius = -1;
        shader = null;
        shadowRadius = -1;
        spans = null;

        imageBitmap = null;
        imageDrawable = null;
        imageUri = null;
        imageResourceId = -1;

        spaceSize = -1;
    }

    public SpanUtil setFlag(final int flag) {
        this.flag = flag;
        return this;
    }

    public SpanUtil setForegroundColor(@ColorInt final int color) {
        this.foregroundColor = color;
        return this;
    }

    public SpanUtil setBackgroundColor(@ColorInt final int color) {
        this.backgroundColor = color;
        return this;
    }

    public SpanUtil setLineHeight(@IntRange(from = 0) final int lineHeight) {
        return setLineHeight(this.lineHeight = lineHeight, ALIGN_CENTER);
    }

    public SpanUtil setLineHeight(@IntRange(from = 0) final int lineHeight, @Align final int align) {
        this.lineHeight = lineHeight;
        this.alignLine = align;
        return this;
    }

    public SpanUtil setQuoteColor(@ColorInt final int color) {
        return setQuoteColor(color, 2, 2);
    }

    public SpanUtil setQuoteColor(@ColorInt final int color,
                                  @IntRange(from = 1) final int stripeWidth,
                                  @IntRange(from = 0) final int gapWidth) {
        this.quoteColor = color;
        this.stripeWidth = stripeWidth;
        this.quoteGapWidth = gapWidth;
        return this;
    }

    public SpanUtil setLeadingMargin(@IntRange(from = 0) final int first,
                                     @IntRange(from = 0) final int rest) {
        this.first = first;
        this.rest = rest;
        return this;
    }


    public SpanUtil setBullet(@IntRange(from = 0) final int gapWidth) {
        return setBullet(0, 3, gapWidth);
    }

    public SpanUtil setBullet(@ColorInt final int color,
                              @IntRange(from = 0) final int radius,
                              @IntRange(from = 0) final int gapWidth) {

        this.bulletColor = color;
        this.bulletRadius = radius;
        this.bulletGapWidth = gapWidth;
        return this;
    }

    public SpanUtil setFontSize(@IntRange(from = 0) final int size) {
        return setFontSize(size, false);
    }

    public SpanUtil setFontSize(@IntRange(from = 0) final int size,
                                final boolean isDp) {
        this.fontSize = size;
        this.fontSizeIsDp = isDp;
        return this;
    }

    public SpanUtil setFontProportion(final float proportion) {
        this.proportion = proportion;
        return this;
    }

    public SpanUtil setFontXProportion(final float proportion) {
        this.xProportion = proportion;
        return this;
    }

    public SpanUtil setStrikethrough() {
        this.isSubscript = true;
        return this;
    }

    public SpanUtil setUnderline() {
        this.isUnderline = true;
        return this;
    }

    public SpanUtil setSuperscript() {
        this.isSuperscript = true;
        return this;
    }

    public SpanUtil setSubscript() {
        this.isSubscript = true;
        return this;
    }

    public SpanUtil setBold() {
        this.isBold = true;
        return this;
    }

    public SpanUtil setItalic() {
        this.isItalic = true;
        return this;
    }

    public SpanUtil setBoldItalic() {
        this.isBoldItalic = true;
        return this;
    }

    public SpanUtil setTypeface(Typeface typeface) {
        this.typeface = typeface;
        return this;
    }

    public SpanUtil setAlign(final Layout.Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public SpanUtil setClickSpan(ClickableSpan clickSpan) {
        this.clickSpan = clickSpan;
        return this;
    }

    public SpanUtil setUrl(String url) {
        this.url = url;
        return this;
    }

    public SpanUtil setBlur(@FloatRange(from = 0, fromInclusive = false) float blurRadius, final BlurMaskFilter.Blur style) {
        this.blurRadius = blurRadius;
        this.style = style;
        return this;
    }

    public SpanUtil setShadow(@FloatRange(from = 0, fromInclusive = false) float shadowRadius,
                              final float dx,
                              final float dy,
                              final int shadowColor) {
        this.shadowRadius = shadowRadius;
        this.shadowDx = dx;
        this.shadowDy = dy;
        this.shadowColor = shadowColor;
        return this;
    }

    public SpanUtil setSpans(final Object... spans) {
        if (spans.length > 0) {
            this.spans = spans;
        }
        return this;
    }

    public SpanUtil append(final CharSequence text) {
        apply(TYPE_CHARSEQUENCE);
        mText = text;
        return this;
    }

    public SpanUtil appendLine() {
        apply(TYPE_CHARSEQUENCE);
        mText = LINE_SEPARATOR;
        return this;
    }

    public SpanUtil appendLine(final CharSequence text) {
        apply(TYPE_CHARSEQUENCE);
        mText = text + LINE_SEPARATOR;
        return this;
    }

    public SpanUtil appendImage(final Bitmap bitmap) {
        return appendImage(bitmap, ALIGN_BOTTOM);
    }

    public SpanUtil appendImage(final Bitmap bitmap, @Align final int align) {
        apply(TYPE_IMAGE);
        this.imageBitmap = bitmap;
        this.alignImage = align;
        return this;
    }

    public SpanUtil appendImage(final Drawable drawable) {
        return appendImage(drawable, ALIGN_BOTTOM);
    }

    public SpanUtil appendImage(final Drawable drawable, @Align final int align) {
        apply(TYPE_IMAGE);
        this.imageDrawable = drawable;
        this.alignImage = align;
        return this;
    }

    public SpanUtil appendImage(final Uri uri) {
        return appendImage(uri, ALIGN_BOTTOM);
    }

    public SpanUtil appendImage(final Uri uri, @Align final int align) {
        apply(TYPE_IMAGE);
        this.imageUri = uri;
        this.alignImage = align;
        return this;
    }

    public SpanUtil appendImage(@DrawableRes final int resourceId) {
        return appendImage(resourceId, ALIGN_BOTTOM);
    }

    public SpanUtil appendImage(@DrawableRes final int resourceId, @Align final int align) {
        append(Character.toString((char) 0));
        apply(TYPE_IMAGE);
        this.imageResourceId = resourceId;
        this.alignImage = align;
        return this;
    }

    public SpanUtil appendSpace(@IntRange(from = 0) final int size) {
        return appendSpace(size, Color.TRANSPARENT);
    }

    public SpanUtil appendSpace(@IntRange(from = 0) final int size, @ColorInt final int color) {
        apply(TYPE_SPACE);
        spaceSize = size;
        spaceColor = color;
        return this;
    }

    public void apply(@Type final int type) {
        applyLast();
        mType = type;
    }


    private void applyLast() {
        if (mType == TYPE_CHARSEQUENCE) {
            updateCharSequence();
        } else if (mType == TYPE_IMAGE) {
            updateImage();
        } else if (mType == TYPE_SPACE) {
            updateSpace();
        }
        setDefault();
    }

    public SpannableStringBuilder create() {
        applyLast();
        return mBuilder;
    }

    private void updateCharSequence() {
        if (mText.length() == 0) return;
        int start = mBuilder.length();
        mBuilder.append(mText);
        int end = mBuilder.length();
        if (foregroundColor != COLOR_DEFAULT)
            mBuilder.setSpan(new ForegroundColorSpan(foregroundColor), start, end, flag);
        if (backgroundColor != COLOR_DEFAULT)
            mBuilder.setSpan(new BackgroundColorSpan(backgroundColor), start, end, flag);
        if (first != -1)
            mBuilder.setSpan(new LeadingMarginSpan.Standard(first, rest), start, end, flag);
        if (quoteColor != COLOR_DEFAULT)
            mBuilder.setSpan(new CustomQuoteSpan(quoteColor, stripeWidth, quoteGapWidth), start, end, flag);
        if (bulletColor != COLOR_DEFAULT)
            mBuilder.setSpan(new CustomBulletSpan(bulletColor, bulletRadius, bulletGapWidth), start, end, flag);
        if (fontSize != -1)
            mBuilder.setSpan(new AbsoluteSizeSpan(fontSize, fontSizeIsDp), start, end, flag);
        if (proportion != -1) mBuilder.setSpan(new RelativeSizeSpan(proportion), start, end, flag);
        if (xProportion != -1) mBuilder.setSpan(new ScaleXSpan(xProportion), start, end, flag);
        if (lineHeight != -1)
            mBuilder.setSpan(new CustomLineHeightSpan(lineHeight, alignLine), start, end, flag);
        if (isStrikeThrough) mBuilder.setSpan(new StrikethroughSpan(), start, end, flag);
        if (isUnderline) mBuilder.setSpan(new UnderlineSpan(), start, end, flag);
        if (isSuperscript) mBuilder.setSpan(new SuperscriptSpan(), start, end, flag);
        if (isSubscript) mBuilder.setSpan(new SubscriptSpan(), start, end, flag);
        if (isBold) mBuilder.setSpan(new StyleSpan(Typeface.BOLD), start, end, flag);
        if (isItalic) mBuilder.setSpan(new StyleSpan(Typeface.ITALIC), start, end, flag);
        if (isBoldItalic) mBuilder.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), start, end, flag);
        if (fontFamily != null) mBuilder.setSpan(new TypefaceSpan(fontFamily), start, end, flag);
        if (typeface != null) mBuilder.setSpan(new CustomTypefaceSpan(typeface), start, end, flag);
        if (alignment != null)
            mBuilder.setSpan(new AlignmentSpan.Standard(alignment), start, end, flag);
        if (clickSpan != null) mBuilder.setSpan(clickSpan, start, end, flag);
        if (url != null) mBuilder.setSpan(new URLSpan(url), start, end, flag);
        if (blurRadius != -1)
            mBuilder.setSpan(new MaskFilterSpan(new BlurMaskFilter(blurRadius, style)), start, end, flag);
        if (shader != null) mBuilder.setSpan(new ShaderSpan(shader), start, end, flag);
        if (shadowRadius != -1)
            mBuilder.setSpan(new ShadowSpan(shadowRadius, shadowDx, shadowDy, shadowColor), start, end, flag);
        if (spans != null) {
            for (Object span :
                    spans) {
                mBuilder.setSpan(span, start, end, flag);
            }
        }


    }

    class CustomQuoteSpan implements LeadingMarginSpan {
        private final int color;
        private final int stripeWidth;
        private final int gapWidth;

        public CustomQuoteSpan(@ColorInt int color,
                               @IntRange(from = 0) int stripeWidth,
                               @IntRange(from = 0) int gapWidth) {
            this.color = color;
            this.stripeWidth = stripeWidth;
            this.gapWidth = gapWidth;
        }

        @Override
        public int getLeadingMargin(boolean b) {
            return stripeWidth + gapWidth;
        }

        @Override
        public void drawLeadingMargin(Canvas canvas, Paint paint, final int x, final int dir,
                                      final int top, final int baseline, final int bottom,
                                      final CharSequence text, final int start, final int end,
                                      final boolean first, final Layout layout) {
            Paint.Style style = paint.getStyle();
            int color = paint.getColor();

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(this.color);

            canvas.drawRect(x, top, x + dir * stripeWidth, bottom, paint);

            paint.setStyle(style);
            paint.setColor(color);


        }
    }

    class CustomBulletSpan implements LeadingMarginSpan {
        private final int color;
        private final int radius;
        private final int gapWidth;
        private Path bulletPath;

        public CustomBulletSpan(int color, int radius, int gapWidth) {
            this.color = color;
            this.radius = radius;
            this.gapWidth = gapWidth;
        }

        @Override
        public int getLeadingMargin(boolean b) {
            return 2 * radius + gapWidth;
        }

        @Override
        public void drawLeadingMargin(final Canvas c, final Paint p, final int x, final int dir,
                                      final int top, final int baseline, final int bottom,
                                      final CharSequence text, final int start, final int end,
                                      final boolean first, final Layout l) {
            if (((Spanned) text).getSpanStart(this) == start) {
                Paint.Style style = p.getStyle();
                int color = p.getColor();
                p.setColor(this.color);
                p.setStyle(Paint.Style.FILL);
                if (c.isHardwareAccelerated()) {
                    if (bulletPath == null) {
                        bulletPath = new Path();
                        bulletPath.addCircle(0f, 0f, radius, Path.Direction.CW);
                    }
                    c.save();
                    c.translate(x + dir * radius, (top + bottom) / 2.0f);
                    c.drawPath(bulletPath, p);
                    c.restore();
                } else {
                    c.drawCircle(x + dir * radius, (top + bottom) / 2.0f, radius, p);
                }
                p.setColor(color);
                p.setStyle(style);
            }


        }
    }

    class CustomLineHeightSpan extends CharacterStyle implements LineHeightSpan {
        private final int height;
        private static final int ALIGN_CENTER = 2;
        private static final int ALIGN_TOP = 3;
        private int verticalAlignment;

        CustomLineHeightSpan(int height, int verticalAlignment) {
            this.height = height;
            this.verticalAlignment = verticalAlignment;
        }

        @Override
        public void chooseHeight(final CharSequence text, final int start, final int end,
                                 final int spanstartv, final int v, final Paint.FontMetricsInt fm) {
            //Baseline基线
            //ascent   文字最顶部
            // descent 文字最底部

            //FontMetrics:ascent/descent  bottom/top leading
            int fontSpace = fm.descent - fm.ascent;
            int need = height - (v + fontSpace - spanstartv);
            if (verticalAlignment == ALIGN_TOP) {
                //所有的字体位于top,
                fm.descent += need;
            } else if (verticalAlignment == ALIGN_CENTER) {
                fm.descent += need / 2;
                fm.ascent -= need / 2;
            } else {
                fm.ascent -= need;
            }

            need = height - (v + fm.bottom - fm.top - spanstartv);
            if (verticalAlignment == ALIGN_TOP) {
                //所有的字体位于top,
                fm.top += need;
            } else if (verticalAlignment == ALIGN_CENTER) {
                fm.bottom += need / 2;
                fm.top -= need / 2;
            } else {
                fm.top -= need;
            }
        }

        @Override
        public void updateDrawState(TextPaint textPaint) {

        }
    }

    class CustomTypefaceSpan extends TypefaceSpan {
        private final Typeface newType;

        public CustomTypefaceSpan(@NonNull Typeface newType) {
            super("");
            this.newType = newType;
        }

        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            apply(ds, newType);
        }

        @Override
        public void updateMeasureState(@NonNull TextPaint paint) {
            super.updateMeasureState(paint);
        }

        private void apply(TextPaint tp, Typeface tf) {
            int oldStyle;
            Typeface old = tp.getTypeface();
            if (old == null) {
                oldStyle = 0;
            } else {
                oldStyle = old.getStyle();
            }
            int fake = oldStyle & ~tf.getStyle();
            if ((fake & Typeface.BOLD) != 0) tp.setFakeBoldText(true);
            if ((fake & Typeface.ITALIC) != 0) tp.setTextSkewX(-0.25f);
            tp.setTypeface(tf);
        }


    }

    class ShaderSpan extends CharacterStyle implements UpdateAppearance {
        private Shader mShader;

        public ShaderSpan(Shader mShader) {
            this.mShader = mShader;
        }

        @Override
        public void updateDrawState(TextPaint textPaint) {
            textPaint.setShader(shader);

        }
    }

    class ShadowSpan extends CharacterStyle implements UpdateAppearance {
        private float radius;
        private float dx, dy;
        private int shadowColor;

        public ShadowSpan(float radius, float dx, float dy, int shadowColor) {
            this.radius = radius;
            this.dx = dx;
            this.dy = dy;
            this.shadowColor = shadowColor;
        }

        @Override
        public void updateDrawState(TextPaint textPaint) {
            textPaint.setShadowLayer(radius, dx, dy, shadowColor);

        }
    }

    private void updateImage() {
        int start = mBuilder.length();
        mBuilder.append("<image>");
        int end = start + 5;
        if (imageBitmap != null)
            mBuilder.setSpan(new CustomImageSpan(imageBitmap, alignImage), start, end, flag);
        if (imageDrawable != null)
            mBuilder.setSpan(new CustomImageSpan(imageDrawable, alignImage), start, end, flag);
        if (imageUri != null)
            mBuilder.setSpan(new CustomImageSpan(imageUri, alignImage), start, end, flag);
        if (imageResourceId != -1)
            mBuilder.setSpan(new CustomImageSpan(imageResourceId, alignImage), start, end, flag);

    }

    class CustomImageSpan extends CustomDynamicDrawableSpan {
        private Drawable mDrawable;
        private Uri mContentUri;
        private int mResourceId;

        private CustomImageSpan(final Bitmap b, final int verticalAlignment) {
            super(verticalAlignment);
            mDrawable = new BitmapDrawable(Util.getApp().getResources(), b);
            mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        }

        private CustomImageSpan(final Drawable d, final int verticalAlignment) {
            super(verticalAlignment);
            mDrawable = d;
            mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        }

        private CustomImageSpan(final Uri uri, final int verticalAlignment) {
            super(verticalAlignment);
            mContentUri = uri;
        }

        private CustomImageSpan(@DrawableRes final int resourceId, final int verticalAlignment) {
            super(verticalAlignment);
            mResourceId = resourceId;
        }


        @Override
        public Drawable getDrawable() {
            Drawable drawable = null;
            if (mDrawable != null) {
                drawable = mDrawable;
            } else if (mContentUri != null) {
                Bitmap bitmap;
                try {
                    InputStream is = Util.getApp().getContentResolver().openInputStream(mContentUri);
                    bitmap = BitmapFactory.decodeStream(is);
                    drawable = new BitmapDrawable(Util.getApp().getResources(), bitmap);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    if (is != null) {
                        is.close();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                drawable = ContextCompat.getDrawable(Util.getApp(), mResourceId);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            }
            return drawable;
        }
    }

    abstract class CustomDynamicDrawableSpan extends ReplacementSpan {
        public final static int ALIGN_BOTTOM = 0;
        public final static int ALIGN_BASELINE = 1;
        public final static int ALIGN_CENTER = 2;
        public final static int ALIGN_TOP = 3;
        private final int mVerticalAlignment;
        private WeakReference<Drawable> mDrawableRef;


        private CustomDynamicDrawableSpan() {
            mVerticalAlignment = ALIGN_BOTTOM;
        }

        public CustomDynamicDrawableSpan(int mVerticalAlignment) {
            this.mVerticalAlignment = mVerticalAlignment;
        }

        public abstract Drawable getDrawable();

        @Override
        public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
            Drawable d = getDrawable();
            Rect bounds = d.getBounds();
            if (fm != null) {
                int lineHeight = fm.bottom - fm.top;
                if (lineHeight < bounds.height()) {
                    switch (mVerticalAlignment) {
                        case ALIGN_TOP: {
                            fm.bottom = bounds.height() + fm.top;
                            break;
                        }
                        case ALIGN_CENTER: {
                            fm.top = -bounds.height() / 2 - lineHeight / 4;
                            fm.bottom = bounds.height() / 2 - lineHeight / 4;
                        }
                        default: {
                            fm.top = fm.bottom - bounds.height();
                        }
                    }

                    fm.ascent = fm.top;
                    fm.descent = fm.bottom;

                }
            }
            return bounds.right;
        }

        @Override
        public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
            Drawable d = getCacheDrawable();
            Rect bounds = d.getBounds();
            canvas.save();
            float transY;
            int lineHeight = bottom - top;
            if (bounds.height() < lineHeight) {
                if (mVerticalAlignment == ALIGN_TOP) {
                    transY = top;
                } else if (mVerticalAlignment == ALIGN_CENTER) {
                    // =lineHeight/2-bounds.height()/2+top
                    // =(bottom-top-bounds.height())/2 +top
                    // =(bottom + top - bounds.height()) / 2
                    transY = (bottom + top - bounds.height()) / 2;
                } else if (mVerticalAlignment == ALIGN_BASELINE) {
                    transY = y - bounds.height();
                } else {
                    transY = bottom - bounds.height();
                }
                canvas.translate(x, transY);
            } else {
                canvas.translate(x, top);
            }
            d.draw(canvas);
            canvas.restore();

        }

        private Drawable getCacheDrawable() {
            WeakReference<Drawable> reference = mDrawableRef;
            Drawable d = null;
            if (reference != null) {
                d = reference.get();
            }
            if (d == null) {
                d = getDrawable();
                mDrawableRef = new WeakReference<>(d);
            }

            return d;
        }
    }

    private void updateSpace() {
        int start = mBuilder.length();
        mBuilder.append("< >");
        int end = start + 3;
        mBuilder.setSpan(new SpaceSpan(spaceSize, spaceColor), start, end, flag);
    }

    class SpaceSpan extends ReplacementSpan {
        private final int width;
        private final int color;

        public SpaceSpan(int width, int color) {
            this.width = width;
            this.color = color;
        }

        public SpaceSpan(int width) {
            this(width, Color.TRANSPARENT);
        }

        @Override
        public int getSize(@NonNull final Paint paint, final CharSequence text,
                           @IntRange(from = 0) final int start,
                           @IntRange(from = 0) final int end,
                           @Nullable final Paint.FontMetricsInt fm) {
            return width;
        }

        @Override
        public void draw(@NonNull final Canvas canvas, final CharSequence text,
                         @IntRange(from = 0) final int start,
                         @IntRange(from = 0) final int end,
                         final float x, final int top, final int y, final int bottom,
                         @NonNull final Paint paint) {
            Paint.Style style = paint.getStyle();
            int color = paint.getColor();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(color);
            canvas.drawRect(x,top,x+width,bottom,paint);
            paint.setColor(color);
            paint.setStyle(style);

        }
    }
}
