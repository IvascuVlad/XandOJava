package GUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

public class MenuPanel extends JPanel {
    final MainFrame frame;
    JButton createBtn = new JButton("Create game");
    JButton joinBtn = new JButton("Join game");
    JButton exitBtn = new JButton("Exit");
    ArrayList<String> availableGames;
    JList gamesList;
    int index = -1;
    int symbol;

    public MenuPanel(MainFrame mainFrame) {
        this.frame = mainFrame;
        init();
    }

    private void init(){
        setLayout(null);
        Dimension size = createBtn.getPreferredSize();
        createBtn.setBounds(400 - size.width/2,100,size.width,size.height);
        add(createBtn);
        //size = joinBtn.getPreferredSize();
        joinBtn.setBounds(400 - size.width/2,200,size.width,size.height);
        add(joinBtn);
        //size = exitBtn.getPreferredSize();
        exitBtn.setBounds(400 - size.width/2,300,size.width,size.height);
        add(exitBtn);
        createBtn.addActionListener(actionEvent2 -> {
            try {
                create(actionEvent2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        joinBtn.addActionListener(actionEvent1 -> {
            try {
                join(actionEvent1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        exitBtn.addActionListener(actionEvent -> {
            try {
                exit(actionEvent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        frame.repaint();
    }

    private void exit(ActionEvent actionEvent) throws IOException {
        System.out.println("Ai solicitat exit");
        frame.writeToServer("exit");
        System.exit(0);
    }

    private void join(ActionEvent actionEvent) throws IOException {
        System.out.println("Ai solicitat join");
        frame.writeToServer("available_games");
        String aux = frame.readFromServer();
        if (!aux.equals("Nu sunt jocuri disponibile")) {
            remove(createBtn);
            remove(joinBtn);
            remove(exitBtn);
            frame.repaint();
            availableGames = new ArrayList<>();
            while (!aux.equals("")) {
                int virgula = aux.indexOf(",");
                availableGames.add(aux.substring(0, virgula));
                aux = aux.substring(virgula + 1);
            }
            String[] lista = availableGames.toArray(new String[0]);
            gamesList = new JList(lista);
            Dimension size = gamesList.getPreferredSize();
            if (size.width < 100)
                size.width = 150;
            gamesList.setBounds(400 - size.width / 2, 200 - size.height / 2, size.width, size.height);
            add(gamesList);
            frame.repaint();
            gamesList.addListSelectionListener(listSelectionEvent -> {
                try {
                    selectedGame(listSelectionEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            frame.repaint();
        }
    }

    private void selectedGame(ListSelectionEvent listSelectionEvent) throws IOException {
        if(index == -1) {
            String aux1 = (String) gamesList.getSelectedValue();
            index = aux1.charAt(aux1.length()-1) - 48;
            System.out.println(index);
            System.out.println("Te inscrii la " + availableGames.get(gamesList.getSelectedIndex()));
            frame.writeToServer("join_game_" + index);
            String aux2 = frame.readFromServer();
            if (!aux2.equals("Nu poti fi adaugat in jocul acesta")) {
                remove(gamesList);
                frame.loadWaiting();
            }
            frame.symbol = aux2.charAt(0)-48;
        }
    }

    private void create(ActionEvent actionEvent) throws IOException {
        System.out.println("Ai solicitat create");
        frame.writeToServer("create_game");
        String aux = frame.readFromServer();
    }
}
