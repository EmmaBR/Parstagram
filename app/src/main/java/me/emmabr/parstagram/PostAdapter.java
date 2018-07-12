package me.emmabr.parstagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import me.emmabr.parstagram.model.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    //list of posts
    ArrayList<Post> posts;
    //context
    Context context;

    //initialize list
    public PostAdapter(ArrayList<Post> movies) {
        this.posts = movies;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //get context and create inflater
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //create view using item movie layout
        View viewPost = inflater.inflate(R.layout.activity_post_list, parent, false);
        // return a new viewholder
        return new ViewHolder(viewPost);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.tvUser.setText(post.getUser().getUsername());
        holder.tvDescription.setText(post.getDescription());
        String imageUrl = null;

        ImageView imageView = holder.ivPost;
        //load image using glide
        Glide.with(context)
                .load(imageUrl)
                .into(imageView);
    }

    // returns size of list
    @Override
    public int getItemCount() {
        return posts.size();
    }

    //create viewholder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //track view objects
        ImageView ivPost;
        TextView tvUser;
        TextView tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            //lookup view objects by id
            ivPost = itemView.findViewById(R.id.ivPost);
            tvUser = (TextView) itemView.findViewById(R.id.tvUser);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
        }
    }
}
