package com.example.nwhacks2019;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

public class PostsAdapter {
    /*
    private ServicePost[] posts;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView description;
        public TextView host;
        public TextView numRatings;
        public ImageView photo;
        public RatingBar rating;
        private final Context ct;

        public ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            description = v.findViewById(R.id.description);
            host = v.findViewById(R.id.host);
            numRatings = v.findViewById(R.id.numRatings);
            photo = v.findViewById(R.id.photo);
            rating = v.findViewById(R.id.ratingBar);
            ct = v.getContext();
            photo.setOnClickListener(this);
            //int resID = ct.getResources().getIdentifier("plumber.jpg" , "drawable", ct.getPackageName());
            Drawable myDrawable = ct.getDrawable(R.drawable.plumber);
            photo.setImageDrawable(myDrawable);
        }

        @Override
        public void onClick(View v) {
            //final Intent intent = new Intent(ct, ServiceActivity.class);
            //ct.startActivity(intent);
        }
    }

    public PostsAdapter(ServicePost[] posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(posts[position].getTitle());
        holder.description.setText(posts[position].getDescription());
        holder.host.setText(posts[position].getHost());
        holder.numRatings.setText(posts[position].getNumRatings().toString());
        holder.rating.setNumStars((int)posts[position].getRating());
        //Picasso.with(context).load(posts[position].getPhoto()).into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return posts.length;
    }
    * */
}
