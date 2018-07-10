package me.emmabr.parstagram;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

import me.emmabr.parstagram.model.Post;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // define your fragments here
        final Fragment fragment1 = new HomeFragment();
        final Fragment fragment2 = new NewPostFragment();
        final Fragment fragment3 = new UserFragment();

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction;

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, fragment1).commit();
        loadTopPosts();

        // code below deals with switching between fragments using bottom navigation
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction newFragment = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.action_home:
                        newFragment = fragmentManager.beginTransaction();
                        newFragment.replace(R.id.flContainer, fragment1).commit();
                        loadTopPosts();
                        break;
                    case R.id.action_new_post:
                        newFragment = fragmentManager.beginTransaction();
                        newFragment.replace(R.id.flContainer, fragment2).commit();
                         break;
                    case R.id.action_user:
                        newFragment = fragmentManager.beginTransaction();
                        newFragment.replace(R.id.flContainer, fragment3).commit();
                        break;
                    default:
                        newFragment = fragmentManager.beginTransaction();
                        newFragment.replace(R.id.flContainer, fragment1).commit();
                        loadTopPosts();
                        break;
                }
                return true;
            }
        });
    }

    private void loadTopPosts() {
        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser();

        // grab posts using parse query and view it in the background
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    // iterate through posts
                    for (int i = 0; i < objects.size(); i++) {
                        Log.d("HomeActivity", "Post[" + i + "] = "
                                + objects.get(i).getDescription()
                                + "\nusername = " + objects.get(i).getUser().getUsername()
                        );
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
