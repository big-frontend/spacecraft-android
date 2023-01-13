# Network

标准七层协议
layer|协议
---|---
应用层 | HTTP
表示层| TLS/SSL
会话层| ...
传输层| TCP/UDP
网络层| IP
数据链路层| ...
物理层 | ...

TCP/IP协议
layer | devices|传输数据的基本单位
---|---|---
应用层|应用网关|
传输层|传输网关|数据段、报文段
网络层|路由器|数据分组
数据链路层|网桥、交换机|数据帧
物理层|中继器、集线器|比特流

## TCP协议
- 连接：使用检验和、确认和重试这些来保证可靠传输
- 流控：使用滑动窗口进行流量控制，动态改变窗口大小进行拥塞控制

## HTTP协议
style|serializable
--- |---
soap    |xml
rest(retrofit)    |json(Gson,FastJson,...)
rpc(grpc)     |protobuf(wire,protobuf)

style描述了网络请求的调用方式，serializable描述了端与端之间数据的格式。

Feature | HTTP2|HTTP3
---|---| --- |
Header compression algorithm|HPACK|QPACK
Handshake protocol|TCP + TLS|QUIC
Handshake negotiation|At the certificate stage via ALPN=Application-Layer Protocol Negotiation (ALPN) protocol|After certificate negotiation via “Alt-Svc:” HTTP response header
HTTP scheme|HTTP (not well adopted) / HTTPS|HTTPS
Prioritization|Yes|No, although HTTP/3 streams can have a “PRIORITY” frame to implement it


###  为什么要用retrofit？
- 功能强大：支持各种数据序列化库，支持各种Call转换的库
- 简洁易用：通过简单的注解完成一个rest接口的声明
- 扩展行强：功能模块高度封装，解耦彻底
- 弊端：只支持Okhttp网络请求库，且只支持HTTP协议


# ssl/tls
[Java使用SSLSocket通信](https://my.oschina.net/itblog/blog/651608)


