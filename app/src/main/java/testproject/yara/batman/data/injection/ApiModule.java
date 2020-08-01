package testproject.yara.batman.data.injection;


import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import testproject.yara.batman.BuildConfig;
import testproject.yara.batman.data.UserAgentInterceptor;
import testproject.yara.batman.data.api.BatmanApi;

import static testproject.yara.batman.data.Constants.CACHE_SIZE;

@Module
public class ApiModule {

    private static File cacheDir = new File(System.getProperty("java.io.tmpdir")
            , UUID.randomUUID().toString());


    public ApiModule() {
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {

        Cache cache = new Cache(cacheDir, CACHE_SIZE);
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .cache(cache)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS);
        clientBuilder.networkInterceptors().add(new UserAgentInterceptor
                ("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 " +
                        "(KHTML, like Gecko) Chrome/70.0.3538.102 Mobile Safari/537.36"));
        return clientBuilder.build();
    }

    @Provides
    @Singleton
    BatmanApi provideBatmanApiService(OkHttpClient okHttpClient) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BATMAN_IP)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(BatmanApi.class);
    }

}
