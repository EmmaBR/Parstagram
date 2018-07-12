package me.emmabr.parstagram;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
    private SwipeRefreshLayout swipeContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

//    //method for pull down to refresh
//    public void fetchTimelineAsync(int page) {
//        final Post.Query postQuery = new Post.Query();
//        postQuery.getTop().withUser();
//        postQuery.findInBackground(new FindCallback<Post>() {
//            @Override
//            public void done(List<Post> objects, ParseException e) {
//                if (e == null) {
//                    adapter.clear();
//                    // ...the data has come back, add new items to your adapter...
//                    List<Post> list = new ArrayList<>();
//                    for (int i=0; i<objects.size(); i++) {
//                        list.add(objects.get(i));
//                    }
//                    adapter.addAll(list);
//                    // Now we call setRefreshing(false) to signal refresh has finished
//                    swipeContainer.setRefreshing(false);
//                } else {
//                    Log.i("Parstagram", "Sorry, can't load feed.");
//                    e.printStackTrace();
//                }
//            }
//        });
//    }


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
        View newView = inflater.inflate(R.layout.fragment_home, container, false);

        swipeContainer = (SwipeRefreshLayout) newView.findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                loadTopPosts();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        return newView;
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
                    swipeContainer.setRefreshing(false);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

}
