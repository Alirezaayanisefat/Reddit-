import javax.imageio.IIOException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Comment
{

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
    //____________________________________________________________________________________________________________
    private String userName;
    private String body;

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public static void giveComment()
    {
        Scanner input = new Scanner(System.in);
        String str;

        Attributes.comment.setUserName(Attributes.account.getUserName());
        System.out.println("Write your comment");
        str = input.nextLine();
        Attributes.comment.setBody(str);

        File postComments = new File("Posts\\" + Attributes.account.getUserName() + "\\" + Attributes.post.getTitle() + "Comments.txt");
        try
        {
            FileWriter myWriter = new FileWriter(postComments,true);
            myWriter.write( "\\u" + Attributes.comment.getUserName() + " : ");
            myWriter.write(Attributes.comment.getBody() + "\n");
            myWriter.close();
            clearScreen();
            System.out.println("You have commented Successfully");
            sleep(2000);
        }
        catch (IOException e)
        {
            System.out.println("something went wrong with comment section file");
        }
    }

    public static void giveCommentOnSubReddit()
    {
        String str;

        Scanner getComment = new Scanner(System.in);
        clearScreen();

        System.out.println("Enter the subReddit name of the post :");
        str = getComment.nextLine();
        Attributes.post.setSubRedditName(str);

        System.out.println("Enter the username of the post creator");
        str = getComment.nextLine();
        Attributes.post.setUserName(str);

        System.out.println("Enter the title of the post by : " + Attributes.post.getUserName());
        str = getComment.nextLine();
        Attributes.post.setTitle(str);

        File postComments = new File("Posts\\" + Attributes.post.getUserName() + "\\" + Attributes.post.getTitle() + "Comments.txt");
        try {
            Scanner commentReader = new Scanner(postComments);
            String data;
            while (commentReader.hasNextLine())
            {
                data = commentReader.nextLine();
                System.out.println(data);
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("user and title does nt match any");
        }

        System.out.println("Press 'E' if you want to give comment\nPress 'Q' tu return to previous menu");
        Scanner getUserChoice = new Scanner(System.in);
        char userChoice = getUserChoice.next().charAt(0);
        if ( userChoice == 'e')
        {
            clearScreen();
            giveComment();
        }
    }
}
