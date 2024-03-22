
import java.util.Scanner;

public class Main
{
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static int displayMenu() {
        clearScreen();
        Scanner input = new Scanner(System.in);
        int pointer = 1;
        int printOnce = 1;
        while (true)
        {
            if(printOnce == 1)
            {
                System.out.println("Account creation <--\nUser authentication");
                printOnce++;
            }
            //System.out.print("Account creation <--\nUser authentication");
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
    public static void main(String[] args) {
        displayMenu();
    }
}