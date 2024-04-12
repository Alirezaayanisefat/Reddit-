import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Post {
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
    //_____________________________________________________________________
    private String subRedditName;
    private String userName;
    private String title;
    private String body;
    private boolean hasFirstPost;
    private String karma;
    public  Post()
    {
        this.karma = "0";
    }

    public void setKarma(String karma) {
        this.karma = karma;
    }
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

    public String getKarma() {
        return karma;
    }

    public String getTitle() {
        return title;
    }

    //______________________________________________________________________________________

    public static void addPostTimeline()
    {
        File subRedditData = new File("SubReddit\\" + Attributes.post.getSubRedditName() +"\\"+ Attributes.post.getSubRedditName() + "members.txt");
        try {
            Scanner readMembers = new Scanner(subRedditData);
            String memberName;
            while (readMembers.hasNextLine())
            {
                memberName = readMembers.nextLine();
                File timeLine = new File("users\\" + memberName + "\\" + memberName + "TimeLine.txt");
                try {
                    FileWriter writer = new FileWriter(timeLine,true);
                    writer.write(Attributes.post.getSubRedditName() + ",");
                    writer.write(Attributes.post.getUserName() + ",");
                    writer.write(Attributes.post.getTitle() + "\n");
                    writer.close();
                }
                catch (IOException e)
                {
                    System.out.println("could not write to member file in add to time line method");
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("could not read the member file in add to timeline method");
        }
    }

    public static void addPostToSubReddit()
    {
        File subRedditPostFile = new File("SubReddit\\" + Attributes.post.getSubRedditName() +"\\"+ Attributes.post.getSubRedditName()  + "Posts.txt");
        try{

            File file = new File("SubReddit\\SubRedditNames.txt");
            if (checkTheFileForLine(file, Attributes.post.getSubRedditName()))
            {
                if (!checkTheFileForLine(subRedditPostFile ,Attributes.post.getUserName() + "," +Attributes.post.getTitle()))
                {
                    FileWriter writer = new FileWriter(subRedditPostFile, true);
                    writer.write(Attributes.post.getSubRedditName() + ",");
                    writer.write( Attributes.post.getUserName()  + ",");// posts are written with this format on subRedditPostFile
                    writer.write(Attributes.post.getTitle() + "\n");
                    writer.close();
                }
            }
            else
            {
                System.out.println("such a SubReddit does not exist");
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("an exception");
        }
        catch (IOException e)
        {
            System.out.println("could not add the post to SubReddit");
        }

    }

    public static void writeToPostFile()
    {
        boolean success;
        File subRedditNames = new File("SubReddit\\SubRedditNames.txt");// to check if the subReddit is created or not
        File subRedditPosts = new File("SubReddit\\" +Attributes.post.getSubRedditName() + "\\" + Attributes.post.getSubRedditName() + "Posts.txt");
        File postKarma = new File("Posts\\" + Attributes.account.getUserName()  + "\\" + Attributes.post.getTitle() + "Karma.txt");
        File postComments = new File("Posts\\" + Attributes.account.getUserName() +  "\\" + Attributes.post.getTitle() + "Comments.txt");
        File postUserVote = new File("Posts\\" + Attributes.account.getUserName()  + "\\" + Attributes.post.getTitle() + "userVote.txt");

        if (checkTheFileForLine(subRedditNames, Attributes.post.getSubRedditName()))
        {
            File postFile = new File("Posts\\" + Attributes.account.getUserName() +  "\\" + Attributes.post.getTitle() + "_" + Attributes.post.getSubRedditName() + ".txt");// this is the post file name. the file where the post details are
            try
            {

                if (!checkTheFileForLine(subRedditPosts, Attributes.post.getSubRedditName()+ "," + Attributes.post.getTitle()))
                {


                    success = postFile.createNewFile();
                    success = postComments.createNewFile();
                    success = postKarma.createNewFile();
                    success = postUserVote.createNewFile();
                    FileWriter writer = new FileWriter(postFile);
                    writer.write("\\r" + Attributes.post.getSubRedditName() + "\n");
                    writer.write("\\u" + Attributes.post.getUserName() + "\n");
                    writer.write("title : " + Attributes.post.getTitle() + "\n");
                    writer.write(Attributes.post.getBody() + "\n");
                    writer.write("Karma : " + Attributes.post.getKarma());
                    writer.close();

                    FileWriter myWriter = new FileWriter(postComments);
                    myWriter.write("Comment section :\n");
                    myWriter.close();

                    addPostTimeline();
                    System.out.println("Your post with title " + Attributes.post.getTitle() + " is created successfully");
                }
                else
                {
                    System.out.println("you already have "+Attributes.post.getTitle() +" title on "+ Attributes.post.getSubRedditName() + " subReddit");
                }
            }
            catch (IOException e)
            {
                System.out.println("Something went wrong");
            }
        }
        else {
            Attributes.post.setFirstPost(true);
            System.out.println("This subReddit does not exist");
            sleep(2000);
            clearScreen();
        }
    }
    // write to post file and checks to not create a post if its subReddit is not created before
    public static void createDirectory()
    {
        File titleFile = new File("Posts\\" + Attributes.account.getUserName()  + "\\" +Attributes.account.getUserName() + "PostTitles.txt");
        File postFile = new File("Posts\\" + Attributes.account.getUserName() +  "\\" + Attributes.post.getTitle() + "_"+ Attributes.post.getSubRedditName() + ".txt");// this is the post file name. the file where the post details are

        boolean success;
        success = postFile.getParentFile().mkdir();
        writeToPostFile();
        try {
            if (postFile.exists())
            {
                success = titleFile.createNewFile();
                if (!checkTheFileForLine(titleFile, Attributes.post.getSubRedditName() +"," + Attributes.post.getTitle()))
                {
                    FileWriter writer = new FileWriter(titleFile, true);
                    writer.write(  Attributes.post.getSubRedditName() + "," + Attributes.post.getTitle() + "\n");
                    writer.close();
                }
                else
                    System.out.println("you have this post with this title on this subReddit");
            }
        }
        catch (IOException e)
        {
            System.out.println("something went wrong with creating titleFile");
            Attributes.post.setFirstPost(true);
        }
    }

    public static void createPost()
    {
        Scanner input = new Scanner(System.in);
        String temp;

        temp = Attributes.account.getUserName();
        Attributes.post.setUserName(temp);
        System.out.println("Enter the SubReddit name:");
        temp = input.nextLine();
        Attributes.post.setSubRedditName(temp);
        clearScreen();
        System.out.println("SubReddit :\\r " + temp);

        System.out.print("Title: ");
        temp = input.nextLine();
        Attributes.post.setTitle(temp);
        clearScreen();
        System.out.println("SubReddit: \\r " + Attributes.post.getSubRedditName());
        System.out.println("Title: " + temp);

        System.out.println("Body: ");
        temp = input.nextLine();// how to get a paragraph?????????????????????????
        // probably hava to initials attributes
        Attributes.post.setBody(temp);
        clearScreen();

        System.out.println("SubReddit: \\r" + Attributes.post.getSubRedditName());
        System.out.println("Title: " + Attributes.post.getTitle());
        System.out.println("Body: " + Attributes.post.getBody());
        createDirectory();
        addPostToSubReddit();
    }
    //when a post is created postKarma and postComments file is created by method above too

    public static void showPostDetails()
    {
        File postTitleFile = new File("Posts\\" + Attributes.account.getUserName() +"\\"+ Attributes.account.getUserName() +"PostTitles.txt");

        try {

            if(checkTheFileForLine(postTitleFile, Attributes.post.getSubRedditName() +"," +Attributes.post.getTitle()))
            {
                String data;
                File postFile = new File("Posts\\" + Attributes.account.getUserName() +  "\\" + Attributes.post.getTitle() + "_" + Attributes.post.getSubRedditName() + ".txt");
                Scanner postTitleFileReader= new Scanner(postFile);

                while (postTitleFileReader.hasNextLine())
                {
                    data = postTitleFileReader.nextLine();
                    System.out.println(data);
                }
                postTitleFileReader.close();
            }
            else
            {
                System.out.println("you do not have such a post");
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Something went wrong");
        }
    }

    public static void accessToPosts()

    {
        File titleFile = new File("Posts\\" + Attributes.account.getUserName() +"\\"+ Attributes.account.getUserName() + "PostTitles.txt");
        try
        {
            Scanner fileReader = new Scanner(titleFile);
            String data;

            System.out.println("Titles :");
            int i = 0;
            while (fileReader.hasNext()) {
                data = fileReader.nextLine();
                System.out.println('_' + data);// first data is subReddit name the second data is post creator name and the last one is the post title
            }
            fileReader.close();
        }
        catch (FileNotFoundException e)
        {
            Scanner input = new Scanner(System.in);
            System.out.println("You have no posts");
            sleep(2000);
            clearScreen();
            System.out.println("Press 'Q' to return to main menu\nPress 'E' to create your first post");
            char ch = input.next().charAt(0);

            if (ch == 'e') {
                createPost();
                return;
            }
            else if (ch == 'q') {
                Attributes.post.setFirstPost(true);
                return;
            }
        }
        System.out.println("_____________");
        System.out.println("HELP:\nPress 'E' to create post\nPress 'R' to access to posts\nPress 'Q' to return to Main menu");
        Scanner input = new Scanner(System.in);
        char ch = input.next().charAt(0);
        clearScreen();
        switch (ch)
        {
            case 'e':
                createPost();
                clearScreen();
                sleep(2000);
                clearScreen();
                break;
            case 'r':
                // only prints the post
                String title;
                Scanner input_2 = new Scanner(System.in);
                System.out.println("Enter the subReddit name:");
                title = input_2.nextLine();
                Attributes.post.setSubRedditName(title);
                System.out.println("Enter the post title:");
                title = input_2.nextLine();
                Attributes.post.setTitle(title);
                showPostDetails();
                clearScreen();
                System.out.println("Press 'Q' to exit");/////////////////////////////////// ADD ACCESS TO COMMENT
                char quit = input.next().charAt(0);
                clearScreen();
                if(quit == 'q') {
                    Attributes.post.setFirstPost(true);
                    return;
                }
                break;
            case 'q': {
                Attributes.post.setFirstPost(true);
                break;
            }
        }
    }

    public static void seePost()
    {
        clearScreen();
        accessToPosts();
        if (Attributes.post.getFirstPost()) {
            Attributes.post.setFirstPost(false);// this is for controlling the menu
            return;
        }
        // this is a for menu control when we cant access to posts
        clearScreen();
        System.out.println("Press 'C' to access to comment section\nPress 'Q' to quit");
        Scanner input = new Scanner(System.in);
        char ch = input.next().charAt(0);
        if(ch == 'q') {
            clearScreen();
            return;
        }
        System.out.println("Press 'C' if you want to see comment section");
        if (ch == 'c')
        {   clearScreen();
            File postComments = new File("Posts\\" +Attributes.account.getUserName() + "\\" + Attributes.post.getTitle() + "Comments.txt");
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
                System.out.println("could not read from the comment file");
            }
        }
        System.out.println("Press 'E' if you want to leave a comment\nPress 'Q' if you want to quit");
        char commentOrLeave = input.next().charAt(0);
        clearScreen();
        if (commentOrLeave == 'e')
        {
            clearScreen();
            Comment.giveComment();
        }
        else if (commentOrLeave == 'q')
            return;
    }

}
