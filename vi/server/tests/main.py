from app import create_app
from config.__init__ import TestingConfig

'''
- 127.0.0.1：回环地址。该地址指电脑本身，主要预留测试本机的TCP/IP协议是否正常。只要使用这个地址发送数据，则数据包不会出现在网络传输过程中。
- 10.x.x.x、172.16.x.x～172.31.x.x、192.168.x.x：这些地址被用做内网中。用做私网地址，这些地址不与外网相连。
- 255.255.255.255：广播地址
- 0.0.0.0：这个IP地址在IP数据报中只能用作源IP地址，这发生在当设备启动时但又不知道自己的IP地址情况下。

IPV4中，0.0.0.0地址被用于表示一个无效的，未知的或者不可用的目标。
* 在服务器中，0.0.0.0指的是本机上的所有IPV4地址，如果一个主机有两个IP地址，192.168.1.1 和 10.1.2.1，并且该主机上的一个服务监听的地址是0.0.0.0,那么通过两个ip地址都能够访问该服务。
* 在路由中，0.0.0.0表示的是默认路由，即当路由表中没有找到完全匹配的路由的时候所对应的路由。


用途：
- DHCP分配前，表示本机。
- 用做默认路由，表示任意主机。
- 用做服务端，表示本机的任意IPV4地址。

比如我有一台服务器，一个外网A,一个内网B，如果我绑定的端口指定了0.0.0.0，那么通过内网地址或外网地址都可以访问我的应用。
'''
if __name__ == '__main__':
    # app = create_app(config="settings.yaml")
    app = create_app(config_object=TestingConfig)
    use_debugger = app.debug
    # debug - whether to enable debug mode and catch exceptions
    # use_debugger - whether to use the internal Flask debugger
    # use_reloader - whether to reload and fork the process if modules were changed
    app.run(host='0.0.0.0', port=9000,
            use_debugger=use_debugger, debug=app.debug, use_reloader=use_debugger)
