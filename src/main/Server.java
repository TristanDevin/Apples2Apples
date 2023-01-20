package main;

import java.io.*;
import java.net.*;
import java.util.*;

import game.Game;
import player.*;

public class Server {
    private ArrayList<Player> playerList;
    private ServerSocket sSocket;
    private String redAppleFile;
    private String greenAppleFile;
    private int players;

    public Server(String ip, int port, int players, int humans, String redAppleFile, String greenAppleFile) {
        this.playerList = new ArrayList<Player>();
        this.redAppleFile = redAppleFile;
        this.greenAppleFile = greenAppleFile;
        this.players = players;
        try {
            this.sSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Could not listen on port " + port);
            e.printStackTrace();
        }

        addBots(players, humans);

    }

    
    /** 
     * @param humanPlayers
     */
    public void queue(int humanPlayers) {
        for (int i = players - humanPlayers; i < players; i++) {
            Socket human;
            try {
                System.out.println("Waiting for human players to connect");
                human = sSocket.accept();
                this.playerList.add(new Player(i + 1, human));
                System.out.println("Human player created...sending id");
                this.playerList.get(i).com.sendInt(i + 1);
                this.playerList.get(i).com.sendInt(players);
                // We also send them the number of people that will be in the game
            } catch (IOException e) {
                System.out.println("Could not accept human player");
                e.printStackTrace();
            }
        }
        System.out.println("All human players connected...game will soon start");

        Game game = new Game(greenAppleFile, redAppleFile, playerList);
        game.game();
        System.out.println("Game is over, closing server");

        close();
    }

    public void close() {
        try {
            sSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * @param players
     * @param humans
     */
    public void addBots(int players, int humans) {
        for (int i = 0; i < players - humans; i++) {
            this.playerList.add(new Player(i + 1));
        }
    }

}
