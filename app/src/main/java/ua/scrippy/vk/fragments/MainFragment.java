package ua.scrippy.vk.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk.sdk.VKAccessToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import ua.scrippy.vk.Person;

import ua.scrippy.vk.R;
import ua.scrippy.vk.RVAdapter;
import ua.scrippy.vk.api.VKApiModule;
import ua.scrippy.vk.models.FriendsModel;
import ua.scrippy.vk.models.FriendsResponse;
import ua.scrippy.vk.models.PhotoItem;
import ua.scrippy.vk.models.PhotoModel;


public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    final String LOG_TAG = "MainFragment";
    ArrayList<Person> persons = new ArrayList<>();
    String id = VKAccessToken.currentToken().userId;
    String token = VKAccessToken.currentToken().accessToken;
    private ArrayList<Integer> friendsIds;
    RecyclerView rv;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    RVAdapter adapter;
    public static final String BASE_URL = "https://vk-api-proxy.xtrafrancyz.net/";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment,
                container, false);

        rv = (RecyclerView) view.findViewById(R.id.rv);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);
        loadData();
        Log.d(LOG_TAG, LOG_TAG);
        mSwipeRefreshLayout.setRefreshing(true);
        return view;
    }
    private void loadData(){
        new Thread() {
            public void run() {
                Log.d(LOG_TAG, "count = ");

                try {
                    Response response = VKApiModule.getService().getFriends(id, token, "5.80").execute();
                    FriendsModel friendsModel = (FriendsModel) response.body();
                    FriendsResponse friendsResponse = friendsModel.getResponse();
                    ArrayList<Integer> friendsId = (ArrayList<Integer>) friendsResponse.getItems();
                    for(int i = 0; i < friendsId.size(); i++) {
                        Response response1 = VKApiModule.getService().getPhotos(friendsId.get(i) + "", "saved", "1", "1", "0", "1", token, "5.80").execute();
                        PhotoModel photoModel = (PhotoModel) response1.body();
                        if (photoModel.getResponse() != null) {
                            List<PhotoItem> photoItems = photoModel.getResponse().getItems();
                            for (int a = 0; a < photoItems.size(); a++) {
                                persons.add(new Person(friendsId.get(i) + " ", Long.valueOf(photoItems.get(a).getDate()), photoItems.get(a).getSizes().get(photoItems.get(a).getSizes().size() - 1).getUrl()));
                                Log.d(LOG_TAG, " persons = " + persons.size());

                            }

                        }

                        else if (photoModel.getResponse() == null){
                                Log.d(LOG_TAG, " null " + i );
                            }

                        Thread.sleep(350);

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        adapter = new RVAdapter(persons);
                        rv.setAdapter(adapter);
                    }
                });
            }
        }.start();

    }
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Отменяем анимацию обновления
                if (mSwipeRefreshLayout.isRefreshing()) {
                    persons.clear();
                    rv.setAdapter(adapter);
                    mSwipeRefreshLayout.setRefreshing(true);
                    loadData();
                } else {

                    mSwipeRefreshLayout.setRefreshing(false);

                }
            }
        }, 100);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}