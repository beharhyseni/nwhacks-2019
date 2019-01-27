package com.example.nwhacks2019;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import org.json.JSONObject;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private JSONObject posts;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView price;
        public ImageView image;
        public TextView store;
        private final Context ct;

        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.title);
            price = v.findViewById(R.id.price);
            image = v.findViewById(R.id.image);
            store = v.findViewById(R.id.store);
            ct = v.getContext();
            image.setOnClickListener(this);
            //Drawable myDrawable = ct.getDrawable(R.drawable.plumber);
            //image.setImageDrawable(myDrawable);
        }

        @Override
        public void onClick(View v) {
            //final Intent intent = new Intent(ct, ServiceActivity.class);
            //ct.startActivity(intent);
        }
    }

    public PostsAdapter(JSONObject posts, Context context) {
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
        String positionString = Integer.toString(position);
        try {
            JSONObject productJSON = posts.getJSONObject(positionString);
            holder.name.setText(productJSON.get("name").toString());
            holder.price.setText(productJSON.get("price").toString());
            holder.store.setText(productJSON.get("store").toString());
        } catch (Exception e) {
        }
        //Picasso.with(context).load(posts[position].getimage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return posts.length();
    }
}
