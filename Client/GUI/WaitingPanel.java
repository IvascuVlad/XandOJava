package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class WaitingPanel extends JPanel {
    final MainFrame frame;
    JLabel text = new JLabel("Waiting for the other player to join...");
    JButton ready = new JButton("Ready");

    public WaitingPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        setLayout(null);
        Dimension size = text.getPreferredSize();
        text.setBounds(400 - size.width/2,200 - size.height/2,size.width,size.height);
        size = ready.getPreferredSize();
        ready.setBounds(400 - size.width/2,300,size.width,size.height);
        ready.addActionListener(this::readyState);
        add(ready);
        add(text);
    }

    private void readyState(ActionEvent actionEvent) {
        try {
            pentruConexiune();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pentruConexiune() throws IOException {
        frame.writeToServer("waiting");
        String aux = frame.readFromServer();
        if (aux.equals("ready")){
            frame.loadGame();
        }
    }
}
