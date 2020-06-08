package com.example.demo.xando;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameServer {
    public static final int PORT = 8100;
    public GameServer() throws IOException {
        ServerSocket serverSocket = null ;
        List<Local> local = Collections.synchronizedList(new ArrayList<>());
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                System.out.println ("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                ClientThread clientThread = new ClientThread(socket,local);
                clientThread.start();
            }
        } catch (IOException e) {
            System.err. println ("Ooops... " + e);
        } finally {
            serverSocket.close();
        }
    }
}
