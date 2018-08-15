package ua.scrippy.vk.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk.sdk.VKAccessToken;

import java.util.ArrayList;
import java.util.List;

import ua.scrippy.vk.Api;
import ua.scrippy.vk.Person;

import ua.scrippy.vk.R;
import ua.scrippy.vk.RVAdapter;
import ua.scrippy.vk.models.PhotoItem;

public class MainFragment extends Fragment {

    private RecyclerView rv;
    final String LOG_TAG = "MainFragment";
    private Handler handler;
    List<Person> persons = new ArrayList<>();
    List<PhotoItem> photoItems = new ArrayList<>();
    String id = VKAccessToken.currentToken().userId;
    String token = VKAccessToken.currentToken().accessToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment,
                container, false);

        final RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);
        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        final RVAdapter adapter = new RVAdapter(persons);
        persons = Api.photosGet(id);

        rv.setAdapter(adapter);
        Log.d(LOG_TAG, "adapter");
        return view;
    }
}
/*  Application.getApi().getUsers("1", token, "5.20").enqueue(new Callback<UsersModel>() {
            @Override
            public void onResponse(@NonNull Call<UsersModel> call, @NonNull Response<UsersModel> response) {


                ArrayList<ResponseUserModel> userArray = response.body().getResponse();

                if (response.body() != null && response.isSuccessful()) {

                    for(ResponseUserModel user :userArray) {
                        Log.d(LOG_TAG, "access " + user.getFirstName());
                    }

                }
            }
            @Override
            public void onFailure(Call<UsersModel> call, Throwable t) {
                //Произошла ошибка
                Log.d(LOG_TAG, "error");
            }
        });*/









