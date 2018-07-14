package me.emmabr.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.File;

public class UserFragment extends Fragment {
    // added variables
    private Button logOutBtn;
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public final static int CREATE_IMAGE_ACTIVITY_REQUEST_CODE = 1035;
    public final static String PARSE_FILE_NAME = "profileImage";
    public String photoFileName = "photo.jpg";
    File photoFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View newView = inflater.inflate(R.layout.fragment_user, container, false);

        //sets username to profile
        TextView userName = (TextView) newView.findViewById(R.id.tvUserName);
        userName.setText(ParseUser.getCurrentUser().getUsername());

        //sets user profile pic to user profile page
        ImageView userPic = (ImageView) newView.findViewById(R.id.ivUserPic);
        ParseFile parseFile = ParseUser.getCurrentUser().getParseFile("profileImage");
        String imgUrl;

        if(parseFile != null) {
            imgUrl = parseFile.getUrl();
            Glide.with(getContext()).load(imgUrl).into(userPic);
        }

        //TODO: set an on Click to access media storage to change profile image and send back to parse
        userPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //access local media storage to upload an image
                Intent intent = new Intent(Intent.ACTION_PICK); //search local gallary??
                startActivity(intent);
            }
        });

        return newView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final ParseUser parseUser = ParseUser.getCurrentUser();
//
//        //parse user set Image??do i need a user model and user adapter?
//        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                ParseFile profPic = new ParseFile(photoFile.getAbsoluteFile());
//                ParseUser.getCurrentUser().put("profileImage", profPic);
//                ParseUser.getCurrentUser().saveInBackground();
//                Bitmap profBitmapImg =  BitmapFactory.decodeFile(photoFile.getAbsolutePath());
//                ((UserFragment) fgProfile).ibCreateProfPic.setImageBitmap(profBitmapImg);
//            } else { // Result was a failure
//                Toast.makeText(getActivity(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Intent i = new Intent(getActivity(), UserFragment.class);
//            i.putExtra("photoFilePath", photoFile.getAbsolutePath());
//            startActivity(i);
//
//        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logOutBtn = view.findViewById(R.id.btnLogOut);
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();

                // checks if the logout was successful
                ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
                if (currentUser == null) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    Toast.makeText(getActivity(), "Log out successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Log out failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}


