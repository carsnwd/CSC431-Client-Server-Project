import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Client side for our client
 * server project. Sends a request
 * with a string to reverse.
 * @author Carson Wood
 *
 */
public class Client
{
    private static final int port = 4446;

    private static final String host = "localhost";

    //Output to server
    PrintWriter out;

    //Input from server
    Scanner conIn;

    //Input from user
    Scanner in;

    String[] tenLetWords = { "Strawberry", "California", "Everything", "Technology", "Volleyball", "Homecoming", "Relaxation", "Television", "University", "Cinderella" };

    /**
     * Creates a new instance of the client
     * @param args
     * @throws UnknownHostException
     * @throws IOException
     */
    public static void main(String[] args) throws UnknownHostException, IOException
    {
        Client c = new Client();
        c.run();
    }

    /**
     * Runs the client
     * TO-DO BREAK IT UP
     * 
     * @throws UnknownHostException
     * @throws IOException
     */
    public void run() throws UnknownHostException, IOException
    {
        String choice; //To advance client and send next string
        String msg; //Recieve messages from server

        //Opens connection
        Socket connection = openConnection();

        //Gets I/O streams from server
        out = new PrintWriter(connection.getOutputStream());
        conIn = new Scanner(connection.getInputStream());
        msg = conIn.nextLine();
        System.out.println(msg);
        in = new Scanner(System.in);

        /*
         * Loops through strings to reverse
         */
        for (String s : tenLetWords)
        {
            send(s);
            System.out.println("Press 1 to continue to next string...");
            choice = in.next();
            while (!choice.equals("1"))
            {
                choice = in.next();
            }

        }
        closeConnection(connection); //Closes the connection
    }

    /**
     * Open the connection
     * @return
     * @throws UnknownHostException
     * @throws IOException
     */
    private Socket openConnection() throws UnknownHostException, IOException
    {
        System.out.println("Connecting to server...");
        Socket connection = new Socket(host, port); //Connects to server
        return connection;
    }

    /**
     * Closes the connection
     * @param connection
     * @throws IOException
     */
    private void closeConnection(Socket connection) throws IOException
    {
        System.out.println("Closing connection...");
        connection.close();
    }

    /**
     * Sends a message to the server
     * with the string to reverse.
     * @param s
     */
    private void send(String s)
    {
        System.out.println("You sent " + s + ", sending message...");
        out.println(s);
        out.flush();
        String msg = conIn.nextLine();
        System.out.println(msg);
        out.flush();
    }
}
