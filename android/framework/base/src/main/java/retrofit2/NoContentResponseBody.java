package retrofit2;

import javax.annotation.Nullable;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;

/**
 * Copyright ® $ 2021
 * All right reserved.
 *
 * @author jamesfchen
 * @since 9月/28/2021  周二
 */
public final class NoContentResponseBody extends ResponseBody {
    private final @Nullable
    MediaType contentType;
    private final long contentLength;

    public NoContentResponseBody(@Nullable MediaType contentType, long contentLength) {
        this.contentType = contentType;
        this.contentLength = contentLength;
    }

    @Override
    public MediaType contentType() {
        return contentType;
    }

    @Override
    public long contentLength() {
        return contentLength;
    }

    @Override
    public BufferedSource source() {
        throw new IllegalStateException("Cannot read raw response body of a converted body.");
    }
}
