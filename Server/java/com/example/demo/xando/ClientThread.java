package com.example.demo.xando;

import com.example.demo.controllers.GameController;
import com.example.demo.controllers.UserController;
import com.example.demo.models.Game;
import com.example.demo.models.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collections;
import java.util.List;

public class ClientThread extends Thread{
    boolean rulare = true;
    private Socket socket = null ;
    List<Local> local;
    UserController userController = new UserController();
    GameController gameController = new GameController();
    User player;
    int xOro;
    public ClientThread (Socket socket, List<Local> localDBList) {
        this.socket = socket ;
        this.local = Collections.synchronizedList(localDBList);
    }

    public void run () {
        try {
            String request = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            request = in.readLine();
            player = userController.createPlayerController(request.substring(0, request.indexOf("_")),request.substring(request.indexOf("_") + 1));
            if(player != null){
                if(player.getUsername() == null)
                    System.out.println("Te-am logat");
                else
                    System.out.println("Ai intrat si te-am bagat " + player.getUsername());
                PrintWriter outi = new PrintWriter(socket.getOutputStream());
                String raspunsi = "Bravo";
                outi.println(raspunsi);
                outi.flush();
            }else {
                System.out.println("Invalid");
                PrintWriter outi = new PrintWriter(socket.getOutputStream());
                String raspunsi = "Eroare";
                outi.println(raspunsi);
                outi.flush();
            }
            while(!request.equals("exit") /*&& rulare*/) {
                List<Game> games = gameController.getGamesController();
                request = in.readLine();
                if (request.equals("stop")) {
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    String raspuns = "Server stopped";
                    out.println(raspuns);
                    out.flush();
                    System.exit(0);
                }else if(request.equals("exit")){
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    String raspuns = "La revedere";
                    out.println(raspuns);
                    out.flush();
                }
                else {
                    System.out.println("");
                    System.out.println("Am primit " + request);
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    /* verific jocurile disponibile si care nu sunt terminate*/
                    if(request.equals("waitother")){
                        System.out.println(player.getUsername() + " astept pe altul");
                        for (Local gameForMe : local) {
                            if (gameForMe.game.getPlayer1().equals(player.getId()) || gameForMe.game.getPlayer2().equals(player.getId())) {
                                while (gameForMe.turn == player) {
                                    try {
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                while (gameForMe.lastMoveX == -1) {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                                if(gameForMe.weHaveAWinner){
                                    System.out.println(player.getUsername() + " stiu ca e gata");
                                    out.println("game_over");
                                    out.flush();
                                }else {
                                    System.out.println(player.getUsername() + " iau mutarea celuilalt");
                                    out.println(gameForMe.lastMoveX + "_" + gameForMe.lastMoveY);
                                    gameForMe.lastMoveX = -1;
                                    gameForMe.lastMoveY = -1;
                                    out.flush();
                                }
                            }
                        }
                    }else if( request.equals("available_games"))
                    {
                        String raspuns = "";
                        if(local.size() > 0){
                            System.out.println("Asta din local DB");
                            for (int i = 0; i < local.size(); i++) {
                                if(!local.get(i).game.itsOver && local.get(i).game.getPlayer2() == null){
                                    raspuns += "Jocul cu id-ul = ";
                                    raspuns += i;
                                    raspuns += ",";
                                }
                            }
                        }
                        else{
                            raspuns = "Nu sunt jocuri disponibile";
                        }
                        if(raspuns.equals(""))
                            raspuns = "Nu sunt jocuri disponibile";
                        out.println(raspuns);
                        out.flush();
                    }else if( request.equals("create_game") ) {
                        String raspuns;
                        Game result = gameController.createGameController();
                        if (result != null) {
                            raspuns = "S-a creat jocul " + games.size();
                            synchronized (this) {
                                local.add(new Local(result, new Board()));
                            }
                        }
                        else
                            raspuns = "Nu s-a putut crea jocul";
                        out.println(raspuns);
                        out.flush();
                    } else if ( request.substring(0,9).equals("join_game")) {
                        String raspuns;
                        int i = Integer.parseInt(request.substring(10));
                        if (gameController.joinGameController(local.get(i).game.getId(),player.getId()) && !local.get(i).game.itsOver) {
                            raspuns = "Ai fost adaugat la jocul cu id " + i;
                            if(local.get(i).game.getPlayer1() == null) {
                                local.get(i).game.setPlayer1(player.getId());
                                local.get(i).setPlayer1(player);
                                local.get(i).setTurn(player);
                                xOro = 1;
                            }
                            else if (local.get(i).game.getPlayer2() == null){
                                local.get(i).game.setPlayer2(player.getId());
                                local.get(i).setPlayer2(player);
                                xOro = 0;
                            }else
                                raspuns = "Nu poti fi adaugat in jocul acesta";
                            raspuns = xOro + raspuns;
                        }
                        else
                            raspuns = "Nu poti fi adaugat in jocul acesta";
                        out.println(raspuns);
                        out.flush();

                        if(!raspuns.equals("Nu poti fi adaugat in jocul acesta")) {
                            String responseWaiting = in.readLine();
                            while (local.get(i).game.getPlayer2() == null) {
                                try {
                                    Thread.sleep(5000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            out.println("ready");
                            out.flush();
                        }
                    }else if ( request.substring(0,11).equals("submit_move"))
                    {
                        System.out.println("procesez submit");
                        for (Local localBD : local) {
                            if(localBD.game.getPlayer1().equals(player.getId()) || localBD.game.getPlayer2().equals(player.getId())){
                                System.out.println("Playerul " + player.getUsername() + " asteata sa isi inceapa tura");
                                System.out.println("Local bd over " + localBD.game.isItsOver() );
                                while(localBD.turn != player) {
                                    try {
                                        Thread.sleep(5000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println("Playerul " + player.getId() + " se plictiseste si stie ca e tura " + localBD.turn);
                                }
                                if (!localBD.game.itsOver) {
                                    String coordonate = request.substring(12);
                                    int x = Integer.parseInt(coordonate.substring(0, coordonate.indexOf("_")));
                                    int y = Integer.parseInt(coordonate.substring(coordonate.indexOf("_") + 1));
                                    System.out.println(x + " " + y);
                                    while (!localBD.submitMove(x, y, xOro)) {
                                        out.println("Coordonate_invalide");
                                        out.flush();

                                        System.out.println("Procesez x si y");
                                        request = in.readLine();
                                        request = request.substring(12);
                                        x = Integer.parseInt(request.substring(0, request.indexOf("_")));
                                        y = Integer.parseInt(request.substring(request.indexOf("_") + 1));
                                    }
                                    if (localBD.checkOver()) {
                                        localBD.game.setItsOver(true);
                                        System.out.println("Local bd over " + localBD.game.isItsOver() );
                                        gameController.joinGameController(localBD.game.getId(),player.getId());
                                        local.remove(localBD);
                                        String raspuns = "Felicitari ai castigat";
                                        System.out.println("La revedere campionule !");
                                        localBD.turn = null;
                                        localBD.weHaveAWinner = true;
                                        rulare = false;
                                        out.println(raspuns);
                                        out.flush();
                                    } else {
                                        System.out.println("Coordonate bune adaug piesa " + localBD.board.getBoard()[x][y]);
                                        System.out.println("Local bd over " + localBD.game.isItsOver() );
                                        localBD.turn = null;
                                        String raspuns = "Piesa adaugata";
                                        out.println(raspuns);
                                        out.flush();
                                    }
                                    localBD.changeTurn(player);
                                } else {
                                    String raspuns = "Ne parare rau dar ai pierdut";
                                    System.out.println("La revedere jucatorule!");
                                    localBD.turn = null;
                                    rulare = false;

                                    out.println(raspuns);
                                    out.flush();
                                }
                                break;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close(); // or use try-with-resources
            } catch (IOException e) { System.err.println (e); }
        }
    }
}
