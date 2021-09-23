## Android's FrameSequence vs. Glide's GifDrawable
相比较Glide的实现的GifDrawable编解码，FrameSequenceDrawable到底哪里好?

| |GifDrawable|FrameSequenceDrawable|
| --- | ---| ---|
gif动图 |支持|支持
webp动图|不支持|支持
集成包大小| 仅有15个文件| 260kb起步


GifDrawable的卡顿：
GifDrawable的动图实现，两帧是串行的，所以如果其中一帧耗时严重就会出现掉帧的问题，再StandardGifDecoder只会保留一帧的数据，
每次获取当前要绘制的帧就会从BitmapPool中获取新的Bitmap，这样就会同时存在两个Bitmap造成内存过高

优化
FrameSequenceDrawable使用两个frontBitmap backBitmap，frontBitmap用于被渲染，而backBitmap用于存放解码数据，在绘制下一帧的时候swap两个Bitmap。


