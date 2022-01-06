interface DefaultConfig {
    var applicationId: String
    var minSdk: Int
}


fun defaultConfig(action: DefaultConfig.() -> Unit) {

}

fun main(args: Array<String>) {
//    val
    defaultConfig {
        applicationId = "com.jamesfchen"
    }
}