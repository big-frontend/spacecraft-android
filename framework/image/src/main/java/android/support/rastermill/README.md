## Android's FrameSequence vs. Glide's GifDrawable
相比较Glide的实现的GifDrawable编解码，FrameSequenceDrawable到底哪里好?

| |GifDrawable|FrameSequenceDrawable|
| --- | ---| ---|
gif动图 |支持|支持
webp动图|不支持|不支持
集成包大小| 仅有15个文件| 260kb起步
