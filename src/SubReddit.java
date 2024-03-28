import java.util.ArrayList;
import java.util.List;
public class SubReddit {
    private String name;
    private String topic;
    private String mainAdmin;
    private final List <String> adminList = new ArrayList<>();
    public void setName(String name) {
        this.name = name;
    }

    public void setMainAdmin(String mainAdmin) {
        this.mainAdmin = mainAdmin;
    }

    public String getMainAdmin() {
        return mainAdmin;
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
        if (adminList.contains(userName))
            return true;
        else
            return false;
    }
}
