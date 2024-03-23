import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class UserAccountFile
{
    private String userName;
    private String email;
    private String password;
    private boolean hasAccount;//  to check if the user has account or not
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    // edit the path
    private final File file = new File(userName);
    public void createFile() throws IOException {
        hasAccount = file.createNewFile();
    }
    public void writeToFile() throws IOException {
        FileWriter myWriter = new FileWriter(userName);
        myWriter.write(userName);
        myWriter.write(email);
        myWriter.write(password);
        myWriter.close();
    }
    public void readFile() throws FileNotFoundException {
        Scanner myReader = new Scanner(file);
        //while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        //}
        myReader.close();
    }
}

