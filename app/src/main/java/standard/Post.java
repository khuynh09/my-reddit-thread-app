package standard;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Post {

    private String id, title, content, username;
    private int score = 0;
    private ArrayList<Reply> replyList;

    public Post(){};

    public Post(String title, String msg, String name){
        this.title = title;
        this.content = msg;
        this.username = name;

        Reply reply = new Reply();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<Reply> getReplyList() {
        return replyList;
    }

    public void setReplyList(ArrayList<Reply> replyList) {
        this.replyList = replyList;
    }
}
