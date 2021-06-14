class ModuleExtension {
    List<String> srcDirs
}
//gradle framework
def moduleConfig(Closure<ModuleExtension> closure/*从build.gradle获取*/){
        def c = new ModuleExtension()//从project.extensions.create获取
        closure.setDelegate(c)//委托代理优先
        closure.setResolveStrategy(Closure.DELEGATE_FIRST)//Groovy的闭包有this、owner、delegate三个属性
        return closure
}
//build.gradle
moduleConfig {
    srcDirs = []
}