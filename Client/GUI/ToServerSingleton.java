package GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ToServerSingleton {
    private static ToServerSingleton obj;
    private static Socket socket;


    // private constructor to force use of
    // getInstance() to create Singleton object
    private ToServerSingleton() throws IOException {
        String serverAddress = "127.0.0.1"; // The server's IP address
        int PORT = 8100; // The server's port

        socket = new Socket(serverAddress, PORT);
    }

    public static ToServerSingleton getInstance() throws IOException {
        if (obj==null)
            obj = new ToServerSingleton();
        return obj;
    }

    public PrintWriter getOutput() throws IOException {
        return new PrintWriter(socket.getOutputStream(), true);
    }

    public BufferedReader getInput() throws IOException {
        return new BufferedReader (new InputStreamReader(socket.getInputStream()));
    }
}
