package com.utte.growon.mono;

import com.google.gson.Gson;
import com.utte.growon.mono.model.Afternoon_tea;
import com.utte.growon.mono.model.Morning_tea;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.utte.growon.mono.music.MonoMusicNet.getMonoMusicData;
import static com.utte.growon.mono.music.MonoMusicNet.getMonoPicData;

public class MonoNetWork {

    private static OkHttpClient mOkHttpClient;
    private static final String TAG = "MonoNetWork";

    // https://mmmono.com/api/v3/tea/2018-10-27/full/
    private static final String MONO_URL_PREFIX = "https://mmmono.com/api/v3/tea/";
    private static final String MONO_URL_SUFFIX = "/full/";

    static {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }};
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            mOkHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    .sslSocketFactory(sslSocketFactory)
                    .build();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }


    public static void getMonoData(String date, final MonoIn in) {
        final Request request = new Request.Builder()
                .url(MONO_URL_PREFIX.concat(date).concat(MONO_URL_SUFFIX))
                .addHeader("User-Agent", "api-client/2.0.0 com.mmmono.mono/3.6.8 Android/8.0.0 ONEPLUS A3010 channel/qq")
                .addHeader("Cookie", "SERVER_ID=1912e68c-fb300035; bid=5917a910; session=eyJfcGVybWFuZW50Ijp0cnVlfQ.DrWhzQ.SRv5ZGo7zg1bZjysaTG_TSVEi6c")
                .addHeader("HTTP-AUTHORIZATION", "58a38bd4d9b711e88f54525400b827a7")
                .addHeader("MONO-USER-AGENT", "api-client/2.0.0 com.mmmono.mono/3.6.8 Android/8.0.0 ONEPLUS A3010 channel/qq")
                .addHeader("Connection", "Keep-Alive")
                .addHeader("Host", "mmmono.com")
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // TODO: 2018/10/27 请求One首页数据失败
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    MonoData data = new MonoData();
                    data.mMorningTea = new Gson().fromJson(String.valueOf(json.getJSONObject("morning_tea")), Morning_tea.class);
                    data.mAfternoonTea = new Gson().fromJson(String.valueOf(json.getJSONObject("afternoon_tea")), Afternoon_tea.class);

                    in.onSuccess(data);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static void getDetailData(long id, Callback callback) {
        String url = "https://mmmono.com/api/v3/g/meow/" + String.valueOf(id) + "/";
        final Request request = new Request.Builder()
                .url(url)
//                .addHeader("Accept-Encoding", "gzip")
                .addHeader("User-Agent", "api-client/2.0.0 com.mmmono.mono/3.6.8 Android/8.0.0 ONEPLUS A3010 channel/qq")
                .addHeader("Cookie", "SERVER_ID=1912e68c-fb300035; bid=5917a910; session=eyJfcGVybWFuZW50Ijp0cnVlfQ.DrWhzQ.SRv5ZGo7zg1bZjysaTG_TSVEi6c")
                .addHeader("HTTP-AUTHORIZATION", "58a38bd4d9b711e88f54525400b827a7")
                .addHeader("MONO-USER-AGENT", "api-client/2.0.0 com.mmmono.mono/3.6.8 Android/8.0.0 ONEPLUS A3010 channel/qq")
                .addHeader("Connection", "Keep-Alive")
                .addHeader("Host", "mmmono.com")
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);

    }



}
