package com.jamesfchen.plugin

import com.jamesfchen.plugin.counter.CounterExtension

//gradle framework
def counterConfig(Closure<CounterExtension> closure){
        def c = CounterExtension()
        closure.setDelegate(c)//委托代理优先
        closure.setResolveStrategy(Closure.DELEGATE_FIRST)//Groovy的闭包有this、owner、delegate三个属性
        return closure
}
//build.gradle
counterConfig {
    seaSrcDirs = true
}