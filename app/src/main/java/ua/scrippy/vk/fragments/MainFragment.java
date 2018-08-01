package ua.scrippy.vk.fragments;


import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiFriends;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKApiPhotoSize;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.api.model.VKPhotoArray;
import com.vk.sdk.api.model.VKPhotoSizes;
import com.vk.sdk.api.model.VkAudioArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ua.scrippy.vk.Person;

import ua.scrippy.vk.R;
import ua.scrippy.vk.RVAdapter;
import ua.scrippy.vk.DBHelper;
import ua.scrippy.vk.StartActivity;

public class MainFragment extends Fragment {

    private RecyclerView rv;
    final String LOG_TAG = "MainFragment";

    private static VKPhotoArray vkPhotoArray;
    List<Person> persons = new ArrayList<>();
    final String id = VKAccessToken.currentToken().userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.main_fragment,
                container, false);

        final RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        
        persons = photosGet(id);

        Log.d(LOG_TAG, "onCreateView");

        RVAdapter adapter = new RVAdapter(persons);
        rv.setAdapter(adapter);
        return view;
    }

    public  List<Person> photosGet(final String user_id){
        final String id = user_id;
        final int count = 50;
        final List<Person> persons = new ArrayList<>();
        final long[] imageDate = new long[count];
        final String[] imageUrl = new String[count];
        final String[] imageBigUrl = new String[count];

        final VKRequest imageRequest = new VKRequest("photos.get", VKParameters.from(
                VKApiConst.OWNER_ID, id,
                VKApiConst.ALBUM_ID, "saved",
                VKApiConst.REV, "1",
                VKApiConst.EXTENDED, "0",
                VKApiConst.PHOTO_SIZES, "0",
                VKApiConst.COUNT, 50),
                VKPhotoArray.class);
        imageRequest.executeWithListener(new VKRequest.VKRequestListener() {
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
                        imageUrl[i] = vkPhoto.src.getByType('m');
                    }
                    imageDate[i] = vkPhoto.date * 1000;
                    persons.add(new Person(user_id, imageDate[i], imageUrl[i]));
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
        return persons;
    }


}


