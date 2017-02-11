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
    static ServerSocket serverSocket = null;
    //Input for the client socket
    static Scanner in = null;
    //Output for the client socket
    static PrintWriter out = null;
    //Client socket
    static Socket connection = null;
    
    /**
     * Constructor for starting a new thread/server.
     * @param c
     * @param i
     * @param o
     * @param s
     */
    Server(Socket c, Scanner i, PrintWriter o, ServerSocket s){
        connection = c;
        serverSocket = s;
        in = i;
        out = o;
    }
    
    /**
     * Main method to run server when the file
     * is first runned.
     * @param args
     */
    public static void main(String[] args)
    {
        Server s = new Server(connection, in, out, serverSocket);
        try
        {
            s.startServer();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }
    
    private void startServer() throws IOException
    {
        serverSocket = new ServerSocket(4446); //Create server
        System.out.println("Waiting for a connection...");
        while(true){
            Socket incomingConnection = serverSocket.accept(); //Accept connection
            System.out.println("Connection recieved from " + incomingConnection.getInetAddress() + 
                    " on port " + incomingConnection.getLocalPort());
            out = new PrintWriter(incomingConnection.getOutputStream()); //Gets output to connection
            out.flush();
            in = new Scanner(incomingConnection.getInputStream()); //Gets input to connection
            out.println("S: Connected to server at " + serverSocket.getLocalPort());
            out.flush();
            new Thread(new Server(incomingConnection, in, out, serverSocket)).start();
        }
    }

    /**
     * Starts a new thread for each
     * new 
     */
    public void run()
    {
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
