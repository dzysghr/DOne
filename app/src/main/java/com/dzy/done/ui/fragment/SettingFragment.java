package com.dzy.done.ui.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;

import com.dzy.done.R;
import com.dzy.done.asynctask.ClearCacheTask;
import com.dzy.done.config.AppSetting;

/**
 *  设置界面
 */
public class SettingFragment extends PreferenceFragment
{



    public static SettingFragment newInstance()
    {
        return new SettingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getPreferenceManager().setSharedPreferencesName("setting");
        addPreferencesFromResource(R.xml.setting);

        getPreferenceScreen().findPreference("FontSize").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue)
            {
                AppSetting.getSetting().setFontSize(Integer.parseInt((String)newValue));
                return true;
            }
        });

    }


    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference)
    {

        //如果选中了清除缓存
        if (preference.getKey().equals("clearCache"))
        {
            new ClearCacheTask().execute();
            return true;
        }
        else if (preference.getKey().equals("NightMode"))
        {
            boolean night =  ((SwitchPreference)preference).isChecked();
            AppSetting.getSetting().setNightMode(night);
            getActivity().recreate();
//            if (night)
//            {
//                ((AppCompatActivity)getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            }else
//            {
//                ((AppCompatActivity)getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            }
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

}
