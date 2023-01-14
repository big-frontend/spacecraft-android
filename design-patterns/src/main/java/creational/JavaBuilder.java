package creational;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/02/2018  Thu
 */

class TextView {
    private int textColor;
    private String text;
    private int textSize;
    private int textStyle;

    TextView(int textColor, String text, int textSize, int textStyle) {
        this.textColor = textColor;
        this.text = text;
        this.textSize = textSize;
        this.textStyle = textStyle;
    }

    static class Builder {
        private int textColor;
        private String text;
        private int textSize;
        private int textStyle;

        public int getTextColor() {
            return textColor;
        }

        public Builder setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public String getText() {
            return text;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public int getTextSize() {
            return textSize;
        }

        public Builder setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public int getTextStyle() {
            return textStyle;
        }

        public Builder setTextStyle(int textStyle) {
            this.textStyle = textStyle;
            return this;
        }

        public TextView build() {
            return new TextView(textColor, text, textSize, textStyle);
        }
    }
}
