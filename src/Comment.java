import javax.imageio.IIOException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Comment
{

    public static void clearScreen()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void sleep(long millis)
    {
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException er)
        {
            System.out.println("Something went wrong");
        }
    }
    public static void replaceLineInFile(File file ,String oldLine, String newLine)
    {
        String filePath = file.getPath();
        try
        {
            Scanner sc = new Scanner(new File(filePath));
            StringBuilder buffer = new StringBuilder();
            while (sc.hasNextLine()) {
                buffer.append(sc.nextLine()).append(System.lineSeparator());
            }
            String fileContents = buffer.toString();
            sc.close();
            fileContents = fileContents.replaceAll(oldLine, newLine);
            FileWriter writer = new FileWriter(filePath);
            writer.append(fileContents);
            writer.flush();
        }
        catch (IOException e)
        {
            System.out.println("Problem reading file.");
        }
    }
    public static boolean checkTheFileForLine(File file, String line)
    {
        List<String> list = new ArrayList<>();
        try
        {
            Scanner readFile = new Scanner(file);
            while(readFile.hasNextLine())
            {
                list.add(readFile.nextLine());
            }
            return list.contains(line);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("could not find the given file in check the file method");
        }
        return false;
    }
    public static void removeLineFromFile(String filePath, String lineToRemove)
    {

        try {
            System.out.println(filePath);
            File inFile = new File(filePath);

            if (!inFile.isFile()) {
                System.out.println("Parameter is not an existing file");
                return;
            }

            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

            BufferedReader br = new BufferedReader(new FileReader(filePath));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

            String line = null;

            //Read from the original file and write to the new
            //unless content matches data to be removed.
            while ((line = br.readLine()) != null) {

                if (!line.trim().equals(lineToRemove)) {

                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();

            //Delete the original file
            if (!inFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }
//            Path p = Paths.get(filePath);
//            try {
//                Files.delete(p);
//            } catch (IOException e) {
//                // the exception will explain everything there is to be said about why it did not work!
//            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inFile))
                System.out.println("Could not rename file");

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    //____________________________________________________________________________________________________________
    private final File postComments = new File("Posts\\" + Attributes.account.getUserName() +  "\\" + Attributes.post.getTitle() + "Comments");
    private final File postCommentReplies = new File("Posts\\" + Attributes.account.getUserName() +  "\\" + Attributes.post.getTitle() + "CommentsReplies");
    private static final List<String>list = new ArrayList<>();
//    private void readPostComments()
//    {
//        try
//        {
//            Scanner reader = new Scanner(postComments);
//            while (reader.hasNextLine()) {
//                list.add(reader.nextLine());
//            }
//        }
//        catch (IOException e)
//        {
//            System.out.println(e);
//        }
//    }
    private String userName;
    private String body;

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public static void giveComment()
    {
        Scanner input = new Scanner(System.in);
        String str;
        Attributes.comment.setUserName(Attributes.account.getUserName());
        System.out.println("Write your comment");
        str = input.nextLine();
        Attributes.comment.setBody(str);
        File postComments = new File("Posts\\" + Attributes.account.getUserName() + "\\" + Attributes.post.getTitle() + "Comments");
        try
        {
            FileWriter myWriter = new FileWriter(postComments,true);
            myWriter.write( "\\u" + Attributes.comment.getUserName() + " : ");
            myWriter.write(Attributes.comment.getBody() + "\n");
            myWriter.close();
            clearScreen();
            System.out.println("You have commented Successfully");
            sleep(2000);
        }
        catch (IOException e)
        {
            System.out.println("something went wrong with comment section file");
        }
    }

    public static void giveCommentOnSubReddit()
    {
        String str;
        String temp;// using to give the write path to the file write in give comment method
        Scanner getComment = new Scanner(System.in);
        clearScreen();
        System.out.println("Enter the subReddit name of the post :");
        str = getComment.nextLine();
        Attributes.post.setSubRedditName(str);
        System.out.println("Enter the username of the post creator");
        str = getComment.nextLine();
        Attributes.post.setUserName(str);
        System.out.println("Enter the title of the post by : " + Attributes.post.getUserName());
        str = getComment.nextLine();
        Attributes.post.setTitle(str);
        File postComments = new File("Posts\\" + Attributes.post.getUserName() + "\\" + Attributes.post.getTitle() + "Comments");
        try {
            Scanner commentReader = new Scanner(postComments);
            String data;
            while (commentReader.hasNextLine())
            {
                data = commentReader.nextLine();
                System.out.println(data);
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("user and title does nt match any");
        }
        System.out.println("Press 'E' if you want to give comment\nPress 'Q' tu return to previous menu");
        Scanner getUserChoice = new Scanner(System.in);
        char userChoice = getUserChoice.next().charAt(0);
        if ( userChoice == 'e')
        {
            clearScreen();
            giveComment();
        }
    }
}
