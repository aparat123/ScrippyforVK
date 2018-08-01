package ua.scrippy.vk;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKPhotoArray;
import com.vk.sdk.util.VKUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ua.scrippy.vk.fragments.LoginFragment;
import ua.scrippy.vk.fragments.MainFragment;

public class StartActivity extends AppCompatActivity {



    public static Context context;
    private static String LOG_TAG = "MainFActivity";
    LoginFragment loginfragment;
    MainFragment mainfragment;
    SharedPreferences mSettings;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateTheme();


        setContentView(R.layout.activity_main);

        context = getApplicationContext();  // in onCreate in MainActivity

        loginfragment = new LoginFragment();
        mainfragment = new MainFragment();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setNavigationIcon(R.mipmap.ic_settings_white_18dp);
      //  getSupportActionBar().setIcon(R.mipmap.ic_launcher_round);
        LoginState();
    }


        public void updateTheme() {
            if (ThemeUtils.getTheme(getApplicationContext()) == 1) {
                setTheme(R.style.AppTheme);

            } else if (ThemeUtils.getTheme(getApplicationContext()) == 2) {
                setTheme(R.style.AppThemeDark);


    }

}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.menuSettings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.menuLogout:

                finish();
                break;

        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FrameLayout, mainfragment).commit();
            }
            @Override
            public void onError(VKError error) {
// Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    public static ArrayList friendsGet(){

        final String id = VKAccessToken.currentToken().userId;
        final ArrayList friends = new ArrayList<>();
        final VKRequest request = new VKRequest("friends.get", VKParameters.from(
                VKApiConst.USER_ID, id));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                try {
                    JSONObject jsonObject = response.json.getJSONObject ("response");
                    JSONArray items = jsonObject.getJSONArray ("items");
                    for(int i = 0; i < items.length(); i++){
                        friends.add(items.get(i).toString());
                        Log.d("friend", i + " " + items.get(i).toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onError(VKError error) {
                super.onError(error);
                Log.v("error", "error");
            }
        });
        return friends;
    }


    private void LoginState(){
        VKSdk.wakeUpSession(StartActivity.context, new VKCallback<VKSdk.LoginState>() {
            @Override
            public void onResult(VKSdk.LoginState res) {
                switch (res) {
                    case LoggedOut:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.FrameLayout, loginfragment).commit();

                        Log.d("logout", "logoout");
                        break;
                    case LoggedIn:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.FrameLayout, mainfragment).commit();
                        break;
                    case Pending:
                        break;
                    case Unknown:
                        break;
                }
            }

            @Override
            public void onError(VKError error) {

            }
        });

    }



}
