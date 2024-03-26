
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
import java.util.Scanner;
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
//___________________________________________________________FILE FOR ACCOUNT CREATION___________________________________________
    public static String readFile() throws FileNotFoundException {
        File file = new File("C:\\Users\\LEGION\\IdeaProjects\\Reddit\\users\\" + account.getUserName());
        String data = null;
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine())
        {
            data += myReader.nextLine();
        }
        myReader.close();
        return data;
    }
    public static void writeToFile() {
        File file = new File("C:\\Users\\LEGION\\IdeaProjects\\Reddit\\users\\" + account.getUserName());
        try {
            if(file.createNewFile())
            {
                FileWriter myWriter = new FileWriter("C:\\Users\\LEGION\\IdeaProjects\\Reddit\\users\\" + account.getUserName());
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
    public static void crateCommentSection()
    {
    File file = new File("C:\\Users\\LEGION\\IdeaProjects\\Reddit\\Posts\\" + account.getUserName() +"\\" + post.getTitle() + "comments");
         try
         {
             file.createNewFile();
             FileWriter myWriter = new FileWriter(file);
             myWriter.write(comment.getUserName() + "\n");
             myWriter.write(comment.getBody() +"\n");
         }
        catch (IOException e)
        {
             System.out.println("something went wrong with comment section file");
        }
    }
//______________________
    public static void writeToPostFile()
    {
        File file = new File("C:\\Users\\LEGION\\IdeaProjects\\Reddit\\Posts\\" + account.getUserName() + "\\" + post.getTitle());
        try{
            if (file.createNewFile())
            {   crateCommentSection();
                FileWriter myWriter =new FileWriter(file);
                //myWriter.write(post.getSubRedditName() + "\n");
                myWriter.write(post.getOwner() + "\n");
                myWriter.write(post.getTitle() +"\n");
                myWriter.write(post.getBody());
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
//_________________________
    public static void createDirectory()
    {
        File file = new File("C:\\Users\\LEGION\\IdeaProjects\\Reddit\\Posts\\" + account.getUserName());
        while (true) {
            if (!file.mkdir()) {
                writeToPostFile();/// check again
                break;
            }
        }
        File titleFile = new File("C:\\Users\\LEGION\\IdeaProjects\\Reddit\\Posts\\" + account.getUserName() +"\\"+ account.getUserName());
        try {
            titleFile.createNewFile();
            FileWriter fileWriter = new FileWriter(titleFile);
            fileWriter.write(post.getTitle() + "\n");
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
        post.setOwner(account.getUserName());
        System.out.print("Title: ");
        String temp = input.nextLine();
        post.setTitle(temp);
        clearScreen();
        System.out.println("Title: " + temp);
        System.out.println("Body: ");
        temp = input.nextLine();// how to print a paragraph?????????????????????????
        // probably hava to initials attributes
        post.setBody(temp);
        clearScreen();
        System.out.println("Title: " + temp);
        System.out.println("Body: " + temp);
        createDirectory();
    }
//____
    public static void accessToPosts()
    {
        // reading titleFile
        Scanner fileReader = new Scanner("C:\\Users\\LEGION\\IdeaProjects\\Reddit\\Posts\\" + account.getUserName() +"\\"+ account.getUserName());
        while (fileReader.hasNext())
        {
            System.out.println(fileReader.nextLine());
        }
        System.out.println("HELP:\nPress 'E' to create post\nPress 'R' to access to posts\nPress 'Q' to return to previous menu");
        Scanner input = new Scanner(System.in);
        char ch = input.next().charAt(0);
        clearScreen();
        switch (ch)
        {
            case 'e':
                createPost();
                clearScreen();
                break;
            case 'r':
                // only prints the post
                System.out.println("Enter the post title");
                String title = input.nextLine();
                clearScreen();
                Scanner fileReader_2 = new Scanner("C:\\Users\\LEGION\\IdeaProjects\\Reddit\\Posts\\" + account.getUserName()+"\\"+ title);
                while (fileReader_2.hasNextLine())
                    System.out.println(fileReader_2.nextLine());
                System.out.println("Press 'Q' to exit");
                char c = input.next().charAt(0);
                clearScreen();
                if(c == 'q')
                    return;
                break;
            case 'q':
                break;
        }
    }
//____________________________
    public static void giveComment()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter your user name to comment");
        String str = input.nextLine();
        comment.setUserName(str);
        System.out.println("Write your comment");
        str = input.nextLine();
        comment.setBody(str);
        return;
    }
    public static void seePost() throws FileNotFoundException {
        clearScreen();
        accessToPosts();
        clearScreen();
        System.out.println("Press 'C' to access to comment section\nPress 'Q' to quit");// what if creates a post and not just see it presses e not rat the accessTO post method
        Scanner input = new Scanner(System.in);
        char ch = input.next().charAt(0);
        if(ch == 'q') {
            clearScreen();
            return;
        }
        System.out.println("Press 'C' if you want to see comment section");
        if (ch == 'c')
        {   clearScreen();
            File file = new File("C:\\Users\\LEGION\\IdeaProjects\\Reddit\\Posts\\" + account.getUserName() +"\\" + post.getTitle() + "comments");
            Scanner commentReader = new Scanner(file);
            while (commentReader.hasNextLine())
            {
                System.out.println(commentReader.nextLine());
            }
        }
        System.out.println("Press 'E' if you want to leave a comment");
        if (ch == 'e')
        {
            clearScreen();
            giveComment();
        }
    }
//_____________________________________________________________________________________________________________________________
// Study about throws InterruptedException in next line.
     public static void displayMainMenu() throws FileNotFoundException {
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
    public static void createAccount() throws InterruptedException {
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
            Thread.sleep(2000);
        }
        else
        {
            clearScreen();
            System.out.println("Wrong format for an email");
            Thread.sleep(2000);
            clearScreen();
        }
    }
//______________________________________________________________________________________________________________________
    public static void login () throws InterruptedException, FileNotFoundException {
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
                Thread.sleep(2000);
                clearScreen();
                displayMainMenu();
                break;
            }
            else {
                System.out.println("Password and email does not match any");
                Thread.sleep(2000);
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
//_________________________
    public static void accessingProcess() throws InterruptedException, FileNotFoundException {
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
    public static void main(String[] args) throws InterruptedException, NullPointerException, FileNotFoundException{
        accessingProcess();
    }
}
