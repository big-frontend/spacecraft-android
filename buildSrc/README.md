工作内容：编译与运行是两个都要负责的内容

插件化、组件化的实现涉及了编译时与运行时的知识

[aspectj](https://www.eclipse.org/aspectj/)
[asm](https://asm.ow2.io/index.html)
[ReDex](https://github.com/facebook/redex)


[Android字节码插桩](https://www.daimajiaoliu.com/daima/4795c92d31003fc)
[Jvm系列3—字节码指令](http://gityuan.com/2015/10/24/jvm-bytecode-grammar/)
[Advanced Java Bytecode ](https://www.jrebel.com/blog/java-bytecode-tutorial)
[bytecode with asm](https://courses.cs.ut.ee/MTAT.05.085/2016_spring/uploads/Main/Generating_bytecode.pdf)
[不得不学之「 ASM 」④ 加密字符串原理](https://www.yuque.com/mr.s/hs39hv/yrzlp5?language=zh-cn)
[JVM基础知识和ASM修改字节码](https://blog.csdn.net/sweatOtt/article/details/88114002)
[Introduction to Java Bytecode](https://dzone.com/articles/introduction-to-java-bytecode)
[Java MethodVisitor.visitTypeInsn方法代码示例](https://vimsky.com/examples/detail/java-method-org.objectweb.asm.MethodVisitor.visitTypeInsn.html)

插桩(instrumentation):
源代码插桩 Source Code Instrumentation(SCI)：通过asm在编译期间插入字节码
二进制插桩（Binary Instrumentation）
 - 静态二进制插桩[Static Binary Instrumentation(SBI)]:插入额外的字节码，然后利用重打包技术
 - 动态二进制插桩[Dynamic Binary Instrumentation(DBI)]：hook需要修改的函数，在运行期间将本应该执行的函数指向篡改之后的函数
 