package ua.scrippy.vk;

import android.util.Log;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Application extends android.app.Application {
    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken != null) {
// VKAccessToken is invalid
            }
        }
    };

    private static VKApiRetrofit vkApiRetrofit;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);


        retrofit = new Retrofit.Builder()
                //Базовая часть адреса
                .baseUrl("https://api.vk.com")
                //Конвертер, необходимый для преобразования JSON'а в объекты
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //Создаем объект, при помощи которого будем выполнять запросы
        vkApiRetrofit = retrofit.create(VKApiRetrofit.class);

    }
    public static VKApiRetrofit getApi() {
        return vkApiRetrofit;
    }


}

