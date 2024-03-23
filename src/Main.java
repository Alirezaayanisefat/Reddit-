
//import java.lang.Thread;
import java.util.Scanner;

public class Main
{
    Scanner input = new Scanner(System.in);
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
        if(account.validateEmail())
        {
            clearScreen();
            System.out.println("Please enter your password to create an account");
            String password = input.nextLine();
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
                {
                    clearScreen();
                    System.out.println("Please enter your password");
                    String password = input.nextLine();
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
            if (ch == 'r')
            {
                break;
            }
        }
        return pointer % 2;
    }
    public static void accessingProcess() throws InterruptedException {
      int order = displayAccessingMenu();
      Scanner input = new Scanner(System.in);
      while (true)
      {
          clearScreen();
          if (order == 1)
          {
              createAccount();
              clearScreen();
              System.out.println("Press 'R' if you want to return to the previous menu ");
              char ch = input.next().charAt(0);
              if (ch == 'r')
                {
                    clearScreen();
                    //System.out.println("Press 'E' if you want to exit");
                    displayAccessingMenu();

                }
              else if(ch == 'x')
              {
                    order = 0;
              }
          }
          else
          {     // if you want to go login first you hava to go to create account and from there go to login by pressing x
              login();
              clearScreen();
              System.out.println("Press 'R' if you want to return to the previous menu");
              char ch = input.next().charAt(0);
              if (ch == 'r')
              {   clearScreen();
                  //System.out.println("Press 'O' if you want to exist");
                  displayAccessingMenu();
              }
              else if(ch == 1)
              {
                  order = 1;
              }
          }
      }
    }
    // remember the menu display after the Account creation method should be based on the return value of method above
    public static void main(String[] args) throws InterruptedException {
        accessingProcess();
    }
}