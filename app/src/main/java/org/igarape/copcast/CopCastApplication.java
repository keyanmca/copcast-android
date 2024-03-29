package org.igarape.copcast;

import android.app.Application;
import android.content.res.Configuration;

import org.igarape.copcast.utils.FileUtils;
import org.igarape.copcast.utils.NetworkUtils;

/**
 * Created by fcavalcanti on 27/10/2014.
 */
public class CopCastApplication extends Application {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkUtils.setAppContext(this.getApplicationContext());
        FileUtils.init();
    }



    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
