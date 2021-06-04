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

Feature | HTTP/2|HTTP/3
---|---| --- |
Header compression algorithm|HPACK|QPACK
Handshake protocol|TCP + TLS|iQUIC
Handshake negotiation|At the certificate stage via ALPN=Application-Layer Protocol Negotiation (ALPN) protocol|After certificate negotiation via “Alt-Svc:” HTTP response header
HTTP scheme|HTTP (not well adopted) / HTTPS|HTTPS
Prioritization|Yes|No, although HTTP/3 streams can have a “PRIORITY” frame to implement it

layer | devices|传输数据的基本单位
---|---|---
应用层|应用网关|
传输层|传输网关|数据段、报文段
网络层|路由器|数据分组
数据链路层|网桥、交换机|数据帧
物理层|中继器、集线器|比特流


# ssl/tls
[Java使用SSLSocket通信](https://my.oschina.net/itblog/blog/651608)
