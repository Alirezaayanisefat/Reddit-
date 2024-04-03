
/*
      Study about throws InterruptedException.
       remember to add a break statement for accessing process method
       make user able to post with the same title several posts at writeToPostFile method make good names for files
       add a comment deleting method
*/
import java.io.*;
import java.lang.Thread;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
// this format is how a post is written on a subreddit on timeline and on postTitleFile
public class Main
{
//____________________________________Fields___________________________________________________________________________________
    private static final AccountCreation account = new AccountCreation();
    private static final Post post = new Post();
    private static final Comment comment = new Comment();
    private static final SubReddit subReddit = new SubReddit();
//_____________________________________________________________________________________________________________________________

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
    public static void removeLineFromFile(File file, String lineToRemove)
    {
        String filePath = file.getPath();
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
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
//__________________________________________________________POSTING AND COMMENTING PROCESS_____________________________________________________
    public static int determineKarma()
    {
        int upVotes = 0;
        int downVotes = 0;
        List<String> votsList = new ArrayList<>();
        File postKarma = new File("Posts\\" + post.getUserName()  + "_" +post.getSubRedditName() +"\\" + post.getTitle() + "Karma");
        try {
            Scanner readFromPostKarma = new Scanner(postKarma);
            while (readFromPostKarma.hasNextLine())
            {
                votsList.add(readFromPostKarma.nextLine());
            }
            for (String s : votsList) {
                if (s.equals("e")) {
                    upVotes++;
                }
                else if (s.equals("q"))
                {
                    downVotes++;
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("could no calculate the karma in determineKarmaMethod");
        }
        return (upVotes - downVotes);
    }
    // this method counts the votes and sets karma of the post object and the returns the value in String type to save it to file : post.getTitle() + "," + post.getSubRedditName()
    public static void getUerKarmaVote()
    {
        File postKarma = new File("Posts\\" + post.getUserName()  + "_" +post.getSubRedditName() +"\\" + post.getTitle() + "Karma");
        Scanner input = new Scanner(System.in);
        System.out.println("Press 'E' if you want to \"up vote\"\npress 'Q' if you want to \"down vote\"");
        char userVote = input.next().charAt(0);
        try
        {
            if (userVote == 'e')
            {
                FileWriter writer = new FileWriter(postKarma, true);
                writer.write("e\n");
                writer.close();
            }
            else if(userVote == 'q')
            {
                FileWriter writer = new FileWriter(postKarma, true);
                writer.write("q\n");
                writer.close();
            }

        }
        catch (IOException e)
        {
            System.out.println("could not find the postKarma file in giveKarma method");
        }
        clearScreen();
    }// this method gets user vote

    public static void giveKarma()
    {
        clearScreen();
        Scanner userInput = new Scanner(System.in);
        String postData;
        System.out.println("Please enter the username of the post creator: ");
        postData = userInput.nextLine();
        post.setUserName(postData);
        System.out.println("Enter the subReddit name of the post:");
        postData = userInput.nextLine();
        post.setSubRedditName(postData);
        System.out.println("Enter the postTitle please: ");
        postData = userInput.nextLine();
        post.setTitle(postData);
        File postFile = new File("Posts\\" + post.getUserName() + "\\" + post.getUserName() + "_" + post.getSubRedditName() + "\\" + post.getTitle());
        getUerKarmaVote();
        int karma = determineKarma();
        int previousKarna = karma - 1;
        String oldKarma = Integer.toString(previousKarna);
        String newKarma = Integer.toString(karma);
        replaceLineInFile(postFile, "Karma : " + oldKarma, "Karma : " + newKarma);
    }
    //_________________________________
    public static void addPostTimeline()
    {
        File subRedditData = new File("SubReddit\\" + post.getSubRedditName() +"\\"+ post.getSubRedditName() + "members");
        try {
            Scanner readMembers = new Scanner(subRedditData);
            String memberName;
            while (readMembers.hasNextLine())
            {
                memberName = readMembers.nextLine();
                File timeLine = new File("users\\" + memberName + "\\" + memberName + "TimeLine");
               try {
                   FileWriter writer = new FileWriter(timeLine,true);
                   writer.write(post.getSubRedditName() + ",");
                   writer.write(post.getUserName() + ",");
                   writer.write(post.getTitle() + "\n");
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
        System.out.println(subReddit.getName());
        File subRedditPostFile = new File("SubReddit\\" + post.getSubRedditName() +"\\"+ post.getSubRedditName()  + "Posts");
        try{
            List<String> list = new ArrayList<>();
            File file = new File("SubReddit\\SubRedditNames");
            if (checkTheFileForLine(file, post.getSubRedditName()))
            {
                FileWriter writer = new FileWriter(subRedditPostFile, true);
                writer.write( post.getUserName()  + ",");// posts are written with this format on subRedditPostFile
                writer.write(post.getTitle() + "\n");
                writer.close();
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
        File subRedditNames = new File("SubReddit\\SubRedditNames");// to check if the subReddit is created or not
        File subRedditPosts = new File("SubReddit\\" + post.getSubRedditName() + "\\" + post.getSubRedditName() + "Posts");
        if (checkTheFileForLine(subRedditNames, post.getSubRedditName()))
        {
            File postFile = new File("Posts\\" + account.getUserName() + "\\" + account.getUserName() + "_" + post.getSubRedditName() + "\\" + post.getTitle());// this is the post file name. the file where the post details are

           try
           {
               if (!checkTheFileForLine(subRedditPosts, post.getUserName() + "," + post.getTitle()))
               {
                   boolean success = postFile.createNewFile();
                   FileWriter writer = new FileWriter(postFile);
                   writer.write("\\r" + post.getSubRedditName() + "\n");
                   writer.write("\\u" + post.getUserName() + "\n");
                   writer.write("title : " + post.getTitle() + "\n");
                   writer.write(post.getBody() + "\n");
                   writer.write("Karma : " + post.getKarma());
                   writer.close();
                   addPostTimeline();
                   System.out.println("Your post with title " + post.getTitle() + " is created successfully");
               }
               else
               {
                   System.out.println("you already have "+ post.getTitle() +" title on "+ post.getSubRedditName() + " subReddit");
               }
           } catch (IOException e) {
               System.out.println("Something went wrong");
           }
       }
        else {
            post.setFirstPost(true);
            System.out.println("This subReddit does not exist");
            sleep(2000);
            clearScreen();
        }
    }
    // write to post file and checks to not create a post if its subReddit is not created before
    public static void createDirectory()
    {
        File userPosts = new File("Posts\\" + account.getUserName());
        File titleFile = new File("Posts\\" + account.getUserName()  + "\\" + account.getUserName() + "PostTitles");
        File postFile = new File("Posts\\" + account.getUserName() + "\\"+ account.getUserName() + "_" + post.getSubRedditName() + "\\" + post.getTitle());// this is the post file name. the file where the post details are
        System.out.println(titleFile);
        System.out.println(postFile);
        boolean success = userPosts.mkdir();
        success = postFile.getParentFile().mkdir();
        writeToPostFile();
        try {
            if (postFile.exists())
            {
                success = titleFile.createNewFile();
                FileWriter writer = new FileWriter(titleFile, true);
                writer.write(post.getSubRedditName() + "," + post.getTitle() + "\n");
                writer.close();
            }
        }
        catch (IOException e)
        {
            System.out.println("something went wrong with creating titleFile");
            post.setFirstPost(true);
        }
    }

    public static void createPost()
    {
        Scanner input = new Scanner(System.in);
        String temp;
        temp = account.getUserName();
        post.setUserName(temp);
        System.out.println("Enter the SubReddit name:");
        temp = input.nextLine();
        post.setSubRedditName(temp);
        clearScreen();
        System.out.println("SubReddit :\\r " + temp);
        System.out.print("Title: ");
        temp = input.nextLine();
        post.setTitle(temp);
        clearScreen();
        System.out.println("SubReddit: \\r " + post.getSubRedditName());
        System.out.println("Title: " + temp);
        System.out.println("Body: ");
        temp = input.nextLine();// how to get a paragraph?????????????????????????
        // probably hava to initials attributes
        post.setBody(temp);
        clearScreen();
        System.out.println("SubReddit: \\r" + post.getSubRedditName());
        System.out.println("Title: " + post.getTitle());
        System.out.println("Body: " + post.getBody());
        createDirectory();
        addPostToSubReddit();
        File postComments = new File("Posts\\" + account.getUserName() + "\\" +  account.getUserName() + "_" + post.getSubRedditName() + "\\" + post.getTitle() + "Comments");
        File postKarma = new File("Posts\\" + account.getUserName() + "\\" +account.getUserName() + "_" + post.getSubRedditName()  +"\\" + post.getTitle() + "Karma");
        File subRedditFile = new File("SubReddit\\SubRedditNames");
        try{
            if (checkTheFileForLine(subRedditFile, post.getSubRedditName()))
            {
                boolean success = postComments.createNewFile();
                success = postKarma.createNewFile();
                FileWriter myWriter = new FileWriter(postComments);
                myWriter.write("Comment section :\n");
                myWriter.close();
            }
            else
                post.setFirstPost(true);
        }
        catch (IOException e)
        {
            System.out.println("Comment section is not created");
        }
    }
    //when a post is created postKarma and postComments file is created by method above too
    public static void showPostDetails()
    {
        File postTitleFile = new File("Posts\\" + account.getUserName() +"\\"+ account.getUserName() +"PostTitles");
        try {
            if(checkTheFileForLine(postTitleFile, post.getSubRedditName() + "," + post.getTitle()))
            {
                String data;
                File postFile = new File("Posts\\" + account.getUserName() + "\\" + account.getUserName() + "_" + post.getSubRedditName() + "\\" + post.getTitle());
                Scanner postTitleFileReader= new Scanner(postFile);
                while (postTitleFileReader.hasNextLine())
                {
                    data = postTitleFileReader.nextLine();
                    System.out.println(data);
                }

                postTitleFileReader.close();
            } else {
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
        File titleFile = new File("Posts\\" + account.getUserName() +"\\"+ account.getUserName() + "PostTitles");
        try
        {
            Scanner fileReader = new Scanner(titleFile);
            String data;
            System.out.println("Titles :");
            while (fileReader.hasNext()) {
                data = fileReader.nextLine();
                System.out.println('_' + data);
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
                post.setFirstPost(true);
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
                post.setSubRedditName(title);
                System.out.println("Enter the post title:");
                title = input_2.nextLine();
                post.setTitle(title);
                showPostDetails();
                clearScreen();
                System.out.println("Press 'Q' to exit");/////////////////////////////////// ADD AN ACCESS TO COMMENT
                char quit = input.next().charAt(0);
                clearScreen();
                if(quit == 'q') {
                    post.setFirstPost(true);
                    return;
                }
                break;
            case 'q': {
                post.setFirstPost(true);
                break;
            }
        }
    }

    public static void giveComment()
    {
        Scanner input = new Scanner(System.in);
        String str;
        comment.setUserName(account.getUserName());
        System.out.println("Write your comment");
        str = input.nextLine();
        comment.setBody(str);
        File postComments = new File("Posts\\" + account.getUserName() + "\\" +  account.getUserName() + "_" + post.getSubRedditName() + "\\" + post.getTitle() + "Comments");
        try
        {
            FileWriter myWriter = new FileWriter(postComments,true);
            myWriter.write( "\\u" + comment.getUserName() + " : ");
            myWriter.write(comment.getBody() + "\n");
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

    public static void seePost()
    {
        clearScreen();
        accessToPosts();
        if (post.getFirstPost()) {
            post.setFirstPost(false);// this is for controlling the menu
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
            File postComments = new File("Posts\\" + account.getUserName() + "\\" +  account.getUserName() + "_" + post.getSubRedditName() + "\\" + post.getTitle() + "Comments");
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
            giveComment();
        }
        else if (commentOrLeave == 'q')
            return;
    }

//____________________________________________________SubReddit Process______________________________________________________________________

    public static void kickUser()
    {
        File subRedditMemberFile = new File("SubReddit\\" + subReddit.getName() + "\\" + subReddit.getName() + "members");
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the username you want to kick :");
        String userName = input.nextLine();
        removeLineFromFile(subRedditMemberFile, userName);
        File joinedSubReddit = new File("user\\" + userName + "\\" + userName + "JoinedSubReddit");
        removeLineFromFile(joinedSubReddit, subReddit.getName());
        System.out.println("The user is kicked successfully");
        sleep(2000);
        clearScreen();
    }

    public static void deleteComment()
    {   File subRedditPostFile = new File("SubReddit\\" + subReddit.getName() +"\\"+ subReddit.getName()  + "Posts");
        File postComments = new File("Posts\\" + account.getUserName() + "\\" +  account.getUserName() + "_" + post.getSubRedditName() + "\\" + post.getTitle() + "Comments");
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the username of post creator : ");
        String data = input.nextLine();
        post.setUserName(data);
        System.out.println("Enter the post title : ");
        data = input.nextLine();
        post.setTitle(data);
        System.out.println("Please enter the username of comment creator : ");
        data = input.nextLine();
        comment.setUserName(data);
        System.out.println("Enter the comment body : ");
        data = input.nextLine();
        comment.setBody(data);
        String lineToRemove ="\\u" + comment.getUserName() + " : " + comment.getBody();
        if (checkTheFileForLine(subRedditPostFile ,post.getUserName() + "," + post.getTitle()))
          removeLineFromFile(postComments, lineToRemove);
        else
            System.out.println("This post is not on your subReddit");
        System.out.println("The comment is deleted successfully");
    }
    // the admins are not able to delete comments of other posts which are not on their subReddit
    public static void deletePostFromTimeLines(String subRedditName, String creatorUserName, String postTitle)
    {
        File subRedditData = new File("SubReddit\\" + post.getSubRedditName() +"\\"+ post.getSubRedditName() + "members");
        try {
            Scanner readMembers = new Scanner(subRedditData);
            String memberName;
            while (readMembers.hasNextLine())
            {
                String data = subRedditName + "," + creatorUserName + "," + postTitle;
                memberName = readMembers.nextLine();
                File timeLine = new File("users\\" + memberName + "\\" + memberName + "TimeLine");
                removeLineFromFile(timeLine, data);
            }
        }
        catch (IOException e)
        {
            System.out.println("could not read the memberFile in deletePostFromTimeLines method");
        }
    }

    public static void deletePost()
    {
        File subRedditPostFile = new File("SubReddit\\" + subReddit.getName() +"\\"+ subReddit.getName()  + "Posts");
        //File timeLine = new File("users\\" + account.getUserName() + "\\" + account.getUserName() + "TimeLine");
        String username;
        String postTitle;
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the post creator userName: ");
        username = input.nextLine();
        System.out.println("Enter the Post title: ");
        postTitle = input.nextLine();
        File postFile = new File( "Posts\\" + username +"\\"+ username + "_" + subReddit.getName() + "\\" + postTitle);
        removeLineFromFile(subRedditPostFile, username + "," + postTitle);
        //removeLineFromFile(timeLine, subReddit.getName() + "," + username + "," +postTitle);
        deletePostFromTimeLines(subReddit.getName(), username, postTitle);
        try {
            FileWriter writer = new FileWriter(postFile, true);
            writer.write("\nThis post is deleted from its subReddit");
            writer.close();
            clearScreen();
            System.out.println("You have deleted the post successfully");
            sleep(2000);
            clearScreen();
        }
        catch (IOException e)
        {
            System.out.println("could not write to postFile in delete post method");
        }
    }
    //this method is not complete

    public static void addAdmin()
    {
        File subRedditAdminFile = new File("SubReddit\\" + subReddit.getName() +"\\"+ subReddit.getName() + "Admins");
        File userNames = new File("users\\userNames");
        try {
            Scanner readAdminFile = new Scanner(subRedditAdminFile);
            Scanner input = new Scanner(System.in);
            List<String> admins = new ArrayList<>();
            while (readAdminFile.hasNextLine())
                admins.add(readAdminFile.nextLine());
            if (account.getUserName().equals(admins.get(1)))
            {
                String newAdminName;
                System.out.println("Please enter the new Admin userName:");
                newAdminName = input.nextLine();
                clearScreen();
                List<String>checkNewAdminUserName = new  ArrayList<>();
                Scanner readUserNames = new Scanner(userNames);
                while (readUserNames.hasNextLine())
                 checkNewAdminUserName.add(readUserNames.nextLine());
                if (checkNewAdminUserName.contains(newAdminName))
                {
                    try
                    {
                        FileWriter writer = new FileWriter(subRedditAdminFile,true);
                        writer.write(newAdminName);
                        writer.close();
                    }
                    catch (IOException e)
                    {
                        System.out.println("Could not find the  admin file");
                    }
                }
                else
                {
                    System.out.println("Such a user does not exist");
                    sleep(2000);
                    clearScreen();
                }
            }
            else
            {
              System.out.println("Only the subReddit creator can add new admin");
              sleep(2000);
              clearScreen();
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("could not read the admins file in addAdmin method");
        }
    }

    public static int displayAccessToSubRedditMenu()
    {
        Scanner input = new Scanner(System.in);
        clearScreen();
        int pointer = 1;
        System.out.println("1 _ Add a new Admin <--\n2 _ Delete a post\n3 _ Delete a comment\n4 _ Kick a user\n5 _ Return to Main Menu");
        while (true)
        {
            char ch = input.next().charAt(0);
            if(ch == 'w') {
                pointer--;
                clearScreen();
            }
            else if(ch == 's')
            {
                pointer++;
                clearScreen();
            }
            if (pointer % 5 == 1) {
                clearScreen();
                System.out.println("1 _ Add a new Admin <--\n2 _ Delete a post\n3 _ Delete a comment\n4 _ Kick a user\n5 _ Return to Main Menu");
            }
            else if (pointer % 5 == 2)
            {
                clearScreen();
                System.out.println("1 _ Add a new Admin\n2 _ Delete a post <--\n3 _ Delete a comment\n4 _ Kick a user\n5 _ Return to Main Menu");
            }
            else if (pointer % 5 == 3)
            {
                clearScreen();
                System.out.println("1 _ Add a new Admin\n2 _ Delete a post\n3 _ Delete a comment <--\n4 _ Kick a user\n5 _ Return to Main Menu");
            }
            else if(pointer % 5 == 4)
            {
                clearScreen();
                System.out.println("1 _ Add a new Admin\n2 _ Delete a post\n3 _ Delete a comment\n4 _ Kick a user <--\n5 _ Return to Main Menu");
            }
            else
            {
                clearScreen();
                System.out.println("1 _ Add a new Admin\n2 _ Delete a post\n3 _ Delete a comment\n4 _ Kick a user\n5 _ Return to Main Menu <--");
            }
            if (ch == 'e')
            {
                break;
            }
        }
        return pointer % 5;
    }

    public static void accessToSubReddit()
    {
        int order = displayAccessToSubRedditMenu();
        Scanner input = new Scanner(System.in);
        while (true)
        {
            if (order == 1)
            {
                addAdmin();
                clearScreen();
                order = displayAccessToSubRedditMenu();
            }
            else if (order == 2)
            {
                deletePost();
                clearScreen();
                order = displayAccessToSubRedditMenu();
            }
            else if (order == 3)
            {
                deleteComment();
                clearScreen();
                order = displayAccessToSubRedditMenu();
            }
            else if (order == 4)
            {
                kickUser();
                clearScreen();
                order = displayAccessToSubRedditMenu();
            }
            else
            {
                clearScreen();
                break;
            }
        }
    }

    public static void manageSubReddit()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the SubReddit name:");
        String subRedditName = input.nextLine();
        subReddit.setName(subRedditName);
        System.out.println("Enter the password:");
        String inputPassword = input.nextLine();
        subReddit.setSubRedditPassword(inputPassword);
        List<String> admins= new ArrayList<>();
        File subRedditAdminFile = new File("SubReddit\\" + subReddit.getName() +"\\"+ subReddit.getName() + "Admins");
        try {

            Scanner readAdminFile = new Scanner(subRedditAdminFile);
            while (readAdminFile.hasNextLine())
                admins.add(readAdminFile.nextLine());
            String password;
            password = admins.getFirst();
            admins.removeFirst();
            if (admins.contains(account.getUserName()))
            {
                if (password.equals(inputPassword))
                {
                    accessToSubReddit();
                }
                else
                {
                    System.out.println("Wrong password");
                    sleep(2000);
                    clearScreen();
                }
            }
            else {
                System.out.println("You are not an admin");
                sleep(2000);
                clearScreen();
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("such a subReddit does not exist");
        }
    }
    // first line of admin file is password and the second line is the subreddit creator
    public static void showSubRedditUserHasJoined()
    {   File  joinedSubReddit = new File("users\\" + account.getUserName() + "\\" + account.getUserName() + "JoinedSubReddit");
        Scanner userInput = new Scanner(System.in);
        String data;
        try
        {
            Scanner readSubRedditUserHasJoined = new Scanner(joinedSubReddit);
            while (readSubRedditUserHasJoined.hasNextLine())
            {
                data = readSubRedditUserHasJoined.nextLine();
                System.out.println("_" + data);
            }
            System.out.println("Press 'E' to access to subReddit\nPress 'Q' to return to SubReddit Menu");
            char userChoice = userInput.next().charAt(0);
            if (userChoice == 'e')
            {   Scanner input = new Scanner(System.in);
                System.out.println("Enter SubReddit name:");
                data = input.nextLine();
                File subRedditInterface = new File("SubReddit\\" + data +"\\"+ data + "Posts");
                Scanner readSubRedditInterface = new Scanner(subRedditInterface);
                while (readSubRedditInterface.hasNextLine())
                {
                    data = readSubRedditInterface.nextLine();
                    System.out.println(data);
                }
                System.out.println("__________________");
            }
            else if(userChoice == 'q')
                return;
        }
        catch (IOException e)
        {
            System.out.println("could not find joinedSubReddit file");
        }
    }
    // this method displays the subReddit user has joined and based on the user choice returns to previous menu or gives access to subReddit posts
    public static void joinSubReddit()
    {
        // name is initialize during the search to prevent name from staying null
        File subRedditData = new File("SubReddit\\" + subReddit.getName() +"\\"+ subReddit.getName() + "members");
        File  joinedSubReddit = new File("users\\" + account.getUserName() + "\\" + account.getUserName() + "JoinedSubReddit");
        try {
            List<String> memberShip = new ArrayList<>();
            Scanner readSubRedditMemberData = new Scanner(subRedditData);
            while (readSubRedditMemberData.hasNextLine())
                 memberShip.add(readSubRedditMemberData.nextLine());
            if (!memberShip.contains(account.getUserName()))
            {
                try {
                    FileWriter writer = new FileWriter(subRedditData, true);
                    writer.write(account.getUserName() + "\n");
                    writer.close();
                    writer = new FileWriter(joinedSubReddit,true);
                    writer.write(subReddit.getName() + "\n");
                    writer.close();
                    System.out.println("You have joined successfully");
                    sleep(2000);
                    clearScreen();
                }
                catch (IOException e)
                {
                    System.out.println("could not create members file");
                }
            }
            else {
                System.out.println("You have already joined this SubReddit");
                sleep(2000);
                clearScreen();
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Could not read the member file");
        }
    }
    // the file writer in this method writs to both of userSubreddit file and subreddit user file as a user joins the subreddit
    public static void createSubreddit()
    {
        String data;
        Scanner input = new Scanner(System.in);
        System.out.println("Choose a SubReddit name");
        data = input.nextLine();
        subReddit.setName(data);
        clearScreen();
        System.out.println("What should be the topic");
        data = input.nextLine();
        subReddit.setTopic(data);
        subReddit.setNewAdmin(account.getUserName());
        clearScreen();
        System.out.println("Enter subReddit password");
        data = input.nextLine();
        subReddit.setSubRedditPassword(data);
        clearScreen();
        sleep(2000);
        clearScreen();
        File subRedditName = new File("SubReddit\\SubRedditNames");
        File subRedditData = new File("SubReddit\\" + subReddit.getName() +"\\"+ subReddit.getName() + "members");
        File subRedditAdminFile = new File("SubReddit\\" + subReddit.getName() +"\\"+ subReddit.getName()  + "Admins");// first line in this file is subReddit name and the second line is the owner username
        File subRedditPostFile = new File("SubReddit\\" + subReddit.getName() +"\\"+ subReddit.getName()  + "Posts");
        // the if does not work fix it
        if (!checkTheFileForLine(subRedditName, subRedditName.getName()))
        {
            boolean success = subRedditPostFile.getParentFile().mkdir();
            try {

                success = subRedditData.createNewFile();
                success = subRedditPostFile.createNewFile();
                success = subRedditAdminFile.createNewFile();
                success = subRedditPostFile.createNewFile();
                FileWriter writer;
                writer = new FileWriter(subRedditName, true);
                writer.write(subReddit.getName() + "\n");// may need to add tru to the arguments of constructor
                writer.close();
                writer = new FileWriter(subRedditAdminFile, true);
                writer.write(subReddit.getSubRedditPassword() + "\n");
                writer.write(account.getUserName() + "\n");
                writer.close();
                System.out.println("Your SubReddit is crated successfully");
            } catch (IOException e) {
                System.out.println("Fail to create a SubReddit");
            }
        }
        else
        {
            System.out.println("this SubReddit already exists");
            sleep(2000);
            clearScreen();
        }
    }
    // when a subReddit is created according to this a directory and a file which have the same name with subReddit will be created to
    // the file includes subReddit name and its topic and the posts in it
    //and also a file will be created where the subreddit password and admin names are saved
    public static void searchForSubReddit(String subRedditName)
    {
        File subRedditNames = new File("SubReddit\\SubRedditNames");
            try {
                if(checkTheFileForLine(subRedditNames, subRedditName))
                {   subReddit.setName(subRedditName);// subReddit name is assigned here to use in joining to subReedits method
                    File subReddit = new File("SubReddit\\" + subRedditName +"\\" + subRedditName + "Posts");
                    Scanner readSubReddit = new Scanner(subReddit);
                    String data;
                    String username = "";
                    String postTitle = "";
                    while(readSubReddit.hasNextLine())
                    {   username = "";
                        postTitle = "";
                        data = readSubReddit.nextLine();
                        int i = 0;
                        while (data.charAt(i) != ',')
                        {
                                username += data.charAt(i);
                                i++;
                        }
                        i++;
                        while (i < data.length()) {
                            postTitle += data.charAt(i);
                            i++;
                        }
                        File postFile = new File( "Posts\\" + username +"\\"+ username + "_" +  subRedditName + "\\" + postTitle);
                        try
                        {
                            Scanner readPostFile = new Scanner(postFile);
                            String print;
                            while (readPostFile.hasNextLine())
                            {
                                print = readPostFile.nextLine();
                                System.out.println(print);
                            }
                            System.out.println("___________________________");

                        }
                        catch (IOException e)
                        {
                            System.out.println("could not read postFile");
                        }
                    }
                }
                else
                {
                    System.out.println("such a SubReddit does not exist");
                }
            }
            catch (IOException e)
            {
                System.out.println("could not read subRedditPost file in search for subReddit method");
            }
    }
    // this method searches subReddit based on their name
    // and prints the posts in them
    public static int displaySubRedditMenu()
    {
        Scanner input = new Scanner(System.in);
        clearScreen();
        System.out.println("1 _ Search for SubReddit <--\n2 _ Create your own SubReddit\n3 _ SubReedits you have joined\n4 _ Manage your SubReddit\n5 _ Return to Main Menu");
        int pointer = 1;
        while (true)
        {
            char ch = input.next().charAt(0);
            if(ch == 'w') {
                pointer--;
                clearScreen();
            }
            else if(ch == 's')
            {
                pointer++;
                clearScreen();
            }
            if (pointer % 5 == 1) {
                clearScreen();
                System.out.println("1 _ Search for SubReddit <--\n2 _ Create your own SubReddit\n3 _ SubReedits you have joined\n4 _ Manage your SubReddit\n5 _ Return to Main Menu");
            }
            else if (pointer % 5 == 2)
            {
                clearScreen();
                System.out.println("1 _ Search for SubReddit\n2 _ Create your own SubReddit <--\n3 _ SubReedits you have joined\n4 _ Manage your SubReddit\n5 _ Return to Main Menu");
            }
            else if (pointer % 5 == 3)
            {
                clearScreen();
                System.out.println("1 _ Search for SubReddit\n2 _ Create your own SubReddit\n3 _ SubReedits you have joined <--\n4 _ Manage your SubReddit\n5 _ Return to Main Menu");
            }
            else if(pointer % 5 == 4)
            {
                clearScreen();
                System.out.println("1 _ Search for SubReddit\n2 _ Create your own SubReddit\n3 _ SubReedits you have joined\n4 _ Manage your SubReddit <--\n5 _ Return to Main Menu");
            }
            else
            {
                clearScreen();
                System.out.println("1 _ Search for SubReddit\n2 _ Create your own SubReddit\n3 _ SubReedits you have joined\n4 _ Manage your SubReddit\n5 _ Return to Main Menu <--");
            }
            if (ch == 'e')
            {
                break;
            }
        }
        return pointer % 5;
    }

    public static void giveCommentOnSubReddit()
    {
        String str;
        String temp;// using to give the write path to the file write in give comment method
        Scanner getComment = new Scanner(System.in);
        clearScreen();
        System.out.println("Enter the subReddit name of the post :");
        str = getComment.nextLine();
        post.setSubRedditName(str);
        System.out.println("Enter the username of the post creator");
        str = getComment.nextLine();
        post.setUserName(str);
        System.out.println("Enter the title of the post by : " + post.getUserName());
        str = getComment.nextLine();
        post.setTitle(str);
        File postComments = new File("Posts\\" + post.getUserName() + "\\" +  post.getUserName() + "_" + post.getSubRedditName() + "\\" + post.getTitle() + "Comments");
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

    public static void userInteractionWithSubReddit()
    {
        int order = displaySubRedditMenu();
        Scanner input = new Scanner(System.in);
        Scanner input_2 = new Scanner(System.in);
        while (true)
        {
            clearScreen();
            if (order == 1)
            {
                System.out.println("Enter SubReddit name:");
                String subRedditNameForSearch = input_2.nextLine();
                searchForSubReddit(subRedditNameForSearch);
                clearScreen();
                System.out.print("""
                        Press 'E' if you want to join to this Subreddit
                        Press 'Q' if you want to return to the previous menu 
                        Press 'C' to access to comment section
                        Press 'K' if you want to give karam
                        """);
                char ch = input.next().charAt(0);
                if (ch == 'q')
                {
                    clearScreen();
                    order = displaySubRedditMenu();

                }
                else if (ch == 'e')
                {
                    joinSubReddit();
                    clearScreen();
                    order = displaySubRedditMenu();
                }
                else if(ch == 'c')
                {
                    giveCommentOnSubReddit();
                    order = displaySubRedditMenu();
                }
                else if(ch == 'k')
                {
                    giveKarma();
                    order = displaySubRedditMenu();
                }
            }
            else if(order == 2)
            {
                    createSubreddit();
                    clearScreen();
                    System.out.println("Press 'Q' if you want to return to the previous menu");
                    char ch = input.next().charAt(0);
                    if (ch == 'q') {
                        clearScreen();
                        order = displaySubRedditMenu();
                    }// remember to add a break statement
                else {
                    System.out.println("This subReddit with this name already exists");
                    sleep(2000);
                    clearScreen();
                }
             }
            else if (order == 3)
            {
                showSubRedditUserHasJoined();
                //giveCommentOnSubReddit();
                clearScreen();
                order = displaySubRedditMenu();
            }
            else if (order == 4)
            {
                manageSubReddit();
                clearScreen();
                order = displaySubRedditMenu();
            }
            else {
                System.out.println();
                clearScreen();
                break;
            }
        }
    }

    public static void interactionWithTimeLine()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Press 'C' if you want to comment\nPress 'E' if you want Create your own post\nPress 'K' if you want to \"up vote\" or \"down vote\"\nPress 'Q' to return to main menu");
        char ch = input.next().charAt(0);
        if (ch == 'c')
        {
            clearScreen();
            giveCommentOnSubReddit();
        }
        else if (ch == 'e')
        {
            clearScreen();
            createPost();
        }
        else if(ch == 'q')
        {
            clearScreen();
            return;
        }
        else if (ch == 'k')
        {
            giveKarma();
        }
    }

    public static void seeTimeLine()
    {   File timeLine = new File("users\\" + account.getUserName() + "\\" + account.getUserName() + "TimeLine");
        File postKarma = new File("Posts\\" + account.getUserName() +"\\" + post.getTitle() + "Karma");
        try {
            Scanner readTimeLineFile = new Scanner(timeLine);
            String data = null;
            int i;
            String username;
            String postTitle;
            String subRedditName;
            while (readTimeLineFile.hasNextLine())
            {   subRedditName = "";
                username = "";
                postTitle = "";
                i = 0;
                data =readTimeLineFile.nextLine();
                while (data.charAt(i) != ',')
                {
                    subRedditName += data.charAt(i);
                    i++;
                }
                i++;
                while (data.charAt(i) != ',')
                {
                    username += data.charAt(i);
                        i++;
                }
                i++;
                while (i < data.length())
                {
                    postTitle += data.charAt(i);
                    i++;
                }
                File postFile = new File( "Posts\\" + username +"\\"+ username + "_" +  subRedditName + "\\" + postTitle);
                try
                {
                    Scanner readPostFile = new Scanner(postFile);
                    String print;
                    while (readPostFile.hasNextLine())
                    {
                        print = readPostFile.nextLine();
                        System.out.println(print);
                    }
                    System.out.println("___________________________");
                }
                catch (IOException e)
                {
                    System.out.println("could not read postFile");
                }

            }
            System.out.println();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("could not read the timeline file in see time line method");
        }
        interactionWithTimeLine();
    }

    //___________________________________________________________________________________________________________________________________________
     public static int displayMainMenu()
     {
         Scanner input = new Scanner(System.in);
         int pointer = 1;
         clearScreen();
         System.out.println("1 _ Your profile <--\n2 _ Subreddits\n3 _ Timeline\n4 _ posts\n5 _ log out");
         while(true)
         {
             char ch = input.next().charAt(0);
             if(ch == 'w')
                 pointer--;
             else if (ch == 's')
                 pointer++;
             else if (ch == 'e') {
                 break;
             }
             switch (pointer % 5)
             {
                 case 0:
                     clearScreen();
                     System.out.println("1 _ Your profile\n2 _ Subreddits\n3 _ Timeline\n4 _ posts\n5 _ log out <--");
                     break;
                 case 1:
                     clearScreen();
                     System.out.println("1 _ Your profile <--\n2 _ Subreddits\n3 _ Timeline\n4 _ posts\n5 _ log out");
                     break;
                 case 2:
                     clearScreen();
                     System.out.println("1 _ Your profile\n2 _ Subreddits <--\n3 _ Timeline\n4 _ posts\n5 _ log out");
                     break;
                 case 3:
                     clearScreen();
                     System.out.println("1 _ Your profile\n2 _ Subreddits\n3 _ Timeline <--\n4 _ posts\n5 _ log out");
                     break;
                 case 4:
                     clearScreen();
                     System.out.println("1 _ Your profile\n2 _ Subreddits\n3 _ Timeline\n4 _ posts <--\n5 _ log out");
                     break;
             }
         }
         return pointer % 5;
     }

     public static void mainMenuAccessing()
     {
         File file = new File("Posts\\" + account.getUserName() + "\\" + post.getTitle());
         int order = displayMainMenu();
         while (true)
         {
             switch (order)
             {
                 case 2:
                     userInteractionWithSubReddit();
                     order = displayMainMenu();
                     break;
                 case 3:
                     seeTimeLine();
                     order = displayMainMenu();
                     break;
                 case 4:

                     seePost();
                     order = displayMainMenu();
                     break;
             }
             if (order == 0)
             {
                 clearScreen();
                 break;
             }
         }
     }
//_______________________________________________________ACCESSING PROCESS_______________________________________________________________

    public static String readFile()
    {
        try {
            File file = new File("users\\" + account.getUserName() + "\\" + account.getUserName());
            String data = null;
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine())
            {
                data += myReader.nextLine();
            }
            myReader.close();
            return data;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("could not read the account file");
        }
        return null;
    }

    public static void writeToFile()
    {
        File file = new File("users\\" + account.getUserName() + "\\" + account.getUserName());
        File  joinedSubReddit = new File("users\\" + account.getUserName() + "\\" + account.getUserName() + "JoinedSubReddit");
        File timeLine = new File("users\\" + account.getUserName() + "\\" + account.getUserName() + "TimeLine");
        File user = new File("users\\userNames");
        try {
                //if (!checkTheFileForLine(user, account.getUserEmail()))
                //{
                    if (file.getParentFile().mkdir()) {
                        boolean success = file.createNewFile();
                        success = joinedSubReddit.createNewFile();
                        success = timeLine.createNewFile();
                        FileWriter myWriter = new FileWriter(file, true);
                        myWriter.write(account.getUserName() + "\n");
                        myWriter.write(account.getUserEmail() + "\n");
                        myWriter.write(account.getPassword());
                        myWriter.close();
                        myWriter = new FileWriter(user, true);
                        myWriter.write(account.getUserName() + "\n");
                        myWriter.close();
                        System.out.println("Your account was made successfully");
                    } else {
                        System.out.println("You already hava an account");
                    }
                //}
                //else

        }
        catch (IOException e)
        {
            System.out.println("something went wrong");
        }
    }
    // when an account is created in users directory at database another folder gets created which has the same name as username
    // and that folder contains two file(userAccount data and subreddits the user has joined)
    // userNames file is in here too
    public static void createAccount()
    {
        Scanner input = new Scanner(System.in);
        clearScreen();
        System.out.println("Please enter your email to create an account");
        String email = input.nextLine();
        account.setUserEmail(email);
        clearScreen();
        System.out.println("please choose a user name for yourself");
        String userName = input.next();
        account.setUserName(userName);
        File user = new File("users\\userNames");
        if (!checkTheFileForLine(user, account.getUserName()))
        {
            if (account.validateEmail()) {
                Scanner input2 = new Scanner(System.in);
                clearScreen();
                System.out.println("Please enter your password to create an account");
                String password = input2.nextLine();
                account.setPassword(password);
                clearScreen();
                sleep(2000);
                writeToFile();
            } else {
                clearScreen();
                System.out.println("Wrong format for an email");
                sleep(2000);
                clearScreen();
            }
        }
        else
        {
            System.out.println("this user name already exists please choose another one");
        }
    }

    public static void login ()
    {
        while (true)
        {
            Scanner input = new Scanner(System.in);
            clearScreen();
            String data;
            String str;
            String tempData = null;
            System.out.println("please enter your username");
            str = input.nextLine();
            tempData += str;
            account.setUserName(str);
            clearScreen();
            System.out.println("Please enter your email");
            str = input.nextLine();
            tempData += str;
            account.setUserEmail(str);
            clearScreen();
            System.out.println("Please enter your password");
            str = input.nextLine();;
            tempData += str;
            account.setPassword(str);
            clearScreen();
            data = readFile();
            if (data.equals(tempData)) {
                System.out.println("Welcome");
                sleep(2000);
                clearScreen();
                mainMenuAccessing();
                break;
            }
            else {
                System.out.println("Password and email does not match any");
                sleep(2000);
            }
        }
    }

//________________________________________________________________________________________________________________________________

    public static int displayAccessingMenu()
    {
        Scanner input = new Scanner(System.in);
        clearScreen();
        int pointer = 1;
        int printOnce = 1;
        while (true)
        {
            if(printOnce == 1)
            {
                System.out.println("1 _ Account creation <--\n2 _ User authentication");
                printOnce++;
            }
            char ch = input.next().charAt(0);
            if(ch == 'w') {
                pointer++;
                clearScreen();
            }
            else if(ch == 's')
            {
                pointer--;
                clearScreen();
            }
            if (pointer % 2 == 1) {
                clearScreen();
                System.out.println("1 _ Account creation <--\n2 _ User authentication");
            }
            else
            {
                clearScreen();
                System.out.println("1 _ Account creation\n2 _ User authentication <--");
            }
            if (ch == 'e')
            {
                break;
            }
        }
        return pointer % 2;
    }

    public static void accessingProcess()
    {
      int order = displayAccessingMenu();
      Scanner input = new Scanner(System.in);
      while (true)
      {
          clearScreen();
          if (order == 1)
          {
           createAccount();
           clearScreen();
           clearScreen();
           order = displayAccessingMenu();

          }
          else
          {
              login();
              clearScreen();
              order = displayAccessingMenu();
              // remember to add a break statement
          }
      }
    }

//______________________________________________________________________________________________________________________________
    public static void main(String[] args) throws NullPointerException{
        accessingProcess();
    }
}
