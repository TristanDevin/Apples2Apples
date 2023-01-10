package main;

import java.io.*;
import java.net.*;
import java.util.*;

import cards.*;
import game.Displayer;
import player.Player;
import utils.Network;

public class Client {
    public String ip;
    public int port;
    public Player me;
    public Network com;
    public Displayer display;
    public int nbPlayers;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.display = new Displayer();
    }

    public void queue() {
        System.out.println("You are in queue");
        connectToServer(ip, port);
        int id = com.getInt();
        this.me = new Player(id);
        System.out.println("You are connected to the server as player: " + id);
        this.nbPlayers = com.getInt();
        System.out.println("Waiting for all players to connect...");
        this.me.hand = com.getCards(7);
        System.out.println("All players are connected ! Game will start soon");
        // When we receive our hand, it means the game is starting, therefore we can
        // play
        play();

    }

    public void connectToServer(String ip, int port) {
        try {
            Socket conn = new Socket("127.0.0.1", port);
            this.com = new Network(conn);
            System.out.println("Successfully created network object");
        } catch (IOException e) {
            System.out.println("could not connect to server");
            e.printStackTrace();
        }
    }

    public void play() {
        boolean isOn = true;
        while (isOn) {
            String phase = com.getString();
            switch (phase) {
                case "judge":
                    display.displayNewTurn(true);
                    display.displayGreenApple(com.getCard());
                    ArrayList<Card> playedCards = waitForPlayers();
                    chooseTurnWinner(playedCards);
                    break;
                case "player":
                    display.displayNewTurn(false);
                    display.displayGreenApple(com.getCard());
                    display.displayHand(this.me.hand);
                    playCard();
                    display.displayPlayedCards(com.getCards(nbPlayers - 1));
                    hasWonTurn();
                    this.me.hand = updateHand();
                    break;
                case "end":
                    isOn = false;
                    switch (com.getString()) {
                        case "winner":
                            display.displayWonGame();
                            break;
                        case "loser":
                            display.displayLostGame(com.getString());
                            break;
                    }
            }
        }
        com.close();

    }

    public void hasWonTurn() {
        String hasWon = com.getString();
        switch (hasWon) {
            case "winner":
                System.out.println("You won this turn");
                this.me.points.add((GreenApple) com.getCard());
                break;
            case "loser":
                System.out.println("You lost this turn\n");
                System.out.print(com.getString());
                break;
        }

    }

    public ArrayList<Card> updateHand() {
        ArrayList<Card> hand = com.getCards(7);
        return hand;
    }

    public void playCard() {
        System.out.println("Choose a card to play");
        int card = inputInt(1, this.me.hand.size());
        com.sendInt(card);
        System.out.println("You sent card: " + card);
        this.me.hand.remove(card - 1);
    }

    public void chooseTurnWinner(ArrayList<Card> playedCards) {
        display.displayPlayedCards(playedCards);
        System.out.println("Choose the winner of this turn\n");
        int winner = inputInt(1, playedCards.size());
        com.sendInt(winner);
        System.out.println(com.getString());

    }

    public ArrayList<Card> waitForPlayers() {
        ArrayList<Card> playedCards = com.getCards(this.nbPlayers - 1); // We expect to receive nbPlayers - 1 card,
                                                                        // since we are not playing
        return playedCards;
    }

    public int inputInt(int min, int max) {
        Scanner sc = new Scanner(System.in);
        int input = 0;
        boolean isInt = false;
        while (!isInt) {
            try {
                input = sc.nextInt();
                if (input >= min && input <= max) {
                    isInt = true;
                } else {
                    System.out.println("Please enter a valid number");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                sc.next();
            }
        }
        return input;
    }
}
