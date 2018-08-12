package ua.scrippy.vk;

import android.util.Log;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKPhotoArray;

import java.util.ArrayList;
import java.util.List;

import ua.scrippy.vk.fragments.MainFragment;

public class Api {
    final private static String LOG_TAG = "Api";
    private static VKPhotoArray vkPhotoArray;
    List<Person> persons = new ArrayList<>();
    private static RVAdapter adapter;

    public static List<Person> photosGet(final String user_id){
        final int count = 50;
        final List<Person> persons = new ArrayList<>();
        final long[] imageDate = new long[count];
        final String[] imageUrl = new String[count];
        final String[] imageBigUrl = new String[count];

        final VKRequest imageRequest = new VKRequest("photos.get", VKParameters.from(
                VKApiConst.OWNER_ID, user_id,
                VKApiConst.ALBUM_ID, "saved",
                VKApiConst.REV, "1",
                VKApiConst.EXTENDED, "0",
                VKApiConst.PHOTO_SIZES, "0",
                VKApiConst.COUNT, 50),
                VKPhotoArray.class);
        imageRequest.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                vkPhotoArray = (VKPhotoArray) response.parsedModel;
                int i = 0;
                for (VKApiPhoto vkPhoto : vkPhotoArray) {

                    if (vkPhoto.src.getByType('w') != null) {
                        imageUrl[i] = vkPhoto.src.getByType('w');
                        imageBigUrl[i] = vkPhoto.src.getByType('w');
                    }
                    if (vkPhoto.src.getByType('w') == null) {
                        imageUrl[i] = vkPhoto.src.getByType('z');
                        imageBigUrl[i] = vkPhoto.src.getByType('z');
                    }
                    if (vkPhoto.src.getByType('z') == null) {
                        imageUrl[i] = vkPhoto.src.getByType('y');
                        imageBigUrl[i] = vkPhoto.src.getByType('y');
                    }
                    if (vkPhoto.src.getByType('y') == null) {
                        imageUrl[i] = imageBigUrl[i] = vkPhoto.src.getByType('x');

                    }
                    if (vkPhoto.src.getByType('x') == null) {
                        imageUrl[i] = imageBigUrl[i] = vkPhoto.src.getByType('m');
                    }
                    imageDate[i] = vkPhoto.date * 1000;
                    persons.add(new Person(user_id, imageDate[i], imageBigUrl[i]));
                    i++;


                                    }
                Log.d(LOG_TAG, "photos");


            }
            @Override
            public void onError(VKError error) {
                super.onError(error);
                Log.v("photosGet", "error");
            }

        });
        Log.d(LOG_TAG, "return");
        return persons;
    }
}
