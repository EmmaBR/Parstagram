package me.emmabr.parstagram;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import me.emmabr.parstagram.model.Post;

public class HomeFragment extends Fragment {
    ArrayList<Post> posts;
    RecyclerView rvPosts;
    PostAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        posts = new ArrayList<>();
        adapter = new PostAdapter(posts);

        rvPosts = (RecyclerView) view.findViewById(R.id.rvPosts);
        rvPosts.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPosts.setAdapter(adapter);
        loadTopPosts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void loadTopPosts() {
        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser();

        // grab posts using parse query and view it in the background
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {

                    for (int i = objects.size() - 1; i > -1; --i) {
                        posts.add(objects.get(i));
                    }
                    adapter.notifyDataSetChanged();
                    // iterate through posts
//                    for (int i = 0; i < objects.size(); i++) {
//                        Log.d("HomeActivity", "Post[" + i + "] = "
//                                + objects.get(i).getDescription()
//                                + "\nusername = " + objects.get(i).getUser().getUsername()
//                        );
//                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

}
