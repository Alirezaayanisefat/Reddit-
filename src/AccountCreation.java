// through thi class you get the main data from user
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class AccountCreation {

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

    //____________________________________________________________
    private String userEmail;
    private String password;
    private String userName;
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean validateEmail() {
        // Regular expression for email validation
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userEmail);
        return matcher.matches();
    }
    public String getPassword() {
        return password;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public String getUserName() {
        return userName;
    }

    public static void searchForUsers(String userName)
    {
        File users = new File("users\\userNames");
        if (checkTheFileForLine(users, userName))
        {
            File subRedditUserHasJoined = new File("users\\" + userName + "\\" + userName + "JoinedSubReddit");
            File userPostTitles = new File("Posts\\" + userName + "\\" + userName + "PostTitles");

            try
            {
                Scanner reader = new Scanner(userPostTitles);
                while (reader.hasNextLine())
                {
                    String subReddit = "";
                    String postTitle = "";
                    String data = reader.nextLine();
                    int i = 0;
                    while (data.charAt(i) != ',') {
                        subReddit += data.charAt(i);
                        i++;
                    }
                    i++;
                    while (i < data.length()) {
                        postTitle += data.charAt(i);
                        i++;
                    }

                    File postFile = new File("Posts\\" + userName + "\\" + postTitle + "_" + subReddit);
                    Scanner readPost = new Scanner(postFile);
                    while (readPost.hasNextLine())
                    {
                        String str;
                        str = readPost.nextLine();
                        System.out.println(str);
                    }
                    System.out.println("_________________________");
                }
                Scanner input = new Scanner(System.in);
                System.out.println("Press 'E' to see to subReddit the user has joined\nPress 'Q' to return to main menu");
                char userChoice = input.next().charAt(0);
                if (userChoice == 'e')
                {

                    Scanner readSubReddit  =  new Scanner(subRedditUserHasJoined);
                    while (readSubReddit.hasNextLine())
                    {
                        String data = readSubReddit.nextLine();
                        System.out.println("_" + data);
                    }
                    Scanner quit = new Scanner(System.in);
                    System.out.println("Press 'E' to access to details subReddit user has joined\nPress 'Q' to return to main menu");
                    char ch = quit.next().charAt(0);
                    if (ch == 'e')
                    {
                        Scanner userInput = new Scanner(System.in);
                        String subRedditName = userInput.nextLine();
                        SubReddit.searchForSubReddit(subRedditName);
                        System.out.println("Press any key to return to mainMenu");
                        Scanner v = new Scanner(System.in);
                        v.nextLine();
                    }
                    else if (ch == 'q')
                    {
                        clearScreen();
                        return;
                    }
                }
                else if (userChoice == 'q')
                {
                    return;
                }
            }
            catch (IOException e)
            {
                System.out.println("The UserName you have Searched does not have any posts");
                sleep(2000);
                clearScreen();
            }
        }
        else
        {
            System.out.println("Such a userName does not exist");
        }

    }

    public static void search()
    {
        clearScreen();
        Scanner userInput = new Scanner(System.in);
        System.out.println("You should search for subreddits with\" \\r \"format an for user with \"\\u format \"");
        System.out.println("Search : ");

        while (true)
        {
            String searchResult = userInput.nextLine();
            String result = searchResult.substring(2);
            String searchFormat = searchResult.substring(0, 2);
            if (searchFormat.equals("\\r"))
            {
                clearScreen();
                SubReddit.searchForSubReddit(result);
                break;
            }
            if (searchFormat.equals("\\u"))
            {
                clearScreen();
                searchForUsers(result);
                clearScreen();
                break;
            }
            else
            {
                System.out.println("Choose True Format");
                sleep(2000);
                clearScreen();
            }
        }
    }
    public static void changePassword()
    {
        Scanner input = new Scanner(System.in);
        File userProfile = new File("users\\" + Attributes.account.getUserName() + "\\" + Attributes.account.getUserName() + "Profile");
        System.out.println("Enter your current password :");
        String newPassword = input.nextLine();
        String confirmPassword;

        if (newPassword.equals(Attributes.account.getPassword()))
        {
            System.out.println("Enter your new password:");
            newPassword = input.nextLine();

            while (true)
            {
                System.out.println("Confirm your new password");
                confirmPassword = input.next();

                if (newPassword.equals(confirmPassword))
                {
                    replaceLineInFile(userProfile, "Email : " +Attributes.account.getUserEmail(), "Password : " + newPassword);
                    Attributes.account.setPassword(newPassword);
                    break;
                }
                else
                {
                    System.out.println("Your input does not match the new password");
                    sleep(2000);
                    clearScreen();
                }
            }
        }
        else
        {
            System.out.println("Wrong password");
            sleep(2000);
            clearScreen();
        }
    }

    public static int displayEditProfileMenu()
    {
        Scanner input = new Scanner(System.in);
        clearScreen();
        int pointer = 1;
        int printOnce = 1;
        while (true)
        {
            if(printOnce == 1)
            {
                System.out.println("1 _ Change username <--\n2 _ Change email\n3 _ Change password\n4 _ return to main menu");
                printOnce++;
            }
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
                System.out.println("1 _ Change email <--\n2 _ Change password\n3 _ return to main menu");
            }
            else if (pointer % 3 == 2)
            {
                clearScreen();
                System.out.println("1 _ Change email\n2 _ Change password <--\n3 _ return to main menu");
            }
            else
            {
                clearScreen();
                System.out.println("1 _ Change email\n2 _ Change password\n3 _ return to main menu <--");
            }
            if (ch == 'e')
            {
                break;
            }
        }
        return pointer % 3;
    }

    public static void editProfile()
    {
        File userProfile = new File("users\\" + Attributes.account.getUserName() + "\\" + Attributes.account.getUserName() + "Profile");
        File userNames = new File("users\\userNames");
        int order = displayEditProfileMenu();
        Scanner input = new Scanner(System.in);
        while (true)
        {
            switch (order)
            {
                case 1:
                    System.out.println("Enter your new email");
                    String newEmail = input.nextLine();
                    replaceLineInFile(userProfile, "Email : " + Attributes.account.getUserEmail(), "Email : " + newEmail);
                    Attributes.account.setUserEmail(newEmail);
                    order = displayEditProfileMenu();
                    break;
                case 2:
                    changePassword();
                    order = displayEditProfileMenu();
                    break;
            }
            if (order == 0)
            {
                clearScreen();
                return;
            }
        }
    }

    public static void seeProfile()
    {
        System.out.println("USERNAME : " + Attributes.account.getUserName());
        System.out.println("EMAIL : " + Attributes.account.getUserEmail());
        System.out.println("Press 'E' if you want to edit your profile\nPress 'Q' to return to main menu");
        Scanner input = new Scanner(System.in);
        char userChoice = input.next().charAt(0);
        if(userChoice == 'e')
        {
            clearScreen();
            editProfile();
        }
        else if (userChoice == 'q')
        {
            clearScreen();
            return;
        }
    }
    public static String readFile()
    {
        try {
            File userProfile = new File("users\\" + Attributes.account.getUserName() + "\\" + Attributes.account.getUserName() + "Profile");
            String data = "";
            Scanner myReader = new Scanner(userProfile);
            while (myReader.hasNextLine())
            {
                data += myReader.nextLine();
            }
            myReader.close();
            return data;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("could not read the userProfile file in readFile method");
        }
        return null;
    }

    public static void writeToFile()
    {
        File userProfile = new File("users\\" + Attributes.account.getUserName() + "\\" + Attributes.account.getUserName() + "Profile");
        File  joinedSubReddit = new File("users\\" + Attributes.account.getUserName() + "\\" + Attributes.account.getUserName() + "JoinedSubReddit");
        File timeLine = new File("users\\" + Attributes.account.getUserName() + "\\" + Attributes.account.getUserName() + "TimeLine");
        File user = new File("users\\userNames");
        try {
            //if (!checkTheFileForLine(user, account.getUserEmail()))
            //{
            if (userProfile.getParentFile().mkdir())
            {

                boolean success = userProfile.createNewFile();
                success = joinedSubReddit.createNewFile();
                success = timeLine.createNewFile();

                FileWriter myWriter = new FileWriter(userProfile, true);
                myWriter.write("Email : " + Attributes.account.getUserEmail() + "\n");
                myWriter.write( "Username : " + Attributes.account.getUserName() + "\n");
                myWriter.write("Password : " + Attributes.account.getPassword());
                myWriter.close();

                myWriter = new FileWriter(user, true);
                myWriter.write(Attributes.account.getUserName() + "\n");
                myWriter.close();
                System.out.println("Your account was made successfully");
            } else {
                System.out.println("You already have an account");
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
        Attributes.account.setUserEmail(email);
        clearScreen();
        System.out.println("please choose a user name for yourself");
        String userName = input.next();
        Attributes.account.setUserName(userName);
        File user = new File("users\\userNames");
        if (!checkTheFileForLine(user, Attributes.account.getUserName()))
        {
            if (Attributes.account.validateEmail()) {
                Scanner input2 = new Scanner(System.in);
                clearScreen();
                System.out.println("Please enter your password to create an account");
                String password = input2.nextLine();
                Attributes.account.setPassword(password);
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

    public static int displayMainMenu()
    {
        Scanner input = new Scanner(System.in);
        int pointer = 1;
        clearScreen();
        System.out.println("1 _ Your profile <--\n2 _ Subreddits\n3 _ Timeline\n4 _ posts\n5 _ search\n6 _ log out");
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
            switch (pointer % 6)
            {
                case 0:
                    clearScreen();
                    System.out.println("1 _ Your profile\n2 _ Subreddits\n3 _ Timeline\n4 _ posts\n5 _ search\n6 _ log out <--");
                    break;
                case 1:
                    clearScreen();
                    System.out.println("1 _ Your profile <--\n2 _ Subreddits\n3 _ Timeline\n4 _ posts\n5 _ search\n6 _ log out");
                    break;
                case 2:
                    clearScreen();
                    System.out.println("1 _ Your profile\n2 _ Subreddits <--\n3 _ Timeline\n4 _ posts\n5 _ search\n6 _ log out");
                    break;
                case 3:
                    clearScreen();
                    System.out.println("1 _ Your profile\n2 _ Subreddits\n3 _ Timeline <--\n4 _ posts\n5 _ search\n6 _ log out");
                    break;
                case 4:
                    clearScreen();
                    System.out.println("1 _ Your profile\n2 _ Subreddits\n3 _ Timeline\n4 _ posts <--\n5 _ search\n6 _ log out");
                    break;
                case 5:
                    clearScreen();
                    System.out.println("1 _ Your profile\n2 _ Subreddits\n3 _ Timeline\n4 _ posts\n5 _ search <--\n6 _ log out");
                    break;
            }
        }
        return pointer % 6;
    }

    public static void mainMenuAccessing()
    {
        int order = displayMainMenu();
        while (true)
        {
            switch (order)
            {   case 1:
                seeProfile();
                order = displayMainMenu();
                break;
                case 2:
                    SubReddit.userInteractionWithSubReddit();
                    order = displayMainMenu();
                    break;
                case 3:
                    SubReddit.seeTimeLine();
                    order = displayMainMenu();
                    break;
                case 4:
                    Post.seePost();
                    order = displayMainMenu();
                    break;
                case 5:
                    search();
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
    public static void login ()
    {
        while (true)
        {
            Scanner input = new Scanner(System.in);
            clearScreen();
            String data;
            String str;
            String tempData = "";

            System.out.println("Please enter your email :");
            str = input.nextLine();
            tempData += "Email : " + str;
            Attributes.account.setUserEmail(str);
            clearScreen();

            System.out.println("please enter your username :");
            str = input.nextLine();
            tempData += "Username : " + str;
            Attributes.account.setUserName(str);
            clearScreen();

            System.out.println("Please enter your password :");
            str = input.nextLine();;
            tempData += "Password : " + str;
            Attributes.account.setPassword(str);
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
}
