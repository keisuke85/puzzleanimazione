package it.keisoft.puzzleanimazione;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

/**
 * Created by mmarcheselli on 13/11/2015.
 */
public class SettingsActivity extends PreferenceActivity {

    static CallbackManager callbackManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingFragment()).commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public static class SettingFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.preferences);

            FacebookSdk.sdkInitialize(getActivity());
            callbackManager = CallbackManager.Factory.create();


            final SwitchPreference fbSwitch = (SwitchPreference) findPreference("pref_fb_switch_settings");
            fbSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    boolean enabled = false;

                    if (newValue instanceof Boolean) {
                        enabled = (Boolean) newValue;
                    }

                    if (enabled) {
                        LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile"));
                    }else{
                        LoginManager.getInstance().logOut();
                    }
                    return true;
                }
            });

            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {}

                @Override
                public void onCancel() {
                    fbSwitch.setChecked(false);
                }

                @Override
                public void onError(FacebookException exception) {
                    fbSwitch.setChecked(false);
                }
            });
        }
    }
}
