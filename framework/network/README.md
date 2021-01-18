# Network

style|serializable
--- |---
soap    |xml
rest    |json
rpc     |protobuf

style描述了网络请求的调用方式，serializable描述了端与端之间数据的格式。对于serializable的数据选择上面三个任意一个，但是style的选择却很少，把需要的数据都塞到request里面，这样就会显得很臃肿。

## style
rest: retrofit

rpc: grpc

## serializable

protobuf: wire

json: Gson


# transport
[科普：QUIC协议原理分析](https://zhuanlan.zhihu.com/p/32553477)
[QUIC, a multiplexed stream transport over UDP](https://www.chromium.org/quic)
