package me.emmabr.parstagram;

import android.app.Application;

import com.parse.Parse;

public class Parstagram extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // create a new configuration
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("emma-parse")
                .clientKey("jwqrjuexpo1201936")
                .server("http://emmabr-fbu-instagram.herokuapp.com/parse")
                .build();

        // initialize configuration
        Parse.initialize(configuration);
    }
}
