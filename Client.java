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
    //Output to server
    PrintWriter out;
    //Input from server
    Scanner conIn;
    
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
        System.out.println("Connecting to server...");
        Socket connection = new Socket("localhost", 4446); //Connects to server
        out = new PrintWriter(connection.getOutputStream());
        conIn = new Scanner(connection.getInputStream());
        String msg = conIn.nextLine();
        System.out.println(msg);
        send("Strawberry");
        send("California");
        send("Everything");
        send("Technology");
        send("Volleyball");
        send("Homecoming");
        send("Relaxation");
        send("Television");
        send("University");
        send("Cinderella");
        connection.close();
    }
    
    /**
     * Sends a message to the server
     * with the string to reverse.
     * @param s
     */
    public void send(String s)
    {
        System.out.println("You sent " + s + ", sending message...");
        out.println(s);
        out.flush();
        String msg = conIn.nextLine();
        System.out.println(msg);
        out.flush();
    }
}