package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel extends JPanel {
    final MainFrame frame;
    final static int W = 800, H = 550;
    BufferedImage image;
    Graphics2D graphics;
    JButton otherPlayer = new JButton("Submit");
    JLabel yourTurn = new JLabel("Poti pune piesa urmata de submit!");
    JLabel notYourTurn = new JLabel("Apasa submit si pune piesa!");
    int symbolId;
    int k = 0;

    public GamePanel(MainFrame mainFrame, int symbol) {
        this.frame = mainFrame;
        this.symbolId = symbol;
        createOffscreenImage();
        init();
    }

    private void createOffscreenImage() {
        image = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, W, H);
        graphics.setColor(Color.BLACK);
        graphics.drawLine(320,50,320,400);
        graphics.drawLine(470,50,470,400);
        graphics.drawLine(200,150,600,150);
        graphics.drawLine(200,300,600,300);
    }

    private void init(){
        otherPlayer.addActionListener(this::otherPlayerMove);
        add(otherPlayer);
        if (symbolId == 1) {
            add(yourTurn);
        }else{
            add(notYourTurn);
        }
        setPreferredSize(new Dimension(W, H));
        setBorder(BorderFactory.createEtchedBorder());
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (200 < e.getX() && e.getX() < 320){
                    if(50 < e.getY() && e.getY() < 150){
                        try {
                            frame.writeToServer("submit_move_1_1");
                            String aux = frame.readFromServer();
                            if (aux.equals("Piesa adaugata"))
                                drawShape(260,100,symbolId);
                            if (aux.equals("Felicitari ai castigat"))
                                frame.loadGameOver("Felicitari ai castigat!");
                        } catch (IOException ex) {
                            System.out.println("Eroare la trimiterea la server");
                        }
                    }
                    if(150 < e.getY() && e.getY() < 300){
                        try {
                            frame.writeToServer("submit_move_2_1");
                            String aux = frame.readFromServer();
                            if (aux.equals("Piesa adaugata"))
                                drawShape(260,225,symbolId);
                            if (aux.equals("Felicitari ai castigat"))
                                frame.loadGameOver("Felicitari ai castigat!");
                        } catch (IOException ex) {
                            System.out.println("Eroare la trimiterea la server");
                        }
                    }
                    if(300 < e.getY() && e.getY() < 400){
                        try {
                            frame.writeToServer("submit_move_3_1");
                            String aux = frame.readFromServer();
                            if (aux.equals("Piesa adaugata"))
                                drawShape(260, 350,symbolId);
                            if (aux.equals("Felicitari ai castigat"))
                                frame.loadGameOver("Felicitari ai castigat!");
                        } catch (IOException ex) {
                            System.out.println("Eroare la trimiterea la server");
                        }
                    }
                }
                if(320 < e.getX() && e.getX() < 470){
                    if(50 < e.getY() && e.getY() < 150){
                        try {
                            frame.writeToServer("submit_move_1_2");
                            String aux = frame.readFromServer();
                            if (aux.equals("Piesa adaugata"))
                                drawShape(395,100,symbolId);
                            if (aux.equals("Felicitari ai castigat"))
                                frame.loadGameOver("Felicitari ai castigat!");
                        } catch (IOException ex) {
                            System.out.println("Eroare la trimiterea la server");
                        }
                    }
                    if(150 < e.getY() && e.getY() < 300){
                        try {
                            frame.writeToServer("submit_move_2_2");
                            String aux = frame.readFromServer();
                            if (aux.equals("Piesa adaugata"))
                                drawShape(395,225,symbolId);
                            if (aux.equals("Felicitari ai castigat"))
                                frame.loadGameOver("Felicitari ai castigat!");
                        } catch (IOException ex) {
                            System.out.println("Eroare la trimiterea la server");
                        }
                    }
                    if(300 < e.getY() && e.getY() < 400){
                        try {
                            frame.writeToServer("submit_move_3_2");
                            String aux = frame.readFromServer();
                            if (aux.equals("Piesa adaugata"))
                                drawShape(395, 350,symbolId);
                            if (aux.equals("Felicitari ai castigat"))
                                frame.loadGameOver("Felicitari ai castigat!");
                        } catch (IOException ex) {
                            System.out.println("Eroare la trimiterea la server");
                        }
                    }
                }
                if(470 < e.getX() && e.getX() < 600){
                    if(50 < e.getY() && e.getY() < 150){
                        try {
                            frame.writeToServer("submit_move_1_3");
                            String aux = frame.readFromServer();
                            if (aux.equals("Piesa adaugata"))
                                drawShape(535,100,symbolId);
                            if (aux.equals("Felicitari ai castigat"))
                                frame.loadGameOver("Felicitari ai castigat!");
                        } catch (IOException ex) {
                            System.out.println("Eroare la trimiterea la server");
                        }
                    }
                    if(150 < e.getY() && e.getY() < 300){
                        try {
                            frame.writeToServer("submit_move_2_3");
                            String aux = frame.readFromServer();
                            if (aux.equals("Piesa adaugata"))
                                drawShape(535,225,symbolId);
                            if (aux.equals("Felicitari ai castigat"))
                                frame.loadGameOver("Felicitari ai castigat!");
                        } catch (IOException ex) {
                            System.out.println("Eroare la trimiterea la server");
                        }
                    }
                    if(300 < e.getY() && e.getY() < 400){
                        try {
                            frame.writeToServer("submit_move_3_3");
                            String aux = frame.readFromServer();
                            if (aux.equals("Piesa adaugata"))
                                drawShape(535, 350,symbolId);
                            if (aux.equals("Felicitari ai castigat"))
                                frame.loadGameOver("Felicitari ai castigat!");
                        } catch (IOException ex) {
                            System.out.println("Eroare la trimiterea la server");
                        }
                    }
                }
                repaint();
            }
        });
    }

    private void otherPlayerMove(ActionEvent actionEvent) {
        if(symbolId == 0)
        {
            try {
                System.out.println("vreau sa astept pe altul");
                frame.writeToServer("waitother");
                String aux = frame.readFromServer();
                if(aux.equals("game_over"))
                {
                    frame.loadGameOver("La revedere jucatorule!");
                }else {
                    int x = Integer.parseInt(aux.substring(0, aux.indexOf("_")));
                    int y = Integer.parseInt(aux.substring(aux.indexOf("_") + 1));
                    if(y==1){
                        if(x==1)
                            drawShape(260,100,(symbolId + 1) % 2);
                        if (x==2)
                            drawShape(260,225,(symbolId + 1) % 2);
                        if (x==3)
                            drawShape(260, 350,(symbolId + 1) % 2);
                    }
                    if (y==2){
                        if (x==1)
                            drawShape(395,100,(symbolId + 1) % 2);
                        if (x==2)
                            drawShape(395,225,(symbolId + 1) % 2);
                        if (x==3)
                            drawShape(395, 350,(symbolId + 1) % 2);
                    }
                    if (y==3){
                        if (x==1)
                            drawShape(535,100,(symbolId + 1) % 2);
                        if (x==2)
                            drawShape(535,225,(symbolId + 1) % 2);
                        if (x==3)
                            drawShape(535, 350,(symbolId + 1) % 2);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else{
            try {
                System.out.println("vreau sa astept pe altul");
                frame.writeToServer("waitother");
                String aux = frame.readFromServer();
                if(aux.equals("game_over") || aux.equals("-1_-1"))
                {
                    frame.loadGameOver("La revedere jucatorule!");
                }else {
                    int x = Integer.parseInt(aux.substring(0, aux.indexOf("_")));
                    int y = Integer.parseInt(aux.substring(aux.indexOf("_") + 1));
                    if(y==1){
                        if(x==1)
                            drawShape(260,100,(symbolId + 1) % 2);
                        if (x==2)
                            drawShape(260,225,(symbolId + 1) % 2);
                        if (x==3)
                            drawShape(260, 350,(symbolId + 1) % 2);
                    }
                    if (y==2){
                        if (x==1)
                            drawShape(395,100,(symbolId + 1) % 2);
                        if (x==2)
                            drawShape(395,225,(symbolId + 1) % 2);
                        if (x==3)
                            drawShape(395, 350,(symbolId + 1) % 2);
                    }
                    if (y==3){
                        if (x==1)
                            drawShape(535,100,(symbolId + 1) % 2);
                        if (x==2)
                            drawShape(535,225,(symbolId + 1) % 2);
                        if (x==3)
                            drawShape(535, 350,(symbolId + 1) % 2);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        repaint();
    }

    private void drawShape(int x, int y,int symbol) {
        System.out.println("Desenez");
        if(symbol == 1){
            graphics.setColor(Color.BLACK);
            graphics.fill(new Xsymbol(x,y,graphics));
            k++;
        }
        else {
            graphics.setColor(Color.BLACK);
            graphics.fill(new Osymbol(x,y,100));
            graphics.setColor(Color.WHITE);
            graphics.fill(new Osymbol(x,y,90));
            k++;
        }
    }

    @Override
    public void update(Graphics g) { }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }
}
