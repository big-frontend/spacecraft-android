# -*- coding: utf-8 -*-
"""元编程：
 type 本身就是元类
1. 元类
2. 装饰器：函数装饰器、类装饰器

__new__ vs. __init__:当需要控制新实例的创建时使用 new，而在需要控制新实例的初始化时则使用 init


 """


class Singleton(type):
    """
    Singleton Metaclass
    """

    _inst = {}

    def __call__(cls, *args, **kwargs):
        if cls not in cls._inst:
            cls._inst[cls] = super(Singleton, cls).__call__(*args)
        return cls._inst[cls]


class PythonSingleton():
    __metaclass__ = Singleton
    pass


print(type(PythonSingleton))
