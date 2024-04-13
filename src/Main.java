
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main
{
    public static void clearScreen()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

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
           AccountCreation.createAccount();
           clearScreen();
           clearScreen();
           order = displayAccessingMenu();

          }
          else
          {
              AccountCreation.login();
              clearScreen();
              order = displayAccessingMenu();
              // remember to add a break statement
          }
      }
    }

    public static void main(String[] args)  {
        while (true)
        {
            try {
                accessingProcess();
                break;
            } catch (NullPointerException e) {
                System.out.println("such an account does not exist!");
            }
        }
    }
}
