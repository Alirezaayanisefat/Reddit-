public class Post {
    private String subRedditName;
    private String owner;
    private String title;
    private String body;

    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getOwner() {
        return owner;
    }

    public String getSubRedditName() {
        return subRedditName;
    }

    public String getTitle() {
        return title;
    }
}
