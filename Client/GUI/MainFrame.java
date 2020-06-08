package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static java.awt.BorderLayout.*;

public class MainFrame extends JFrame {
    public LoginPanel loginPanel;
    public MenuPanel menuPanel;
    public WaitingPanel waitingPanel;
    public GamePanel gamePanel;
    public GameOver gameOver;
    public int symbol;

    public MainFrame() {
        super("X and O");
        init();
    }

    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800,600));

        loginPanel = new LoginPanel(this);
        add(loginPanel,CENTER);

        pack();
    }

    public void writeToServer(String command) throws IOException {
        ToServerSingleton instance = ToServerSingleton.getInstance();
        PrintWriter out = instance.getOutput();
        out.println(command);
        System.out.println("Am trimis " + command);
    }

    public String readFromServer() throws IOException {
        ToServerSingleton instance = ToServerSingleton.getInstance();
        BufferedReader in = instance.getInput();
        String response = in.readLine ();
        System.out.println("Am citit " + response);
        return response;
    }

    public void loadMenu(){
        remove(loginPanel);
        repaint();
        menuPanel = new MenuPanel(this);
        add(menuPanel,CENTER);
        pack();
    }

    public void loadWaiting() throws IOException {
        remove(menuPanel);
        repaint();
        waitingPanel = new WaitingPanel(this);
        add(waitingPanel,CENTER);
        pack();
    }

    public void loadGame(){
        remove(waitingPanel);
        repaint();
        gamePanel = new GamePanel(this,symbol);
        add(gamePanel,CENTER);
        pack();
    }

    public void loadGameOver(String name){
        remove(gamePanel);
        repaint();
        gameOver = new GameOver(this,name);
        add(gameOver);
        pack();
    }
}
