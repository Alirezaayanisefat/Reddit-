
//import java.lang.Thread;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main
{
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
     //make a display menu for logging in
    // Study about throws InterruptedException in next line
    public static boolean createAccount() throws InterruptedException {
        Scanner input = new Scanner(System.in);
        boolean displayAgain = true;
        clearScreen();
        AccountCreation account = new AccountCreation();
        System.out.println("Please enter your email to create an account");
        String email = input.nextLine();
        account.setUserEmail(email);
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
        }
        else
        {
            clearScreen();
            System.out.println("Wrong format for an email");
            Thread.sleep(3000);
            clearScreen();
            displayAgain = false;
        }
        return displayAgain;
    }
    // remember the method above must be repeated if it returns false
    public static void membership() throws IOException {

        UserAccountFile user = new UserAccountFile();
        user.createFile();
        user.writeToFile();
    }
    public static void login ()
    {
        Scanner input = new Scanner(System.in);
        clearScreen();
        String email;
        System.out.println("Please enter your email");
        email = input.nextLine();
        Loging loging = new Loging();
        loging.setUserEmail(email);
        if(loging.validateEmail())
        {   // needs debugging
            if (loging.emailCheck())
            {
                while (true)
                {   Scanner input2 = new Scanner(System.in);
                    clearScreen();
                    System.out.println("Please enter your password");
                    String password = input2.nextLine();
                    loging.setPassword(password);
                    if (loging.passwordCheck())
                    {
                        clearScreen();
                        System.out.println("Welcome");
                        break;
                    }
                    else
                    {
                        clearScreen();
                        System.out.println("Password and email does not match any");
                    }
                }
            }
            else
            {
                clearScreen();
                System.out.println("Your email does not match any");
            }
        }
        else
        {
            clearScreen();
            System.out.println("Wrong format for an email");
        }
    }
    public static int displayAccessingMenu() {
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
    public static void accessingProcess() throws InterruptedException, IOException {
      int order = displayAccessingMenu();
      Scanner input = new Scanner(System.in);
      while (true)
      {
          clearScreen();
          if (order == 1)
          {
              createAccount();
              //membership();
              clearScreen();
              System.out.println("Press 'Q' if you want to return to the previous menu ");
              char ch = input.next().charAt(0);
              if (ch == 'q')
                {
                    clearScreen();
                    //System.out.println("Press 'E' if you want to exit");
                    order = displayAccessingMenu();

                }

          }
          else
          {     // if you want to go login first you have to go to create account and from there go to login by pressing x
              login();
              clearScreen();
              System.out.println("Press 'Q' if you want to return to the previous menu");
              char ch = input.next().charAt(0);
              if (ch == 'q')
              {   clearScreen();
                  //System.out.println("Press 'O' if you want to exist");
                order = displayAccessingMenu();
              }
          }
      }
    }
    // remember the menu display after the Account creation method should be based on the return value of method above
    public static void main(String[] args) throws InterruptedException, IOException {
        accessingProcess();
    }
}