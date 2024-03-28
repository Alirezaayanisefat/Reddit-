
/*
      Study about throws InterruptedException.
       remember to add a break statement for accessing process method
       make user able to post with the same title several posts at writeToPostFile method make good names for files
       add a comment deleting method
*/
import java.lang.Thread;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedWriter;
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


//__________________________________________________________POSTING PROCESS_____________________________________________________

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
                writer.write(post.getTitle());
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
        File file = new File("Posts\\" + account.getUserName() + "\\" + post.getTitle());
        try{
            if (file.createNewFile())
            {
                String data = post.getUserName() + "\n" + post.getSubRedditName() + "\n" + post.getTitle() + "\n" + post.getBody();
                Path fileName = Path.of(file.toURI());
                Files.writeString(fileName, data);
            }
            else
            {
                System.out.println("You already have this title");
            }
        }
        catch (IOException e)
        {
            System.out.println("Something went wrong");
        }
    }

    public static void createDirectory()
    {
        File file = new File("Posts\\" + account.getUserName()+"\\"+ account.getUserName());
        file.getParentFile().mkdir();
        writeToPostFile();
        try {
            file.createNewFile();
            String data = post.getTitle();
            Path fileName = Path.of(file.toURI());
            Files.write(fileName,
                    (data+"\n").getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        }
        catch (IOException e)
        {
            System.out.println("something went wrong with creating titleFile");
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
        try{
            file.createNewFile();
            FileWriter myWriter = new FileWriter(file);
            myWriter.write("Comment section :\n");
            myWriter.close();
        }
        catch (IOException e)
        {
            System.out.println("Comment section is not created");
        }
    }

    public static void showPostDetails(String title)
    {
        File file = new File("Posts\\" + account.getUserName() +"\\"+ account.getUserName());
        try {
            Scanner fileReader=new Scanner(file);
            List<String> list=new ArrayList<>();
            while(fileReader.hasNextLine()){
                list.add(fileReader.nextLine());
            }
            if(list.contains(title)){
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
        File titleFile = new File("Posts\\" + account.getUserName() +"\\"+ account.getUserName());
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
            System.out.println("Press 'Q' to return to main menu\nPress 'E' to your first post");
            char ch = input.next().charAt(0);
            if (ch == 'e') {
                createPost();
                post.setFirstPost(true);
                return;
            }
            else if (ch == 'q') {
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
                System.out.println("Your post with title "+ post.getTitle() + " is created successfully");
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
        }
        catch (IOException e)
        {
            System.out.println("something went wrong with comment section file");
        }
    }

    public static void seePost()
    {
        if (post.getFirstPost()) {
            post.setFirstPost(false);
            return;
        }
        clearScreen();
        accessToPosts();
        clearScreen();
        System.out.println("Press 'C' to access to comment section\nPress 'Q' to quit");// what if creates a post and not just see it presses e not r at the accessTO post method
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
    public static void joinSubReddit()
    {
        File subRedditData = new File("SubReddit\\" + subReddit.getName() +"\\"+ subReddit.getName()+"\\" + "members");
        try {
            subRedditData.createNewFile();
            FileWriter writer = new FileWriter(subRedditData, true);
            writer.write(account.getUserName() + "\n");
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
    public static void createSubreddit()
    {
        subReddit.setMainAdmin(account.getUserName());
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
        System.out.println("Your SubReddit is crated successfully");
        sleep(2000);
        clearScreen();
        File subRedditInterface = new File("SubReddit\\" + subReddit.getName() +"\\"+ subReddit.getName());
        File subRedditName = new File("SubReddit\\SubRedditNames");
        subRedditInterface.getParentFile().mkdir();
        try {
            subRedditInterface.createNewFile();
            FileWriter writer = new FileWriter(subRedditInterface);// may be need to add tru to the arguments of constructor
            writer.write(subReddit.getName() + "\n");
            writer.write("Topic:" + subReddit.getTopic() + "\n");
            writer.close();
            writer = new FileWriter(subRedditName,true);
            writer.write( subReddit.getName() + "\n");// may be need to add tru to the arguments of constructor
            writer.close();
        }
        catch (IOException e)
        {
            System.out.println("Fail to create a SubReddit");
        }
    }
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
            {
                File subReddit = new File("SubReddit\\" + subRedditName +"\\" + subRedditName);
                Scanner readSubReddit = new Scanner(subReddit);
                String data;
                while(readSubReddit.hasNextLine())
                {
                    data = readSubReddit.nextLine();
                    System.out.println(data);
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

//___________________________________________________________________________________________________________________________________________
     public static void displayMainMenu()
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
         switch (pointer % 4)
         {
             case 0:
                seePost();
                break;
             case 2:
                 createSubreddit();
                 break;
         }
     }

//_______________________________________________________ACCESSING PROCESS_______________________________________________________________

    public static String readFile()
    {
        try {
            File file = new File("users\\" + account.getUserName());
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
        File file = new File("users\\" + account.getUserName());
        try {
            if(file.createNewFile())
            {
                FileWriter myWriter = new FileWriter(file);
                myWriter.write(account.getUserName()+ "\n");
                myWriter.write(account.getUserEmail()+ "\n");
                myWriter.write(account.getPassword());
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
                displayMainMenu();
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
