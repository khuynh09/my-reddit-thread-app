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

import standard.Reply;

public class ReplyAdapter extends ArrayAdapter<Reply> {

    private Context mContext;

    private ArrayList<Reply> replyList;


    public ReplyAdapter(Context context, ArrayList<Reply> list){

        super(context,0,list);

        this.mContext = context;
        this.replyList = list;

    }

    @Override

    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;
        final Reply currentReply = replyList.get(position);

        if(listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.row_reply,parent,false);

        }


        TextView ID = listItem.findViewById(R.id.replyID);
        ID.setText(currentReply.getId());
//
        TextView score = listItem.findViewById(R.id.score);
        String post_score = Integer.toString(currentReply.getScore());
        score.setText(post_score);
//
        TextView message = listItem.findViewById(R.id.reply_content);
        message.setText(currentReply.getContent());

        return listItem;
    }

}
