package ua.scrippy.vk;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static VKApiRetrofit vkApiRetrofit;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                //Базовая часть адреса
                .baseUrl("https://api.vk.com")
                //Конвертер, необходимый для преобразования JSON'а в объекты
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        //Создаем объект, при помощи которого будем выполнять запросы
        vkApiRetrofit = retrofit.create(VKApiRetrofit.class);
    }

    public static VKApiRetrofit getApi() {
        return vkApiRetrofit;
    }
}
