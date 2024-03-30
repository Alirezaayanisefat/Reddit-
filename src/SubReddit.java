import java.util.ArrayList;
import java.util.List;
public class SubReddit {
    private String name;
    private String topic;
    private String subRedditPassword;
    private final List <String> adminList = new ArrayList<>();
    public void setName(String name) {
        this.name = name;
    }

    public void setSubRedditPassword(String mainAdmin) {
        this.subRedditPassword = mainAdmin;
    }

    public String getSubRedditPassword() {
        return subRedditPassword;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setNewAdmin(String adminName) {
         adminList.add(adminName);
    }

    public String getName() {
        return name;
    }

    public String getTopic() {
        return topic;
    }

    public boolean isAdmin(String  userName) {
        return adminList.contains(userName);
    }
}
