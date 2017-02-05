import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Server code for the client/server project
 * Recieves request, reverses string, 
 * sends reversed string back
 * @author Carson Wood
 */
public class Server implements Runnable
{
    //Our socket for the server
    ServerSocket serverSocket;
    //Input for the client socket
    Scanner in;
    //Output for the client socket
    PrintWriter out;
    //Client socket
    Socket connection = null;
    
    /**
     * Main method to run server
     * @param args
     */
    public static void main(String[] args)
    {
        Server s = new Server();
        s.run();
    }
    
    /**
     * Runs the server.
     * TO-DO Break this bad boy up....
     */
    public void run()
    {
        try
        {
            serverSocket = new ServerSocket(4446); //Create server
            System.out.println("Waiting for a connection...");
            this.connection = serverSocket.accept(); //Accept connection
            System.out.println("Connection recieved from " + connection.getInetAddress() + " on port " + connection.getLocalPort());
            out = new PrintWriter(connection.getOutputStream()); //Gets output to connection
            out.flush();
            in = new Scanner(connection.getInputStream()); //Gets input to connection
            out.println("S: Connected to server at " + serverSocket.getLocalPort());
            out.flush();
            while(connection.isConnected()) //Process all client requests until it closes the connection
            {
                System.out.println("Waiting for response...");
                String msg = in.nextLine();
                System.out.println("Message recieved! Reversing now.");
                String rMsg = reverse(msg);
                System.out.println("Returning message...");
                out.println("S: Your message was: " + msg + " and it is now " + rMsg);
                out.flush();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Simple string reversal
     * @param s
     * @return
     */
    public String reverse(String s)
    {
        String revS = "";
        
        for(int i = s.length() - 1; i >= 0; i--)
        {
            revS = revS + s.charAt(i);
        }
        return revS;
    }

}