
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
//_____________________________________________________________________________________________________________________________

    public static void clearScreen() {
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

//___________________________________________________________FILE FOR ACCOUNT CREATION___________________________________________

    public static String readFile(){
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

    public static void writeToFile() {
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

//__________________________________________________________FILE FOR POSTING____________________________________________________

//    public static void crateCommentSection()
//    {
//    File file = new File("Posts\\" + account.getUserName() +"\\" + post.getTitle() + "Comments");
//         try
//         {
//             file.createNewFile();
//             FileWriter myWriter = new FileWriter(file);
//             myWriter.write(comment.getUserName() + "\n");
//             myWriter.write(comment.getBody() + "\n");
//             myWriter.close();
//         }
//        catch (IOException e)
//        {
//             System.out.println("something went wrong with comment section file");
//        }
//    }


    public static void writeToPostFile()
    {
        File file = new File("Posts\\" + account.getUserName() + "\\" + post.getTitle());
        try{
            if (file.createNewFile())
            {
                String data = post.getUserName() + "\n" + post.getTitle() + "\n" + post.getBody();
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
//______________
//    public static void readFromPostFile() throws FileNotFoundException {
//        File file = new File("C:\\Users\\LEGION\\IdeaProjects\\Reddit\\Posts\\" + account.getUserName() +"\\" + post.getTitle());
//        String data;
//        Scanner myReader = new Scanner(file);
//        while (myReader.hasNextLine())
//        {
//            data = myReader.nextLine();
//            System.out.println(data);
//        }
//        myReader.close();
//    }
/// comment file


//__________________________________________________________POSTING PROCESS_____________________________________________________

    public static void createPost()
    {
        Scanner input = new Scanner(System.in);
        String temp;
        temp = account.getUserName();
        post.setUserName(temp);
        System.out.print("Title: ");
        temp = input.nextLine();
        post.setTitle(temp);
        clearScreen();
        System.out.println("Title: " + temp);
        System.out.println("Body: ");
        temp = input.nextLine();// how to get a paragraph?????????????????????????
        // probably hava to initials attributes
        post.setBody(temp);
        clearScreen();
        System.out.println("Title: " + post.getTitle());
        System.out.println("Body: " + post.getBody());
        createDirectory();
        File file = new File("Posts\\" + account.getUserName() +"\\" + post.getTitle() + " Comments");
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

    public static void showPostDetails(String title){
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

    public static void accessToPosts()  {
        boolean firstPost;
        // reading titleFile
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
                clearScreen();
                System.out.println("Press 'Q' to exit");
                char quit = input.next().charAt(0);
                clearScreen();
                if(quit == 'q')
                    return;
                break;
            case 'q':
                break;
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

    public static void seePost() {
        if (post.getFirstPost())
            return;
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
            File file = new File("Posts\\" + account.getUserName() +"\\" + post.getTitle() + " Comments");
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

//_____________________________________________________________________________________________________________________________
// Study about throws InterruptedException in next line.
     public static void displayMainMenu()  {
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
                 seePost();
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
     }

//_______________________________________________________ACCESSING PROCESS_______________________________________________________________

    public static void createAccount() {
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

//______________________________________________________________________________________________________________________

    public static void login ()  {
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

    public static void accessingProcess() {
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
              System.out.println("Press 'Q' if you want to return to the previous menu ");
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
              System.out.println("Press 'Q' if you want to return to the previous menu");
              char ch = input.next().charAt(0);
              if (ch == 'q')
              {   clearScreen();
                  order = displayAccessingMenu();
              }// remember to add a break statement
          }
      }
    }

//______________________________________________________________________________________________________________________________
    public static void main(String[] args) throws  NullPointerException, FileNotFoundException{
        accessingProcess();
    }
}
