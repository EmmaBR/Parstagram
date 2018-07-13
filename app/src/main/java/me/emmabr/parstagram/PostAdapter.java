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
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

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
        holder.tvUser2.setText(post.getUser().getUsername());
        holder.tvCreatedAt.setText(post.getCreatedAt().toString());

        String imageUrl = post.getImage().getUrl();

        //TODO: fixme logged in user profile image is being displayed on all posts
        ParseFile parseFile = ParseUser.getCurrentUser().getParseFile("profileImage");
        String profileImageUrl = null;

        if (parseFile != null) {
            profileImageUrl = parseFile.getUrl();
        } else {
        }

        ImageView profileImageView = holder.ivUserPic;
        ImageView imageView = holder.ivPost;

        //load image using glide
        Glide.with(context)
                .load(imageUrl)
                .into(imageView);

        //load userProfile image
        Glide.with(context)
                .load(profileImageUrl)
                .into(profileImageView);
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
        TextView tvUser2;
        TextView tvDescription;
        TextView tvCreatedAt;
        ImageView ivUserPic;

        public ViewHolder(View itemView) {
            super(itemView);
            //lookup view objects by id
            ivPost = itemView.findViewById(R.id.ivPost);
            tvUser = (TextView) itemView.findViewById(R.id.tvUser);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvCreatedAt = (TextView) itemView.findViewById(R.id.tvCreatedAt);
            tvUser2 = (TextView) itemView.findViewById(R.id.tvUser2);
            ivUserPic = (ImageView) itemView.findViewById(R.id.ivUserPic);
        }
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }
}
