package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class LoginPanel extends JPanel {
    public String username = "Ivascu",password = "Vlad";
    final MainFrame frame;
    JLabel textForUsername = new JLabel("Enter username");
    JTextField usernameText = new JTextField();
    JLabel textForPassword = new JLabel("Enter password");
    JTextField passwordText = new JTextField();
    JButton submitBtn = new JButton("Submit");


    public LoginPanel(MainFrame frame){
        this.frame = frame;
        init();
    }

    private void init() {
        //setLayout(new GridLayout(1, 5));
        setLayout(null);
        textForPassword.setHorizontalAlignment(SwingConstants.CENTER);
        textForUsername.setHorizontalAlignment(SwingConstants.CENTER);

        Dimension size = textForUsername.getPreferredSize();
        textForUsername.setBounds(400 - size.width/2,50,size.width,size.height);
        add(textForUsername);
        usernameText.setBounds(350,100,100,25);
        add(usernameText);
        size = textForPassword.getPreferredSize();
        textForPassword.setBounds(400 - size.width/2,150,size.width,size.height);
        add(textForPassword);
        size = passwordText.getPreferredSize();
        passwordText.setBounds(350,200,100,25);
        add(passwordText);
        submitBtn.setBounds(400 - 50,250,100,25);
        add(submitBtn);
        System.out.println(size.width + " _ " + size.height);

        usernameText.addActionListener(this::readUsername);
        passwordText.addActionListener(this::readPassword);
        submitBtn.addActionListener(actionEvent -> {
            try {
                sendIt(actionEvent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        frame.repaint();
    }

    private void sendIt(ActionEvent actionEvent) throws IOException {
        if(username != null && password != null){
            System.out.println(username+password);
            frame.writeToServer(username+"_"+password);
            if(frame.readFromServer().equals("Bravo"))
                frame.loadMenu();
        }
        else
        {
            System.out.println("Nu ai apasat enter");
        }
    }

    public void readPassword(ActionEvent actionEvent) {
        password = passwordText.getText();
        passwordText.selectAll();
    }

    public void readUsername(ActionEvent actionEvent) {
        username = usernameText.getText();
        usernameText.selectAll();
    }

}
