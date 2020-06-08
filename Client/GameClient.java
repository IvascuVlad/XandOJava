import GUI.MainFrame;
import GUI.ToServerSingleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GameClient {
    public static void main (String[] args) throws IOException {
        MainFrame da = new MainFrame();
        da.setVisible(true);
        da.pack();

        ToServerSingleton instance = ToServerSingleton.getInstance();
        PrintWriter out = instance.getOutput();
    }

}
