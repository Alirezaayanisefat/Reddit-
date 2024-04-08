import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SubReddit {
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
    //__________________________________________________________________________
    private String name;
    private String topic;
    private String subRedditPassword;
    private final List <String> adminList = new ArrayList<>();
    public void setName(String name) {
        this.name = name;
    }

    public void setSubRedditPassword(String mainAdmin) {
        this.subRedditPassword = mainAdmin;
    }

    public String getSubRedditPassword() {
        return subRedditPassword;
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
        return adminList.contains(userName);
    }
    //_____________________________________________________________________
    public static int determineKarma()
    {
        int upVotes = 0;
        int downVotes = 0;
        List<String> votsList = new ArrayList<>();
        File postKarma = new File("Posts\\" + Attributes.post.getUserName() + "\\" + Attributes.post.getTitle() + "Karma");
        System.out.println(postKarma.getPath());
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
                    downVotes ++;
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("could no calculate the karma in determineKarmaMethod");
        }
        return (upVotes - downVotes);
    }

    public static String getUerKarmaVote()
    {
        File postKarma = new File("Posts\\" + Attributes.post.getUserName() + "\\" + Attributes.post.getTitle() + "Karma");
        Scanner input = new Scanner(System.in);
        int previousKarna = determineKarma();
        String oldKarma = Integer.toString(previousKarna);
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
            System.out.println("could not find the postKarma file in getUser karma method");
        }
        clearScreen();
        return oldKarma;
    }// this method gets user vote

    public static void giveKarma()
    {
        clearScreen();
        Scanner userInput = new Scanner(System.in);
        String postData;
        System.out.println("Please enter the username of the post creator: ");
        postData = userInput.nextLine();
        Attributes.post.setUserName(postData);
        System.out.println("Enter the subReddit name of the post:");
        postData = userInput.nextLine();
        Attributes.post.setSubRedditName(postData);
        System.out.println("Enter the postTitle please: ");
        postData = userInput.nextLine();
        Attributes.post.setTitle(postData);
        File postFile = new File("Posts\\" + Attributes.post.getUserName() + "\\"  + Attributes.post.getTitle() + "_" + Attributes.post.getSubRedditName());
        String oldKarma = getUerKarmaVote();
        int karma = determineKarma();
        String newKarma = Integer.toString(karma);
        replaceLineInFile(postFile, "Karma : " + oldKarma, "Karma : " + newKarma);
    }
    public static void kickUser()
    {
        File subRedditMemberFile = new File("SubReddit\\" + Attributes.subReddit.getName() + "\\" + Attributes.subReddit.getName() + "members");
        System.out.println(subRedditMemberFile);
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the username you want to kick :");
        String userName = input.nextLine();
        removeLineFromFile(subRedditMemberFile.getPath(), userName);
        File joinedSubReddit = new File("user\\" + userName + "\\" + userName + "JoinedSubReddit");
        removeLineFromFile(joinedSubReddit.getPath(), Attributes.subReddit.getName());
        System.out.println("The user is kicked successfully");
        sleep(2000);
        clearScreen();
    }

    public static void deleteComment()
    {   File subRedditPostFile = new File("SubReddit\\" + Attributes.subReddit.getName() +"\\"+ Attributes.subReddit.getName()  + "Posts");
        File postComments = new File("Posts\\" + Attributes.account.getUserName() + "\\" + Attributes.post.getTitle() + "Comments");
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the username of post creator : ");
        String data = input.nextLine();
        Attributes.post.setUserName(data);
        System.out.println("Enter the post title : ");
        data = input.nextLine();
        Attributes.post.setTitle(data);
        System.out.println("Please enter the username of comment creator : ");
        data = input.nextLine();
        Attributes.comment.setUserName(data);
        System.out.println("Enter the comment body : ");
        data = input.nextLine();
        Attributes.comment.setBody(data);
        String lineToRemove ="\\u" + Attributes.comment.getUserName() + " : " + Attributes.comment.getBody();
        if (checkTheFileForLine(subRedditPostFile ,Attributes.post.getUserName() + "," + Attributes.post.getTitle()))
            removeLineFromFile(postComments.getPath(), lineToRemove);
        else
            System.out.println("This post is not on your subReddit");
        System.out.println("The comment is deleted successfully");
    }
    // the admins are not able to delete comments of other posts which are not on their subReddit
    public static void deletePostFromTimeLines(String subRedditName, String creatorUserName, String postTitle)
    {
        File subRedditData = new File("SubReddit\\" + Attributes.post.getSubRedditName() +"\\"+ Attributes.post.getSubRedditName() + "members");
        try {
            Scanner readMembers = new Scanner(subRedditData);
            String memberName;
            while (readMembers.hasNextLine())
            {
                String data = subRedditName + "," + creatorUserName + "," + postTitle;
                memberName = readMembers.nextLine();
                File timeLine = new File("users\\" + memberName + "\\" + memberName + "TimeLine");
                removeLineFromFile(timeLine.getPath(), data);
            }
        }
        catch (IOException e)
        {
            System.out.println("could not read the memberFile in deletePostFromTimeLines method");
        }
    }

    public static void deletePost()
    {
        File subRedditPostFile = new File("SubReddit\\" + Attributes.subReddit.getName() +"\\"+ Attributes.subReddit.getName()  + "Posts");
        //File timeLine = new File("users\\" + account.getUserName() + "\\" + account.getUserName() + "TimeLine");
        String username;
        String postTitle;
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the post creator userName: ");
        username = input.nextLine();
        System.out.println("Enter the Post title: ");
        postTitle = input.nextLine();
        File postFile = new File( "Posts\\" + username +"\\" + "\\" + postTitle + "_" + Attributes.subReddit.getName());
        removeLineFromFile(subRedditPostFile.getPath(), username + "," + postTitle);
        //removeLineFromFile(timeLine, subReddit.getName() + "," + username + "," +postTitle);
        //  deletePostFromTimeLines(subReddit.getName(), username, postTitle);
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
        File subRedditAdminFile = new File("SubReddit\\" + Attributes.subReddit.getName() +"\\"+ Attributes.subReddit.getName() + "Admins");
        File userNames = new File("users\\userNames");
        try {
            Scanner readAdminFile = new Scanner(subRedditAdminFile);
            Scanner input = new Scanner(System.in);
            List<String> admins = new ArrayList<>();
            while (readAdminFile.hasNextLine())
                admins.add(readAdminFile.nextLine());
            if (Attributes.account.getUserName().equals(admins.get(1)))
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
        Attributes.subReddit.setName(subRedditName);
        System.out.println("Enter the password:");
        String inputPassword = input.nextLine();
        Attributes.subReddit.setSubRedditPassword(inputPassword);
        List<String> admins= new ArrayList<>();
        File subRedditAdminFile = new File("SubReddit\\" + Attributes.subReddit.getName() +"\\"+ Attributes.subReddit.getName() + "Admins");
        try {

            Scanner readAdminFile = new Scanner(subRedditAdminFile);
            while (readAdminFile.hasNextLine())
                admins.add(readAdminFile.nextLine());
            String password;
            password = admins.getFirst();
            admins.removeFirst();
            if (admins.contains(Attributes.account.getUserName()))
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
    {
        File  joinedSubReddit = new File("users\\" + Attributes.account.getUserName() + "\\" + Attributes.account.getUserName() + "JoinedSubReddit");
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
        File subRedditData = new File("SubReddit\\" + Attributes.subReddit.getName() +"\\"+ Attributes.subReddit.getName() + "members");
        File  joinedSubReddit = new File("users\\" + Attributes.account.getUserName() + "\\" + Attributes.account.getUserName() + "JoinedSubReddit");
        try {
            List<String> memberShip = new ArrayList<>();
            Scanner readSubRedditMemberData = new Scanner(subRedditData);
            while (readSubRedditMemberData.hasNextLine())
                memberShip.add(readSubRedditMemberData.nextLine());
            if (!memberShip.contains(Attributes.account.getUserName()))
            {
                try {
                    FileWriter writer = new FileWriter(subRedditData, true);
                    writer.write(Attributes.account.getUserName() + "\n");
                    writer.close();
                    writer = new FileWriter(joinedSubReddit,true);
                    writer.write(Attributes.subReddit.getName() + "\n");
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
        Attributes.subReddit.setName(data);
        clearScreen();
        System.out.println("What should be the topic");
        data = input.nextLine();
        Attributes.subReddit.setTopic(data);
        Attributes.subReddit.setNewAdmin(Attributes.account.getUserName());
        clearScreen();
        System.out.println("Enter subReddit password");
        data = input.nextLine();
        Attributes.subReddit.setSubRedditPassword(data);
        clearScreen();
        sleep(2000);
        clearScreen();
        File subRedditName = new File("SubReddit\\SubRedditNames");
        File subRedditData = new File("SubReddit\\" + Attributes.subReddit.getName() +"\\"+ Attributes.subReddit.getName() + "members");
        File subRedditAdminFile = new File("SubReddit\\" + Attributes.subReddit.getName() +"\\"+ Attributes.subReddit.getName()  + "Admins");// first line in this file is subReddit name and the second line is the owner username
        File subRedditPostFile = new File("SubReddit\\" + Attributes.subReddit.getName() +"\\"+ Attributes.subReddit.getName()  + "Posts");
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
                writer.write(Attributes.subReddit.getName() + "\n");// may need to add tru to the arguments of constructor
                writer.close();
                writer = new FileWriter(subRedditAdminFile, true);
                writer.write(Attributes.subReddit.getSubRedditPassword() + "\n");
                writer.write(Attributes.account.getUserName() + "\n");
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
            {   Attributes.subReddit.setName(subRedditName);// subReddit name is assigned here to use in joining to subReedits method
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
                    File postFile = new File( "Posts\\" + username + "\\" + postTitle + "_" + subRedditName);
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
                clearScreen();
                System.out.println("such a SubReddit does not exist");
                sleep(2000);
                clearScreen();
                Attributes.post.setFirstPost(true);
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
                if (Attributes.post.getFirstPost())
                {
                    Attributes.post.setFirstPost(false);
                    return;
                }
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
                    Comment.giveCommentOnSubReddit();
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
            Comment.giveCommentOnSubReddit();
        }
        else if (ch == 'e')
        {
            clearScreen();
            Post.createPost();
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
    {   File timeLine = new File("users\\" + Attributes.account.getUserName() + "\\" + Attributes.account.getUserName() + "TimeLine");
        File postKarma = new File("Posts\\" +Attributes.account.getUserName() +"\\" + Attributes.post.getTitle() + "Karma");
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
                File postFile = new File( "Posts\\" + username + "\\" + postTitle + "_" + subRedditName);
                System.out.println(postFile);
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
}
