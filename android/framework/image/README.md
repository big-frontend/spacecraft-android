## glide 内存申请策略：
当设备为low ram时，glide池子最大可取memoryClassBytes*0.33，默认arrayPool大小为2m；反之取memoryClassBytes*0.4，arrayPool取4m
如果glide的可用size (maxSize - arrayPoolSize)小于bitmapPoolSize + lruResourceCacheSize，那么就会按照比例缩小bitmapPoolSize与LRUResourceCacheSize




