package com.eastcompeace.lpa.sdk.http;

import anet.channel.request.Request;
import anet.channel.util.HttpConstant;
import com.eastcompeace.lpa.sdk.log.ELog;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.LongCompanionObject;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class LogInterceptor implements Interceptor {
    private String TAG = "okhttp log";
    private final Charset UTF8 = Charset.forName(Request.DEFAULT_CHARSET);

    public Response intercept(Interceptor.Chain chain) throws IOException {
        String str;
        okhttp3.Request request = chain.request();
        RequestBody body = request.body();
        if (body != null) {
            Buffer buffer = new Buffer();
            body.writeTo(buffer);
            Charset charset = this.UTF8;
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(this.UTF8);
            }
            str = buffer.readString(charset);
        } else {
            str = null;
        }
        ELog.d(this.TAG, "发送请求: method：" + request.method() + "\nurl：" + request.url() + "\n请求头：" + request.headers().toString() + "\n请求参数: " + str);
        long nanoTime = System.nanoTime();
        Response proceed = chain.proceed(request);
        long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoTime);
        ResponseBody body2 = proceed.body();
        BufferedSource source = body2.source();
        source.request(LongCompanionObject.MAX_VALUE);
        Buffer buffer2 = source.buffer();
        Charset charset2 = this.UTF8;
        MediaType contentType2 = body2.contentType();
        if (contentType2 != null) {
            try {
                charset2 = contentType2.charset(this.UTF8);
            } catch (UnsupportedCharsetException e) {
                e.printStackTrace();
            }
        }
        ELog.d(this.TAG, "收到响应: code:" + proceed.code() + "\n请求url：" + proceed.request().url() + "\n请求body：" + str + "\nResponse: " + buffer2.clone().readString(charset2) + "\n耗时: " + millis + "ms");
        return proceed.newBuilder().removeHeader("pragma").header(HttpConstant.CACHE_CONTROL, "max-age=10").header(HttpConstant.CACHE_CONTROL, "max-stale=30").build();
    }
}
