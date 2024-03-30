
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
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

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

    public static void removeLineFromFile(File file, String line)
    {
        try
        {
            File tempFile = new File("myTempFile.txt");

            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if(trimmedLine.equals(line)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            tempFile.renameTo(file);
        }
        catch (IOException e)
        {
            System.out.println("FileNotFoundException from removeLineFromFile method");
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
//__________________________________________________________POSTING AND COMMENTING PROCESS_____________________________________________________
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
                   writer.write("\\r" + post.getSubRedditName() + "\n");
                   writer.write("\\u"+ post.getUserName() + "\n");
                   writer.write("Title: " + post.getTitle() + "\n");
                   writer.write(post.getBody() + "\n");
                   writer.write("_______________________________\n");
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
        try{
            List<String> list = new ArrayList<>();
            File file = new File("SubReddit\\SubRedditNames");
            Scanner input = new Scanner(file);
            while (input.hasNextLine())
            {
                list.add(input.nextLine());
            }
            if (list.contains(post.getSubRedditName())) {
                file = new File("SubReddit\\" + post.getSubRedditName()+ "\\"+ post.getSubRedditName());
                FileWriter writer = new FileWriter(file, true);
                writer.write("\\u" + post.getUserName() + "\n");// might make a problem
                if (post.getTitle() != null)
                     writer.write(post.getTitle() + "\n");
                if(post.getBody() != null)
                     writer.write(post.getBody() + "\n");
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

        if (checkTheFileForLine(subRedditNames, post.getSubRedditName()))
        {
           File file = new File("Posts\\" + account.getUserName() + "\\" + post.getTitle());// this is the post file name. the file where the post details are
           try {
               if (file.createNewFile())
               {
                   String data = post.getUserName() + "\n" + post.getSubRedditName() + "\n" + post.getTitle() + "\n" + post.getBody();
                   Path fileName = Path.of(file.toURI());
                   Files.writeString(fileName, data);
                   System.out.println("Your post with title "+ post.getTitle() + " is created successfully");
               }
               else
               {
                   System.out.println("You already have this title");
               }
           } catch (IOException e) {
               System.out.println("Something went wrong");
           }
       }
        else
            post.setFirstPost(true);
    }
    // write to post file and checks to nor create a post if its subReddit is not created before
    public static void createDirectory()
    {
        File file = new File("Posts\\" + account.getUserName()+"\\"+ account.getUserName() + "PostTitles");
        File postFile = new File("Posts\\" + account.getUserName() + "\\" + post.getTitle());// this is the post file name. the file where the post details are
        file.getParentFile().mkdir();
        writeToPostFile();
        try {
            if (postFile.exists())
            {
                file.createNewFile();
                String data = post.getTitle();
                Path fileName = Path.of(file.toURI());
                Files.write(fileName,
                        (data + "\n").getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
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
        File file = new File("Posts\\" + account.getUserName() +"\\" + post.getTitle() + "Comments");
        File subRedditFile = new File("SubReddit\\SubRedditNames");
        try{
            if (checkTheFileForLine(subRedditFile, post.getSubRedditName()))
            {
                file.createNewFile();
                FileWriter myWriter = new FileWriter(file);
                myWriter.write("Comment section :\n");
                myWriter.close();
                addPostTimeline();
            }
            else
                post.setFirstPost(true);
        }
        catch (IOException e)
        {
            System.out.println("Comment section is not created");
        }
    }

    public static void showPostDetails(String title)
    {
        File file = new File("Posts\\" + account.getUserName() +"\\"+ account.getUserName() +"PostTitles");
        try {
            if(checkTheFileForLine(file, title))
            {
                String data;
                File search = new File("Posts\\" + account.getUserName()+"\\"+ title);
                Scanner fileReader_2 = new Scanner(search);
                while (fileReader_2.hasNextLine()) {
                    data = fileReader_2.nextLine();
                    System.out.println(data);
                }
                fileReader_2.close();
            } else {
                System.out.println("There is no such a title");
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
                System.out.println("Enter the post title");
                String title;
                Scanner input_2 = new Scanner(System.in);
                title = input_2.nextLine();
                showPostDetails(title);
                post.setTitle(title);
                clearScreen();
                System.out.println("Press 'Q' to exit");
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
        System.out.println("Please enter your user name to comment");
        String str = input.nextLine();
        comment.setUserName(str);
        System.out.println("Write your comment");
        str = input.nextLine();
        comment.setBody(str);
        File file = new File("Posts\\" + account.getUserName() +"\\" + post.getTitle() + "Comments");
        try
        {
            FileWriter myWriter = new FileWriter(file,true);
            myWriter.write(comment.getUserName() + "\n");
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
            File file = new File("Posts\\" + account.getUserName() +"\\" + post.getTitle() + "Comments");
            try {
                Scanner commentReader = new Scanner(file);
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

    }

    public static void deleteComment()
    {

    }

    public static void deletePost()
    {
        File subRedditFile = new File("SubReddit\\" + subReddit.getName() + "\\" + subReddit.getName());
        String creator;
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the post creator userName:");
        creator = input.nextLine();
        creator = "\\u" + creator;
        List<String> subReddirList = new ArrayList<>();
        try {
            Scanner readFromSubRedditFile = new Scanner(subRedditFile);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("could not read the subReddit file int deletePost method" );
        }
    }

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
        int printOnce = 1;
        while (true)
        {
            if(printOnce == 1)
            {
                System.out.println("1 _ Add a new Admin <--\n2 _ Delete a post\n3 _ Delete a comment\n4 _ Kick a user\n5 _ Return to Main Menu");
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
        try
        {
            Scanner readSubRedditUserHasJoined = new Scanner(joinedSubReddit);
            while (readSubRedditUserHasJoined.hasNextLine())
            {
                String data;
                data = readSubRedditUserHasJoined.nextLine();
                File subRedditInterface = new File("SubReddit\\" + data +"\\"+ data);
                Scanner readSubRedditInterface = new Scanner(subRedditInterface);
                while (readSubRedditInterface.hasNextLine())
                {
                    data = readSubRedditInterface.nextLine();
                    System.out.println(data);
                }
                System.out.println("__________________");
            }
        }
        catch (IOException e)
        {
            System.out.println("could not find joinedSubReddit file");
        }
    }

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
        System.out.println("Your SubReddit is crated successfully");
        sleep(2000);
        clearScreen();
        File subRedditInterface = new File("SubReddit\\" + subReddit.getName() +"\\"+ subReddit.getName());
        File subRedditName = new File("SubReddit\\SubRedditNames");
        File subRedditData = new File("SubReddit\\" + subReddit.getName() +"\\"+ subReddit.getName() + "members");
        File subRedditAdminFile = new File("SubReddit\\" + subReddit.getName() +"\\"+ subReddit.getName()  + "Admins");
        subRedditInterface.getParentFile().mkdir();
        try {

            subRedditData.createNewFile();
            subRedditInterface.createNewFile();
            subRedditAdminFile.createNewFile();
            FileWriter writer = new FileWriter(subRedditInterface,true);// may need to add true to the arguments of constructor
            writer.write(subReddit.getName() + "\n");
            writer.write("Topic: " + subReddit.getTopic() + "\n");
            writer.close();
            writer = new FileWriter(subRedditName,true);
            writer.write( subReddit.getName() + "\n");// may need to add tru to the arguments of constructor
            writer.close();
            writer = new FileWriter(subRedditAdminFile, true);
            writer.write(subReddit.getSubRedditPassword() + "\n");
            writer.write(account.getUserName() + "\n");
            writer.close();
        }
        catch (IOException e)
        {
            System.out.println("Fail to create a SubReddit");
        }
    }
    // when a subReddit is created according to this a directory and a file which have the same name with subReddit will be created to
    // the file includes subReddit name and its topic and the posts in it
    //and also a file will be created where the subreddit password and admin names are saved
    public static void searchForSubReddit(String subRedditName)
    {
        File subRedditNames = new File("SubReddit\\SubRedditNames");
        try {
            Scanner fileReader = new Scanner(subRedditNames);
            List<String> findSubReddit = new ArrayList<>();
            while (fileReader.hasNextLine())
            {
                findSubReddit.add(fileReader.nextLine());
            }
            if(findSubReddit.contains(subRedditName))
            {   subReddit.setName(subRedditName);// subReddit name is assigned here to use in joining to subReedits method
                File subReddit = new File("SubReddit\\" + subRedditName +"\\" + subRedditName);
                Scanner readSubReddit = new Scanner(subReddit);
                String data;
                int lineCounter = 2;
                while(readSubReddit.hasNextLine())
                {
                    data = readSubReddit.nextLine();
                    System.out.println(data);
                    if (lineCounter % 3 == 0)
                        System.out.println("_________________________");
                    lineCounter ++;
                }
            }
            else
            {
                System.out.println("such a SubReddit does not exist");
            }
        }
        catch (FileNotFoundException  e)
        {
            System.out.println("could not read SubRedditNames");
        }
    }
    // this method searches subReddit based on their name
    // and prints the posts in them
    public static int displaySubRedditMenu()
    {
        Scanner input = new Scanner(System.in);
        clearScreen();
        int pointer = 1;
        int printOnce = 1;
        while (true)
        {
            if(printOnce == 1)
            {
                System.out.println("1 _ Search for SubReddit <--\n2 _ Create your own SubReddit\n3 _ SubReedits you have joined\n4 _ Manage your SubReddit\n5 _ Return to Main Menu");
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
        System.out.println("Enter the username of the post creator");
        str = getComment.nextLine();
        temp = account.getUserName();
        account.setUserName(str);// possibly there will be no need for this
        System.out.println("Enter the title of the post by : " + account.getUserName());
        str = getComment.nextLine();
        post.setTitle(str);
        File file = new File("Posts\\" + account.getUserName() +"\\" + post.getTitle() + "Comments");
        try {
            Scanner commentReader = new Scanner(file);
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
        account.setUserName(temp);
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
                String subRedditName = input_2.nextLine();
                searchForSubReddit(subRedditName);
                clearScreen();
                System.out.print("""
                        Press 'E' if you want to join to this Subreddit
                        Press 'Q' if you want to return to the previous menu 
                        Press C to access to comment section
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
            }
            else if(order == 2)
            {
                createSubreddit();
                clearScreen();
                System.out.println("Press 'Q' if you want to return to the previous menu");
                char ch = input.next().charAt(0);
                if (ch == 'q')
                {   clearScreen();
                    order = displaySubRedditMenu();
                }// remember to add a break statement
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
        System.out.println("Press 'C' if you want to comment\nPress 'E' if you want Create your own post\nPress 'Q' to return to main menu");
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
    }

    public static void seeTimeLine()
    {   File timeLine = new File("users\\" + account.getUserName() + "\\" + account.getUserName() + "TimeLine");
        try {
            Scanner readTimeLineFile = new Scanner(timeLine);
            String data;
            while (readTimeLineFile.hasNextLine())
            {
                data = readTimeLineFile.nextLine();
                System.out.println(data);
            }
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
         int printOnce = 1;
         if (printOnce == 1)
         {   clearScreen();
             System.out.println("1 _ Your profile <--\n2 _ Subreddits you hava joined\n3 _ Timeline\n4 _ Your posts");
             printOnce++;
         }
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
             switch (pointer % 4)
             {
                 case 0:
                     clearScreen();
                     System.out.println("1 _ Your profile\n2 _ Subreddits you hava joined\n3 _ Timeline\n4 _ Your posts <--");
                     break;
                 case 1:
                     clearScreen();
                     System.out.println("1 _ Your profile <--\n2 _ Subreddits you hava joined\n3 _ Timeline\n4 _ Your posts");
                     break;
                 case 2:
                     clearScreen();
                     System.out.println("1 _ Your profile\n2 _ Subreddits you hava joined <--\n3 _ Timeline\n4 _ Your posts");
                     break;
                 case 3:
                     clearScreen();
                     System.out.println("1 _ Your profile\n2 _ Subreddits you hava joined\n3 _ Timeline <--\n4 _ Your posts");
                     break;
             }
         }
         return pointer % 4;
     }

     public static void mainMenuAccessing()
     {
         File file = new File("Posts\\" + account.getUserName() + "\\" + post.getTitle());
         int order = displayMainMenu();
         while (true)
         {
             switch (order)
             {
                 case 0:

                     seePost();
                     order = displayMainMenu();
                     break;
                 case 2:
                     userInteractionWithSubReddit();
                     order = displayMainMenu();
                     break;
                 case 3:
                     seeTimeLine();
                     order = displayMainMenu();
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
            if(file.getParentFile().mkdir())
            {
                file.createNewFile();
                joinedSubReddit.createNewFile();
                timeLine.createNewFile();
                FileWriter myWriter = new FileWriter(file, true);
                myWriter.write(account.getUserName()+ "\n");
                myWriter.write(account.getUserEmail()+ "\n");
                myWriter.write(account.getPassword());
                myWriter.close();
                myWriter = new FileWriter(user);
                myWriter.write(account.getUserName());
                myWriter.close();
            }
            else
            {
                System.out.println("You already hava an account");
            }
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
        if(account.validateEmail())
        {   Scanner input2 = new Scanner(System.in);
            clearScreen();
            System.out.println("Please enter your password to create an account");
            String password = input2.nextLine();
            account.setPassword(password);
            clearScreen();
            System.out.println("Your account was made successfully");
            sleep(2000);
        }
        else
        {
            clearScreen();
            System.out.println("Wrong format for an email");
            sleep(2000);
            clearScreen();
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
              writeToFile();
              System.out.println("Press 'Q' if you want to return to the previous menu 1 ");
              char ch = input.next().charAt(0);
              if (ch == 'q')
                {
                    clearScreen();
                    order = displayAccessingMenu();

                }

          }
          else
          {
              login();
              clearScreen();
              System.out.println("Press 'Q' if you want to return to the previous menu 2");
              char ch = input.next().charAt(0);
              if (ch == 'q')
              {   clearScreen();
                  order = displayAccessingMenu();
              }// remember to add a break statement
          }
      }
    }

//______________________________________________________________________________________________________________________________
    public static void main(String[] args) throws NullPointerException{
        accessingProcess();
    }
}
