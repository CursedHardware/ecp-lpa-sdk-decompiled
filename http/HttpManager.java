package com.eastcompeace.lpa.sdk.http;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.eastcompeace.lpa.sdk.language.LanguageConfig;
import com.eastcompeace.lpa.sdk.log.ELog;
import com.eastcompeace.lpa.sdk.utils.StringUtils;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpManager {
    /* access modifiers changed from: private */
    public static String GET = "GET";
    /* access modifiers changed from: private */
    public static String POST = "POST";
    private static final String TAG = "HttpManager";
    private static volatile HttpManager mHttpManager;
    /* access modifiers changed from: private */
    public static Request request;
    private OkHttpClient client;
    private boolean isMainThread;
    /* access modifiers changed from: private */
    public boolean isSync;
    private int requestType;

    public HttpManager() {
        init();
    }

    public OkHttpClient getClient() {
        return this.client;
    }

    public static HttpManager getInstance() {
        if (mHttpManager == null) {
            synchronized (HttpManager.class) {
                if (mHttpManager == null) {
                    mHttpManager = new HttpManager();
                }
            }
        }
        return mHttpManager;
    }

    private void init() {
        LogInterceptor logInterceptor = new LogInterceptor();
        OkHttpClient.Builder hostnameVerifier = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS).retryOnConnectionFailure(true).sslSocketFactory(MySSLSocketClient.getSSLSocketFactory(), MySSLSocketClient.X509).hostnameVerifier(MySSLSocketClient.getHostnameVerifier());
        hostnameVerifier.addInterceptor(logInterceptor);
        this.client = hostnameVerifier.build();
    }

    public Response syncExecuteForResponse() throws IOException {
        return this.client.newCall(request).execute();
    }

    public JSONObject syncExecute() throws IOException, HttpExcepsion {
        try {
            Response execute = this.client.newCall(request).execute();
            if (execute.isSuccessful()) {
                if (204 == execute.code()) {
                    return null;
                }
                if (200 == execute.code()) {
                    String string = execute.body().string();
                    if (!StringUtils.isEmpty(string)) {
                        return JSONObject.parseObject(string);
                    }
                    throw new HttpExcepsion(execute.code(), "response is null");
                }
            }
            throw new HttpExcepsion(execute.code(), execute.code() + "");
        } catch (IOException e) {
            String message = e.getMessage();
            if (message.contains("No address associated with hostname")) {
                String replace = message.replace("No address associated with hostname", LanguageConfig.getInstance().noAddressAssociatedWithHostname());
                if (replace.contains("Unable to resolve host")) {
                    replace = replace.replace("Unable to resolve host", LanguageConfig.getInstance().UnableToResolveHost());
                }
                throw new UnknownHostException(replace);
            }
            throw e;
        } catch (HttpExcepsion e2) {
            throw e2;
        } catch (JSONException e3) {
            throw e3;
        } catch (Exception e4) {
            throw new HttpExcepsion(e4.getMessage());
        }
    }

    public void asyncExecute(final CallBack<JSONObject> callBack) {
        this.client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException iOException) {
                String message = iOException.getMessage();
                if (message.contains("No address associated with hostname")) {
                    String replace = message.replace("No address associated with hostname", LanguageConfig.getInstance().noAddressAssociatedWithHostname());
                    if (replace.contains("Unable to resolve host")) {
                        replace = replace.replace("Unable to resolve host", LanguageConfig.getInstance().UnableToResolveHost());
                    }
                    callBack.onError(new UnknownHostException(replace));
                    return;
                }
                callBack.onError(iOException);
            }

            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful() || 200 != response.code()) {
                    callBack.onError(new HttpExcepsion(response.code(), response.code() + ""));
                    return;
                }
                try {
                    JSONObject parseObject = JSONObject.parseObject(response.body().string());
                    if (parseObject != null) {
                        callBack.onSuccess(parseObject);
                        return;
                    }
                    throw new IOException("The response data is empty");
                } catch (IOException e) {
                    ELog.d(HttpManager.TAG, "run: ", e);
                    callBack.onError(e);
                }
            }
        });
    }

    public void asyncExecuteForResponse(final CallBack callBack) {
        this.client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException iOException) {
                callBack.onError(iOException);
            }

            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful() || 200 != response.code()) {
                    callBack.onError(new Exception(String.valueOf(response.code())));
                } else {
                    callBack.onSuccess(response);
                }
            }
        });
    }

    public RequestBody getPostBody(String str) {
        return RequestBody.create(MediaType.parse("application/json;charset=utf-8"), str);
    }

    public static final class Builder {
        private Map<String, Object> bodyMap = new HashMap();
        private Map<String, Object> headMap = new HashMap();
        private Headers.Builder headerBuilder = new Headers.Builder();
        private boolean isMainThread;
        private boolean isSync;
        private MediaType mediaType;
        private String postString;
        private Request request;
        private int requestBodyType;
        private String requestType;
        private String url;

        public Builder mediaType(String str) {
            this.mediaType = MediaType.parse(str);
            return this;
        }

        public Builder get() {
            this.requestType = HttpManager.GET;
            this.requestBodyType = 0;
            return this;
        }

        public Builder postForm() {
            this.requestType = HttpManager.POST;
            this.requestBodyType = 1;
            return this;
        }

        public Builder postBody() {
            this.requestType = HttpManager.POST;
            this.requestBodyType = 2;
            return this;
        }

        public Builder postFile() {
            this.requestType = HttpManager.POST;
            this.requestBodyType = 3;
            return this;
        }

        public Builder url(String str) {
            this.url = str;
            return this;
        }

        public Builder addHeader(String str, String str2) {
            this.headerBuilder.add(str, str2);
            return this;
        }

        public Builder addParams(String str, String str2) {
            this.bodyMap.put(str, str2);
            return this;
        }

        public Builder addStringBody(String str) {
            this.postString = str;
            return this;
        }

        public Builder sync(boolean z) {
            this.isSync = z;
            return this;
        }

        public Builder isMainThread(boolean z) {
            this.isMainThread = z;
            return this;
        }

        public HttpManager build() {
            HttpManager instance = HttpManager.getInstance();
            boolean unused = instance.isSync = this.isSync;
            if (this.mediaType == null) {
                this.mediaType = MediaType.parse("application/json;charset=utf-8");
            }
            RequestBody requestBody = null;
            int i = this.requestBodyType;
            if (i != 1 && (i == 1 || i == 2)) {
                requestBody = !StringUtils.isEmpty(this.postString) ? RequestBody.create(this.mediaType, this.postString) : getRequestBody();
            }
            Request unused2 = HttpManager.request = new Request.Builder().url(this.url).headers(this.headerBuilder.build()).method(this.requestType, requestBody).build();
            return instance;
        }

        private RequestBody getRequestBody() {
            String str;
            String str2;
            if (this.requestBodyType == 2) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    for (String next : this.bodyMap.keySet()) {
                        if (this.bodyMap.get(next) == null) {
                            str2 = "";
                        } else {
                            str2 = this.bodyMap.get(next).toString();
                        }
                        jSONObject.put(next, (Object) str2);
                    }
                    return RequestBody.create(this.mediaType, jSONObject.toString());
                } catch (Exception e) {
                    ELog.e(HttpManager.TAG, "getRequestBody: ", e);
                    return null;
                }
            } else {
                FormBody.Builder builder = new FormBody.Builder();
                for (String next2 : this.bodyMap.keySet()) {
                    if (this.bodyMap.get(next2) == null) {
                        str = "";
                    } else {
                        str = this.bodyMap.get(next2).toString();
                    }
                    builder.add(next2, str);
                }
                return builder.build();
            }
        }
    }
}
