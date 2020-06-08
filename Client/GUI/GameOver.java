package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class GameOver extends JPanel {
    final MainFrame frame;
    JLabel text;
    JButton exit = new JButton("Exit");

    public GameOver(MainFrame frame, String name) {
        this.frame = frame;
        text = new JLabel(name);
        init();
    }

    private void init() {
        setLayout(null);
        Dimension size = text.getPreferredSize();
        text.setBounds(400 - size.width/2,200 - size.height/2,size.width,size.height);
        size = exit.getPreferredSize();
        exit.setBounds(400 - size.width/2, 300 - size.height/2,size.width,size.height);
        exit.addActionListener(actionEvent -> {
            try {
                exit(actionEvent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        add(text);
        add(exit);
        frame.repaint();
    }

    private void exit(ActionEvent actionEvent) throws IOException {
        frame.writeToServer("exit");
        System.exit(0);
    }
}
