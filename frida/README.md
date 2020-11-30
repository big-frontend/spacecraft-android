
## inject:
```
./fs -l 0.0.0.0:27042
frida -U -f com.sankuai.meituan --no-pause -l index.js
```
## embedded
frida -U Gadget


## object
objection -d -g com.sankuai.meituan explore
  > memory list modules
  > memory list exports libsigchain.so
  > android heap search instances 类名
  > android hooking list activities/services