package com.electrolytej.feeds.widget.download;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import java.util.Objects;

@Keep
public class DownloadKey {
    @NonNull
    public String url;
    public String cardUrl;
    public String signature;

    public DownloadKey(@NonNull String url, String cardUrl, String signature) {
        this.url = url;
        this.cardUrl = cardUrl;
        this.signature = signature;
    }

    @Override
    public int hashCode() {
        if (isNotEmpty(cardUrl) && isNotEmpty(signature)) {
            return Objects.hash(url, cardUrl, signature);
        } else if (isNotEmpty(cardUrl)) {
            return Objects.hash(url, cardUrl);
        } else if (isNotEmpty(signature)) {
            return Objects.hash(url, signature);
        }
        return Objects.hash(url);
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof DownloadKey) {
            DownloadKey other = (DownloadKey) o;
            if (isNotEmpty(cardUrl) && isNotEmpty(signature)) {
                return url.equals(other.url) && cardUrl.equals(other.cardUrl) && signature.equals(other.signature);
            } else if (isNotEmpty(cardUrl)) {
                return url.equals(other.url) && cardUrl.equals(other.cardUrl);
            } else if (isNotEmpty(signature)) {
                return url.equals(other.url) && signature.equals(other.signature);
            }
            return url.equals(other.url);

        }
        return false;
    }

    public static boolean isNotEmpty(CharSequence str) {
        return str != null && str.length() != 0;
    }
}