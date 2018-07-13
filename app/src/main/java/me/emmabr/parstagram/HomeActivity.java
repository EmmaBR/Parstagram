package me.emmabr.parstagram;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;

public class HomeActivity extends AppCompatActivity {

    // camera variables
    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        // define fragments here
        final Fragment fragment1 = new HomeFragment();
        final Fragment fragment2 = new NewPostFragment();
        final Fragment fragment3 = new UserFragment();

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction;

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, fragment1).commit();

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
                        break;
                    case R.id.action_new_post:
                        onLaunchCamera();
                        break;
                    case R.id.action_user:
                        newFragment = fragmentManager.beginTransaction();
                        newFragment.replace(R.id.flContainer, fragment3).commit();
                        break;
                    default:
                        newFragment = fragmentManager.beginTransaction();
                        newFragment.replace(R.id.flContainer, fragment1).commit();
                        break;
                }
                return true;
            }
        });
    }

    public void onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        Uri fileProvider = FileProvider.getUriForFile(HomeActivity.this, "me.emmabr.parstagram", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    private File getPhotoFileUri(String photoFileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + photoFileName);

        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Intent intent = new Intent(this, CreatePostActivity.class);
                intent.putExtra("path", photoFile.getAbsolutePath());
                startActivity(intent);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
