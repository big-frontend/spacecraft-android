package retrofit2;

import java.io.IOException;

import javax.annotation.Nullable;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

/**
 * Copyright ® $ 2021
 * All right reserved.
 *
 * @author jamesfchen
 * @since 9月/28/2021  周二
 */
public final class ExceptionCatchingResponseBody extends ResponseBody {
    private final ResponseBody delegate;
    private final BufferedSource delegateSource;
    @Nullable
    IOException thrownException;

    public ExceptionCatchingResponseBody(ResponseBody delegate) {
        this.delegate = delegate;
        this.delegateSource =
                Okio.buffer(
                        new ForwardingSource(delegate.source()) {
                            @Override
                            public long read(Buffer sink, long byteCount) throws IOException {
                                try {
                                    return super.read(sink, byteCount);
                                } catch (IOException e) {
                                    thrownException = e;
                                    throw e;
                                }
                            }
                        });
    }

    @Override
    public MediaType contentType() {
        return delegate.contentType();
    }

    @Override
    public long contentLength() {
        return delegate.contentLength();
    }

    @Override
    public BufferedSource source() {
        return delegateSource;
    }

    @Override
    public void close() {
        delegate.close();
    }

    public void throwIfCaught() throws IOException {
        if (thrownException != null) {
            throw thrownException;
        }
    }
}
