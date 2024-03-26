import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class PostFile extends Post{
    private final File file = new File("C:\\Users\\LEGION\\IdeaProjects\\Reddit\\Post\\" + super.getOwner());
    public void createFile() throws IOException {
        file.createNewFile();
    } //implement try and catch and append these two methods
    public void writeToFile() throws IOException {
        FileWriter myWriter = new FileWriter("C:\\Users\\LEGION\\IdeaProjects\\Reddit\\Post\\" + super.getOwner());
        myWriter.write(super.getOwner() + "\n");
        myWriter.write(super.getSubRedditName() + '\n');
        myWriter.write(super.getTitle() + '\n');
        myWriter.write(super.getBody() + '\n');
        myWriter.close();
    }
    public void readFile() {
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("something went wrong");
        }
    }
}
