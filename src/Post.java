import java.io.File;

public class Post {
    private String subRedditName;
    private String userName;
    private String title;
    private String body;
    private boolean hasFirstPost;
    private String karma = "0";

    public void setKarma(String karma) {
        this.karma = karma;
    }
    public void setFirstPost(boolean firstPost) {
        this.hasFirstPost = firstPost;
    }
    public boolean getFirstPost()
    {
        return hasFirstPost;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setSubRedditName(String subRedditName) {
        this.subRedditName = subRedditName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public String getUserName() {
        return userName;
    }

    public String getSubRedditName() {
        return subRedditName;
    }

    public String getKarma() {
        return karma;
    }

    public String getTitle() {
        return title;
    }

//    public  void determineKarma()
//    {
//        File postKarma = new File("Posts\\" + account.getUserName() +"\\" + post.getTitle() + "Karma");
//    }
}
