// through this class you can gat the data that is needed and check the email and password
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// consider logging out
// this must read from a file not the super class
public class Loging extends AccountCreation {
    private String userEmail;
    private String password;
    private String userName;

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean validateEmail() {
        // Regular expression for email validation
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userEmail);
        return matcher.matches();
    }

    @Override
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUserEmail() {
        return userEmail;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }
    public boolean passwordCheck()
    {
        boolean check = false;
        if(password == super.getPassword()) {
            check = true;
        }
        return check;
    }
    public boolean emailCheck()
    {
        boolean check = false;
        if(userEmail == getUserEmail()) {
            check = true;
        }
        return check;
    }
}
