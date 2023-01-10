package player;

import cards.*;
import utils.Network;

import java.io.*;
import java.net.*;
import java.util.*;

public class Player {
    public int id;
    public ArrayList<Card> hand;
    public ArrayList<GreenApple> points;
    public boolean isJudge = false;
    public boolean isBot = true;
    public Network com;

    // We create a player with an id and an empty hand. No connection socket,
    // therefore is a bot
    public Player(int id) {
        this.id = id;
        points = new ArrayList<GreenApple>(); // This array represents the points the player has
        this.hand = new ArrayList<Card>();
    }

    public Player(int id, Socket conn) { // With a connection socket, therefore is a human
        this.id = id;
        this.isBot = false;
        points = new ArrayList<GreenApple>(); // This array represents the points the player has
        this.hand = new ArrayList<Card>();
        try {
            this.com = new Network(conn);
        } catch (Exception e) {
            System.out.println("Could not create network object");
            e.printStackTrace();
        }
        System.out.println("Player " + id + " created");
    }

    public Network getCom() {
        if (isBot)
            return null; // If the player is a bot, there is no connection
        else
            return com;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void removeCard(Card card) {
        hand.remove(card);
    }

    public void addPoint(GreenApple point) {
        points.add(point);
    }

}
