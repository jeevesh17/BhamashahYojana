package com.paprbit.bhamashah.util;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by ankush38u on 3/19/2017.
 */

public class BhamashahApp extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleHelper.onAttach(getBaseContext());
        //update locale and resources, configuration on each config change
    }
}
