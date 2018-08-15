package ua.scrippy.vk.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;


import ua.scrippy.vk.R;
import ua.scrippy.vk.StartActivity;



public class LoginFragment extends Fragment {


    LoginFragment loginFragment;
    private static final String[] MyScope = new String[]{
            VKScope.FRIENDS,
            VKScope.PHOTOS
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        loginFragment = new LoginFragment();
        View view = inflater.inflate(R.layout.login_fragment,
                container, false);
        Button button = (Button) view.findViewById(R.id.auth);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("loginfragment", "auth button");
                VKSdk.login(getActivity(), MyScope);
            }
        });

        return view;
    }



}
