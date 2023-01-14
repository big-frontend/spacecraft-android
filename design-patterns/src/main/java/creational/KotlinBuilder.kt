package creational

import java.awt.SystemColor.text


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/02/2018  Thu
 */
class Dialog(
        var text: String = "",
        var textSize: Float = 0f,
        var textStyle: Int = 0
) {


    class Builder {

        private var text: String = ""
        private var textSize: Float = 0f
        private var textStyle: Int = 0
        fun text(text: String) = apply {
            this.text = text
        }

        fun textSize(textSize: Float) = apply {
            this.textSize = textSize
        }

        fun textStyle(textStyle: Int) = apply {
            this.textStyle = textStyle
        }

        fun build(): Dialog {
            var dialog = Dialog(text,textSize,textStyle)
            return dialog
        }
    }
}

class Activity4 {
    fun call() {
        Dialog.Builder().text("afds")
                .textSize(1f)
                .textStyle(1323)
                .build()
    }


}

