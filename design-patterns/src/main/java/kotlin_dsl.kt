//
//import java.awt.SystemColor.text
//
//
///**
// * Copyright ® $ 2017
// * All right reserved.
// *
// * @author: hawks.jamesf
// * @since: Aug/02/2018  Thu
// */
//class Dialog {
//    private var textColors: ColorStateList? = null
//    private var text: String = ""
//    private var textSize: Float = 0f
//    private var textStyle: Int = 0
//
//    class Builder {
//
//        var titleHolder: TextView? = null
//
//        constructor(closure: Builder.() -> Unit) {
//            closure.invoke(this)
//        }
//
//        fun title(titleClosure: TextView.() -> Unit) {
////            titleHolder = TextView().applay { titleClosure }
//        }
//
//        fun build(): Dialog {
//            var dialog = Dialog()
//            titleHolder.apply {
//                dialog.textColors = this!!.textColors
//                dialog.text = text.toString()
//                dialog.textSize = textSize
//            }
//            return dialog
//        }
//    }
//}
//
//class Activity4 {
//    fun call() {
//        Dialog.Builder {
//            title {
//                text = "ni hao"
//                textSize = 1f
//            }
//        }
//    }
//
//
//}