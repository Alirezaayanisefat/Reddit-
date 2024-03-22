
//import java.lang.Thread;
import java.util.Scanner;

public class Main
{
    private static final Scanner input = new Scanner(System.in);
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
     //make a display menu for logging in
    // Study about throws InterruptedException in next line
    public static boolean createAccount() throws InterruptedException {
        boolean displayAgain = true;
        clearScreen();
        AccountCreation account = new AccountCreation();
        System.out.print("Please enter your email");
        String email = input.nextLine();
        account.setUserEmail(email);
        if(account.validateEmail())
        {
            clearScreen();
            System.out.print("Please enter your password");
            String password = input.nextLine();
            account.setPassword(password);
            clearScreen();
            System.out.print("Your account was made successfully");
        }
        else
        {
            clearScreen();
            System.out.print("Wrong email");
            Thread.sleep(3000);
            clearScreen();
            displayAgain = false;
        }
        return displayAgain;
    }
    // remember the method above must be repeated if it returns false
    public static int displayMenu() {
        clearScreen();

        int pointer = 1;
        int printOnce = 1;
        while (true)
        {
            if(printOnce == 1)
            {
                System.out.println("Account creation <--\nUser authentication");
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
                System.out.println("Account creation <--\nUser authentication");
            }
            else {
                clearScreen();
                System.out.println("Account creation\nUser authentication <--");
            }
            if (ch == 'r')
            {
                break;
            }
        }
        return pointer % 2;
    }
    // remember the menu display after the Account creation method should be based on the return value of method above
    public static void main(String[] args) {

    }
}