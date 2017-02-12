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
public class Server
{
    //Socket for the server
    static ServerSocket serverSocket = null;

    /**
     * Main method to run server when the file
     * is first runned.
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException
    {
        serverSocket = new ServerSocket(4446); //Create server
        System.out.println("Waiting for a connection...");
        while (true)
        {
            Socket incomingConnection = serverSocket.accept(); //Accept connection
            System.out.println("Connection recieved from " + incomingConnection.getInetAddress() + " on port " + incomingConnection.getLocalPort());
            ClientListener c = new ClientListener(incomingConnection); //Creates new client listener
            c.start(); //Starts the client listener thread
        }

    }

    /**
     * A new thread that listens to each
     * client and processes it's requests.
     * @author carson
     *
     */
    static class ClientListener extends Thread
    {
        Socket connection; //Client socket
        Scanner in; //Client input stream
        PrintWriter out; //Client output stream
        ClientListener(Socket c) throws IOException{
            connection = c;
            in = new Scanner(c.getInputStream());
            out = new PrintWriter(c.getOutputStream());  
        }
        
        /**
         * Runs the thread task
         */
        public void run()
        {
            out.println("S: Connected to server at " + serverSocket.getLocalPort());
            out.flush();
            while (connection.isConnected()) //Process all client requests until it closes the connection
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

            for (int i = s.length() - 1; i >= 0; i--)
            {
                revS = revS + s.charAt(i);
            }
            return revS;
        }
    }
}
