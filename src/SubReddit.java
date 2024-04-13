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
        System.out.println(file);
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
    private String subRedditPassword;
    public void setName(String name) {
        this.name = name;
    }
    public void setSubRedditPassword(String mainAdmin) {
        this.subRedditPassword = mainAdmin;
    }

    public String getSubRedditPassword() {
        return subRedditPassword;
    }

    public String getName() {
        return name;
    }

    //_____________________________________________________________________

    public static int determineKarma()
    {
        int upVotes = 0;
        int downVotes = 0;
        List<String> votsList = new ArrayList<>();

        File postKarma = new File("Posts\\" + Attributes.post.getUserName() + "\\" + Attributes.post.getTitle() + "Karma.txt");

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
//    public static String getUerKarmaVote()
//    {
//        File postKarma = new File("Posts\\" + Attributes.post.getUserName() + "\\" + Attributes.post.getTitle() + "Karma");
//        Scanner input = new Scanner(System.in);
//
//        int previousKarna = determineKarma();
//        String oldKarma = Integer.toString(previousKarna);
//
//        System.out.println("Press 'E' if you want to \"up vote\"\npress 'Q' if you want to \"down vote\"");
//        char userVote = input.next().charAt(0);
//        try
//        {
//            if (userVote == 'e')
//            {
//                FileWriter writer = new FileWriter(postKarma, true);
//                writer.write("e\n");
//                writer.close();
//            }
//            else if(userVote == 'q')
//            {
//                FileWriter writer = new FileWriter(postKarma, true);
//                writer.write("q\n");
//                writer.close();
//            }
//
//        }
//        catch (IOException e)
//        {
//            System.out.println("could not find the postKarma file in getUser karma method");
//        }
//        clearScreen();
//        return oldKarma;
//    }// this method gets user vote

    public static String getUerKarmaVote()
    {
        File postKarma = new File("Posts\\" + Attributes.post.getUserName() + "\\" + Attributes.post.getTitle() + "Karma.txt");
        File postUserVote = new File("Posts\\" + Attributes.account.getUserName()  + "\\" + Attributes.post.getTitle() + "userVote.txt");
        Scanner input = new Scanner(System.in);

        int previousKarna = determineKarma();
        String oldKarma = Integer.toString(previousKarna);

        System.out.println("Press 'E' if you want to \"up vote\"\npress 'Q' if you want to \"down vote\"");
        char userVote = input.next().charAt(0);
        try
        {

            if (userVote == 'e')
            {
                if (checkTheFileForLine(postUserVote , Attributes.account.getUserName()))
                {
                    FileWriter writer = new FileWriter(postKarma, true);
                    writer.write("q\n");
                    writer.close();
                    replaceLineInFile(postUserVote,  Attributes.account.getUserName(),"");
                }
                else
                {
                    FileWriter writer = new FileWriter(postKarma, true);
                    writer.write("e\n");
                    writer.close();
                    writer = new FileWriter(postUserVote , true);
                    writer.write(Attributes.account.getUserName() + "\n");
                    writer.close();
                }
            }
            else if(userVote == 'q')
            {
                if (checkTheFileForLine(postUserVote , Attributes.account.getUserName()))
                {
                    FileWriter writer = new FileWriter(postKarma, true);
                    writer.write("e\n");
                    writer.close();
                    replaceLineInFile(postUserVote,  Attributes.account.getUserName(),"");
                }
                else
                {
                    FileWriter writer = new FileWriter(postKarma, true);
                    writer.write("q\n");
                    writer.close();
                    writer = new FileWriter(postUserVote , true);
                    writer.write(Attributes.account.getUserName() + "\n");
                    writer.close();
                }
            }

        }
        catch (IOException e)
        {
            System.out.println("could not find the postKarma file in getUser karma method");
        }
        clearScreen();
        return oldKarma;
    }// this method gets user vote

    public static void giveKarma(File file)
    {

        clearScreen();
        Scanner userInput = new Scanner(System.in);
        String postData;
        System.out.println("Please enter the post number :");
        int postNumber = userInput.nextInt();
        try
        {

            Scanner fileReader = new Scanner(file);
            List<String> list = new ArrayList<>();

            while (fileReader.hasNextLine())
            {
                postData = fileReader.nextLine();
                list.add(postData);
            }
            fileReader.close();

            String post = list.get(postNumber);

            String subRedditName = "";
            String username = "";
            String postTitle = "";
            int i = 0;

            while (post.charAt(i) != ',')
            {
                subRedditName += post.charAt(i);
                i++;
            }
            Attributes.post.setSubRedditName(subRedditName);
            i++;

            while (post.charAt(i) != ',')
            {
                username += post.charAt(i);
                i++;
            }
            Attributes.post.setUserName(username);
            i++;

            while (i < post.length())
            {
                postTitle += post.charAt(i);
                i++;
            }
            Attributes.post.setTitle(postTitle);

            File postFile = new File( "Posts\\" + username + "\\" + postTitle + "_" + subRedditName + ".txt");
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
            }
            catch (IOException e)
            {
                System.out.println("could not read postFile on give karma method");
            }
        }
        catch (IOException e)
        {
            System.out.println("could not give karma");
        }

        File postFile = new File("Posts\\" + Attributes.post.getUserName() + "\\"  + Attributes.post.getTitle() + "_" + Attributes.post.getSubRedditName() + ".txt");
        String oldKarma = getUerKarmaVote();
        int karma = determineKarma();
        String newKarma = Integer.toString(karma);
        replaceLineInFile(postFile, "Karma : " + oldKarma, "Karma : " + newKarma);
    }

    // the admins are not able to delete comments of other posts which are not on their subReddit
    public static void deletePostFromTimeLines(String subRedditName, String creatorUserName, String postTitle)
    {
        File subRedditData = new File("SubReddit\\" + Attributes.post.getSubRedditName() +"\\"+ Attributes.post.getSubRedditName() + "members.txt");
        try {
            Scanner readMembers = new Scanner(subRedditData);
            String memberName;
            while (readMembers.hasNextLine())
            {
                String data = subRedditName + "," + creatorUserName + "," + postTitle;
                memberName = readMembers.nextLine();
                File timeLine = new File("users\\" + memberName + "\\" + memberName + "TimeLine.txt");
               replaceLineInFile(timeLine, data, "");
            }
        }
        catch (IOException e)
        {
            System.out.println("could not read the memberFile in deletePostFromTimeLines method");
        }
    }

    public static void deletePost()
    {
        File subRedditPostFile = new File("SubReddit\\" + Attributes.subReddit.getName() +"\\"+ Attributes.subReddit.getName()  + "Posts.txt");
        //File timeLine = new File("users\\" + account.getUserName() + "\\" + account.getUserName() + "TimeLine");
        String username;
        String postTitle;
        Scanner input = new Scanner(System.in);

        System.out.println("Enter the post creator userName: ");
        username = input.nextLine();

        System.out.println("Enter the Post title: ");
        postTitle = input.nextLine();

        File postFile = new File( "Posts\\" + username +"\\" + "\\" + postTitle + "_" + Attributes.subReddit.getName() + ".txt");
        removeLineFromFile(subRedditPostFile.getPath(), Attributes.subReddit.getName() + "," + username + "," + postTitle);
        //removeLineFromFile(timeLine, subReddit.getName() + "," + username + "," +postTitle);
        deletePostFromTimeLines(Attributes.subReddit.getName(), username, postTitle);
        try
        {
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
        File subRedditAdminFile = new File("SubReddit\\" + Attributes.subReddit.getName() +"\\"+ Attributes.subReddit.getName() + "Admins.txt");
        File userNames = new File("users\\userNames.txt");

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
        System.out.println("1 _ Add a new Admin <--\n2 _ Delete a post\n5 _ Return to SubReddit Menu");
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
            if (pointer % 3 == 1) {
                clearScreen();
                System.out.println("1 _ Add a new Admin <--\n2 _ Delete a post\n3 _ Return to SubReddit Menu");
            }
            else if (pointer % 3 == 2)
            {
                clearScreen();
                System.out.println("1 _ Add a new Admin\n2 _ Delete a post <--\n3 _ Return to SubReddit Menu");
            }
            else if (pointer % 3 == 3)
            {
                clearScreen();
                System.out.println("1 _ Add a new Admin\n2 _ Delete a post\n3 _ Return to SubReddit Menu");
            }
            else
            {
                clearScreen();
                System.out.println("1 _ Add a new Admin\n2 _ Delete a post\n3 _ Return to SubReddit Menu <--");
            }
            if (ch == 'e')
            {
                break;
            }
        }
        return pointer % 3;
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
        File subRedditAdminFile = new File("SubReddit\\" + Attributes.subReddit.getName() +"\\"+ Attributes.subReddit.getName() + "Admins.txt");

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
        File  joinedSubReddit = new File("users\\" + Attributes.account.getUserName() + "\\" + Attributes.account.getUserName() + "JoinedSubReddit.txt");
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
            {
                Scanner input = new Scanner(System.in);
                System.out.println("Enter SubReddit name:");
                data = input.nextLine();

                File subRedditInterface = new File("SubReddit\\" + data +"\\"+ data + "Posts.txt");
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
        File subRedditData = new File("SubReddit\\" + Attributes.subReddit.getName() +"\\"+ Attributes.subReddit.getName() + "members.txt");
        File  joinedSubReddit = new File("users\\" + Attributes.account.getUserName() + "\\" + Attributes.account.getUserName() + "JoinedSubReddit.txt");
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

        System.out.println("Enter subReddit password");
        data = input.nextLine();
        Attributes.subReddit.setSubRedditPassword(data);
        clearScreen();
        sleep(2000);
        clearScreen();

        File subRedditName = new File("SubReddit\\SubRedditNames.txt");
        File subRedditData = new File("SubReddit\\" + Attributes.subReddit.getName() +"\\"+ Attributes.subReddit.getName() + "members.txt");
        File subRedditAdminFile = new File("SubReddit\\" + Attributes.subReddit.getName() +"\\"+ Attributes.subReddit.getName()  + "Admins.txt");// first line in this file is subReddit name and the second line is the owner username
        File subRedditPostFile = new File("SubReddit\\" + Attributes.subReddit.getName() +"\\"+ Attributes.subReddit.getName()  + "Posts.txt");
        // the if does not work fix it

        if (!checkTheFileForLine(subRedditName, subRedditName.getName()))
        {
            boolean success = subRedditPostFile.getParentFile().mkdir();
            try {

                success = subRedditData.createNewFile();
                success = subRedditPostFile.createNewFile();
                success = subRedditAdminFile.createNewFile();
                success = subRedditPostFile.createNewFile();
                if (!checkTheFileForLine(subRedditName, subRedditName.getName()))
                {
                    FileWriter writer;
                    writer = new FileWriter(subRedditName, true);
                    writer.write(Attributes.subReddit.getName() + "\n");// may need to add tru to the arguments of constructor
                    writer.close();

                    writer = new FileWriter(subRedditAdminFile, true);
                    writer.write(Attributes.subReddit.getSubRedditPassword() + "\n");
                    writer.write(Attributes.account.getUserName() + "\n");
                    writer.close();
                    System.out.println("Your SubReddit is crated successfully");
                }
                else
                {
                    System.out.println("this subreddit already exists, choose another name");
                }
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
        File subRedditNames = new File("SubReddit\\SubRedditNames.txt");
        try {
            if(checkTheFileForLine(subRedditNames, subRedditName))
            {
                Attributes.subReddit.setName(subRedditName);// subReddit name is assigned here to use in joining to subReedits method

                File subReddit = new File("SubReddit\\" + subRedditName +"\\" + subRedditName + "Posts.txt");
                Scanner readSubReddit = new Scanner(subReddit);
                String data;
                String username = "";
                String postTitle = "";
                int i;
                int postNumber = 0;
                while(readSubReddit.hasNextLine())
                {
                    subRedditName = "";
                    username = "";
                    postTitle = "";
                    i = 0;
                    data = readSubReddit.nextLine();

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

                    File postFile = new File( "Posts\\" + username + "\\" + postTitle + "_" + subRedditName + ".txt");

                    try
                    {
                        Scanner readPostFile = new Scanner(postFile);
                        String print;
                        System.out.println(postNumber);
                        while (readPostFile.hasNextLine())
                        {
                            print = readPostFile.nextLine();
                            System.out.println(print);
                        }
                        System.out.println("___________________________");
                        postNumber++;
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
    // and prints the posts in them000000
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
        File subReddit = new File("SubReddit\\" + Attributes.subReddit.getName() +"\\" + Attributes.subReddit.getName() + "Posts.txt");
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
                    giveKarma(subReddit);
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
                else
                {
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
        System.out.println("Press 'C' if you want to comment\nPress 'K' if you want to \"up vote\" or \"down vote\"\nPress 'Q' to return to main menu");
        File timeLineFile = new File("users\\" + Attributes.account.getUserName() + "\\" + Attributes.account.getUserName() + "TimeLine.txt");
        char ch = input.next().charAt(0);
        if (ch == 'c')
        {
            clearScreen();
            Comment.giveCommentOnSubReddit();
        }
        else if(ch == 'q')
        {
            clearScreen();
            return;
        }
        else if (ch == 'k')
        {
            giveKarma(timeLineFile);
        }
    }

    public static void seeTimeLine()
    {   File timeLine = new File("users\\" + Attributes.account.getUserName() + "\\" + Attributes.account.getUserName() + "TimeLine.txt");

        try {

            Scanner readTimeLineFile = new Scanner(timeLine);
            String data = null;
            int i;
            String username;
            String postTitle;
            String subRedditName;
            int postNumber = 0;
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

                File postFile = new File( "Posts\\" + username + "\\" + postTitle + "_" + subRedditName + ".txt");
                try
                {
                    Scanner readPostFile = new Scanner(postFile);
                    String print;

                    System.out.print(postNumber + " _ ");
                    while (readPostFile.hasNextLine())
                    {
                        print = readPostFile.nextLine();
                        System.out.println(print);
                    }
                    System.out.println("___________________________");
                    postNumber++;
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
