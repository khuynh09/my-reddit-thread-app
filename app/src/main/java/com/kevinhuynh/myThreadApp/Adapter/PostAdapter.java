package com.kevinhuynh.myThreadApp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.kevinhuynh.myThreadApp.R;


import java.util.ArrayList;

import standard.Post;

public class PostAdapter extends ArrayAdapter<Post> {

    private Context mContext;

    private ArrayList<Post> postList;


    public PostAdapter(Context context, ArrayList<Post> list){

        super(context,0,list);

        this.mContext = context;
        this.postList = list;

    }

    @Override

    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;
        final Post currentPost = postList.get(position);

        if(listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.row_post,parent,false);

        }


        TextView name = listItem.findViewById(R.id.reply_name);
        String post_username ="posted by t/" + currentPost.getUsername();
        name.setText(post_username);

        TextView ID = listItem.findViewById(R.id.postID);
        ID.setText(currentPost.getId());

        TextView post_title = listItem.findViewById(R.id.post_title);
        post_title.setText(currentPost.getTitle());

        TextView score = listItem.findViewById(R.id.score);
        String post_score = Integer.toString(currentPost.getScore());
        score.setText(post_score);

        TextView message = listItem.findViewById(R.id.reply_content);
        message.setText(currentPost.getContent());

        return listItem;
    }

}
