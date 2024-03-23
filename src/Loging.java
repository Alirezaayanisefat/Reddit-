import java.util.regex.Matcher;
import java.util.regex.Pattern;
// consider logging out
// this must read from a file not the super class
public class Loging extends AccountCreation {
    private String userEmail;
    private String password;
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
    public String getPassword() {
        return password;
    }
    public boolean passwordCheck()
    {
        return password.equals(super.getPassword());
    }
    public boolean emailCheck()
    {
        return userEmail.equals(super.getUserEmail());
    }
}
