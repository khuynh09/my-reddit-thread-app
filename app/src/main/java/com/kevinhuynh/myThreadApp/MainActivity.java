package com.kevinhuynh.myThreadApp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kevinhuynh.myThreadApp.Adapter.PostAdapter;
import com.kevinhuynh.myThreadApp.Adapter.ReplyAdapter;


import java.util.ArrayList;

import standard.Post;
import standard.Reply;
public class MainActivity extends AppCompatActivity {


    EditText postTitle;
    EditText postName;
    EditText postMessage;

    TextView rowPostName;

    Button addPost;
    ListView lvPost;
    ListView lvReply;
    Button deletePost;
    private DatabaseReference database;

    ArrayList<Post> postList;
    PostAdapter postAdapter;

    ArrayList<Reply> replyList = new ArrayList<Reply>();
    ReplyAdapter replyAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        database = FirebaseDatabase.getInstance().getReference();

        postList = new ArrayList<>();
        getPostList();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postTitle = findViewById(R.id.postTitle);
        postName = findViewById(R.id.postName);
        postMessage = findViewById(R.id.postMessage);
        addPost = findViewById(R.id.addPost);

        lvPost = findViewById(R.id.rvPost);
        lvReply = findViewById(R.id.replyView);

        postAdapter = new PostAdapter(this,postList);

        lvPost.setAdapter(postAdapter);

        deletePost = findViewById(R.id.delete);
        rowPostName = findViewById(R.id.reply_name);


        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference postRef = database.child("Post");

                String post_title = postTitle.getText().toString();
                String post_name = postName.getText().toString();
                String message_content = postMessage.getText().toString();
                String post_key = (database.push().getKey());

                if(message_content.length() > 0){
                    Post post = new Post(post_title, message_content, post_name);
                    ArrayList<Reply> reply_list = new ArrayList<Reply>();
                    post.setReplyList(reply_list);
                    post.setId(post_key);
                    postRef.child(post_key).setValue(post);
                    postRef.child(post_key).child("Replies");
                    postTitle.setText("");
                    postName.setText("");
                    postMessage.setText("");
                }


            }


        });

    }

    public void upScore(View v){
        ImageButton up = (ImageButton) v;
        ConstraintLayout parent = (ConstraintLayout) up.getParent();
        TextView score = (TextView) parent.findViewById(R.id.score);
        TextView postID = (TextView) parent.findViewById(R.id.postID);

        String post_id = postID.getText().toString();
        String stringScore = score.getText().toString();

        int p_score = Integer.parseInt(stringScore);
        p_score++;
        score.setText(Integer.toString(p_score));

        database.child("Post").child(post_id).child("score").setValue(p_score);

    }

    public void downScore(View v){
        ImageButton up = (ImageButton) v;
        ConstraintLayout parent = (ConstraintLayout) up.getParent();
        TextView score = (TextView) parent.findViewById(R.id.score);
        TextView postID = (TextView) parent.findViewById(R.id.postID);


        String post_id = postID.getText().toString();
        String stringScore = score.getText().toString();

        int p_score = Integer.parseInt(stringScore);
        p_score--;
        score.setText(Integer.toString(p_score));

        database.child("Post").child(post_id).child("score").setValue(p_score);




    }

    public void deletePost(View v) {
        Button flag = (Button) v;
        ConstraintLayout parent = (ConstraintLayout) flag.getParent();
        TextView postID = (TextView) parent.findViewById(R.id.postID);

        String post_id = postID.getText().toString();



        database.child("Post").child(post_id).removeValue();

        for (int i=0;i<postList.size();i++){
            if (postList.get(i).getId() == post_id){
                postAdapter.remove(postAdapter.getItem(i));
            }
        }




    }


    public void reply(View v){


        Button reply = (Button) v;
        ConstraintLayout parent = (ConstraintLayout) reply.getParent();


        TextView postID = (TextView) parent.findViewById(R.id.postID);
        EditText reply_content = (EditText) parent.findViewById(R.id.editText);
        TextView replyName = (TextView) parent.findViewById(R.id.reply_name);
        ListView lvReply = (ListView) parent.findViewById(R.id.replyView);

        String post_id = postID.getText().toString();
        String reply_text = reply_content.getText().toString();
        String reply_name = replyName.getText().toString();


        Reply postReply = new Reply(reply_text, reply_name);
        postReply.setId(post_id);
        database.child("Post").child(post_id).child("Replies").child("reply-"+post_id).setValue(postReply);


        replyList.add(postReply);


        replyAdapter = new ReplyAdapter(this, replyList);
        lvReply.setAdapter(replyAdapter);


        reply_content.setText("");

    }

    public void upRScore(View v){
        ImageButton up = (ImageButton) v;
        ConstraintLayout parent = (ConstraintLayout) up.getParent();
        TextView score = (TextView) parent.findViewById(R.id.score);
        TextView replyID = (TextView) parent.findViewById(R.id.replyID);

        String post_id = replyID.getText().toString();
        String stringScore = score.getText().toString();

        int p_score = Integer.parseInt(stringScore);
        p_score++;
        score.setText(Integer.toString(p_score));

        database.child("Post").child(post_id).child("reply-"+post_id).child("score").setValue(p_score);

    }

    public void downRScore(View v){
        ImageButton up = (ImageButton) v;
        ConstraintLayout parent = (ConstraintLayout) up.getParent();
        TextView score = (TextView) parent.findViewById(R.id.score);
        TextView replyID = (TextView) parent.findViewById(R.id.replyID);

        String post_id = replyID.getText().toString();
        String stringScore = score.getText().toString();

        int p_score = Integer.parseInt(stringScore);
        p_score--;
        score.setText(Integer.toString(p_score));

        database.child("Post").child(post_id).child("reply-"+post_id).child("score").setValue(p_score);

    }

    public void deleteReply(View v) {
        Button flag = (Button) v;
        ConstraintLayout parent = (ConstraintLayout) flag.getParent();
        TextView postID = (TextView) parent.findViewById(R.id.replyID);

        String post_id = postID.getText().toString();


        replyAdapter.clear();




    }




    public void getPostList(){
        database.child("Post").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Post post = dataSnapshot.getValue(Post.class);
                    postAdapter.add(post);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }











}
