public class Post {
    private String subRedditName;
    private String userName;
    private String title;
    private String body;
    private boolean hasFirstPost;
    private int karma;
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

    public String getTitle() {
        return title;
    }

}
