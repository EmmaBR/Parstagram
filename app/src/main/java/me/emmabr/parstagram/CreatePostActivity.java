package me.emmabr.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import me.emmabr.parstagram.model.Post;

public class CreatePostActivity extends AppCompatActivity {

    EditText descriptionInput;
    Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        descriptionInput = findViewById(R.id.etDescription);
        createBtn = findViewById(R.id.btnCreatePost);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String descripton = descriptionInput.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();

                final File file = new File(getIntent().getStringExtra("path"));
                final ParseFile parseFile = new ParseFile(file);

                createPost(descripton, parseFile, user);

            }
        });
    }

    private void createPost(String description, ParseFile parseFile, ParseUser user) {
        final Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setImage(parseFile);
        newPost.setUser(user);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("CreatePostActivity", "Create post successful!");
                    Intent intent = new Intent(CreatePostActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
