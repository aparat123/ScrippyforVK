package ua.scrippy.vk;

import android.support.annotation.NonNull;
import android.util.Log;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKPhotoArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.scrippy.vk.fragments.MainFragment;
import ua.scrippy.vk.models.PhotoItem;
import ua.scrippy.vk.models.PhotoModel;
import ua.scrippy.vk.models.PhotoSize;
import ua.scrippy.vk.models.ResponsePhoto;



public class Api {
    final private static String LOG_TAG = "Api";

    public static List<Person> photosGet(final String user_id) {

        final List<Person> persons = new ArrayList<>();
        String token = VKAccessToken.currentToken().accessToken;


        Application.getApi().getPhotos("113933878", "saved", "1", "0", "0", "45", token, "5.80").enqueue(new Callback<PhotoModel>() {
                @Override
                public void onResponse(@NonNull Call<PhotoModel> call, @NonNull Response<PhotoModel> response) {
                    Log.d(LOG_TAG, "access");
                    if (response.body() != null && response.isSuccessful()) {
                        ResponsePhoto responsePhotoArrayList = response.body().getResponse();

                        ArrayList<PhotoItem> photoItems = (ArrayList<PhotoItem>) responsePhotoArrayList.getItems();
                        for (int i = 0; i < photoItems.size(); i++) {
                            ArrayList<PhotoSize> photoSizes = (ArrayList<PhotoSize>) photoItems.get(i).getSizes();
                            Log.d(LOG_TAG, photoSizes.get(photoSizes.size() - 1).getType() + " " + photoSizes.get(photoSizes.size() - 1).getUrl());
                            persons.add(new Person(user_id, photoItems.get(i).getDate(), photoSizes.get(photoSizes.size() - 1).getUrl()));
                        }
                    }
                    }
                @Override
                public void onFailure(Call<PhotoModel> call, Throwable t) {
                    Log.d(LOG_TAG, "photos " + " 123");
                }
            });

           return persons;
        }
    }

