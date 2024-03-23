// through this class you can create file, write to file and read from it
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class UserAccountFile extends AccountCreation
{
    private String userName;
    private String email;
    private String password;
    private boolean hasAccount;//  to check if the user has account or not
    // edit the path
    private final File file = new File("C:\\Users\\LEGION\\IdeaProjects\\Reddit\\users\\" + super.getUserName());
    public void createFile() throws IOException {
        file.createNewFile();
    }
    public void writeToFile() throws IOException {
        FileWriter myWriter = new FileWriter("C:\\Users\\LEGION\\IdeaProjects\\Reddit\\users\\" + super.getUserName());
        myWriter.write(super.getUserName());
        myWriter.write(super.getUserEmail());
        myWriter.write(super.getPassword());
        myWriter.close();
    }
    public void readFile() throws FileNotFoundException {
        Scanner myReader = new Scanner(file);
        //while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        //}
        myReader.close();
    }

    public File getFile() {
        return file;
    }

    public boolean membershipCheck() {
        return hasAccount;
    }
}

