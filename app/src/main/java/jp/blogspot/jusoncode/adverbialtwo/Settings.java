package jp.blogspot.jusoncode.adverbialtwo;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.XmlRes;
import android.widget.FrameLayout;

public class Settings extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    FrameLayout frameLayout;

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        addPreferencesFromResource(R.xml.settings_screen);

        setRetainInstance(true);


    }

    @Override
    public void addPreferencesFromResource(@XmlRes int preferencesResId) {
        super.addPreferencesFromResource(preferencesResId);
    }

    @Override
    public void onResume(){
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        ////7 INCH TABLET
        int screenOrientation = getActivity().getResources().getConfiguration().orientation;
        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL){

            frameLayout = (FrameLayout)getActivity().findViewById(R.id.here);

            if(screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                frameLayout.setPadding(20,150,0,0);
            }else {
                frameLayout.setPadding(20, 50, 0, 0);
            }


        }
        else if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE){

            frameLayout = (FrameLayout)getActivity().findViewById(R.id.here);

            if(screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                frameLayout.setPadding(20,150,0,0);
            }
            else {
                frameLayout.setPadding(20, 100, 0, 0);
            }

        }else if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE){

            frameLayout = (FrameLayout)getActivity().findViewById(R.id.here);

            frameLayout.setPadding(20,150,0,0);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        boolean alarmNeeded = sharedPreferences.getBoolean("notifications",false);

        if(key.equalsIgnoreCase("notifications") & !alarmNeeded) {
            HomeScreen.cancelAlarm();

        }else{
            HomeScreen.setAlarm(getActivity());
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);

    }
}
