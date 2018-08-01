package ua.scrippy.vk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.vk.sdk.VKSdk;

public class SettingsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updateTheme();

        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.settings);
        setSupportActionBar(toolbar);

        getFragmentManager().beginTransaction().replace(R.id.preferences_frame, new MyPreferenceFragment()).commit();


}
    public void updateTheme() {
        if (ThemeUtils.getTheme(getApplicationContext()) == 1) {
            setTheme(R.style.AppTheme);

        } else if (ThemeUtils.getTheme(getApplicationContext()) == 2) {
            setTheme(R.style.AppThemeDark);


        }
    }


    public static class MyPreferenceFragment extends PreferenceFragment {


        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            SwitchPreference themeSwitch = (SwitchPreference) findPreference("switch");
            themeSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

                @Override
                public boolean onPreferenceChange(Preference arg0, Object isdataTrafficEnabled) {

                    boolean isDataTrafficOn = ((Boolean) isdataTrafficEnabled).booleanValue();

                    if (isDataTrafficOn) {
                        ThemeUtils.setTheme(getActivity(), 2);

                    } else {
                        ThemeUtils.setTheme(getActivity(), 1);
                    }


                    return true;
                }
            });
            if (ThemeUtils.getTheme(getActivity()) == 2) {
                themeSwitch.setChecked(true);

            } else if (ThemeUtils.getTheme(getActivity()) == 1) {
                themeSwitch.setChecked(false);
            }
            Preference myPref = (Preference) findPreference("switch");
            myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    try {

                        TaskStackBuilder.create(getActivity())

                                .addNextIntent(new Intent(getActivity(), StartActivity.class))

                                .addNextIntent(new Intent(getActivity(), SettingsActivity.class))

                                .startActivities();

                    } catch (Throwable e) {

                        e.printStackTrace();

                        Toast.makeText(getActivity(), "Please restart application", Toast.LENGTH_LONG).show();

                    }

                    getActivity().overridePendingTransition(R.anim.alpha_out, R.anim.alpha_in);


                    return true;
                }
            });
            Preference devVK = (Preference) findPreference("devVK");
            devVK.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://vk.com/id113933878")));
                    return true;
                }
            });
            Preference logout = (Preference) findPreference("logoutVK");
            logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    VKSdk.logout();
                    TaskStackBuilder.create(getActivity())
                            .addNextIntent(new Intent(getActivity(), StartActivity.class))
                            .startActivities();
                    getActivity().finish();
                    return true;

                }
            });
            Preference groupVK = (Preference) findPreference("groupVK");
            groupVK.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://vk.com/scrippy_vk")));
                    return true;
                }
            });

        }


    }

}

