package me.emmabr.parstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import me.emmabr.parstagram.model.Post;

public class Parstagram extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * register subclass with parse object to let parse know that this model is
         * a custom parse model created to encapsulate data
        **/
        ParseObject.registerSubclass(Post.class);

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
