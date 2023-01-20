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
        this.hand = new Hand();
        try {
            this.com = new Network(conn);
        } catch (Exception e) {
            System.out.println("Could not create network object");
            e.printStackTrace();
        }
        System.out.println("Player " + id + " created");
    }

    
    /** 
     * @return Network
     */
    public Network getCom() {
        if (isBot)
            return null; // If the player is a bot, there is no connection
        else
            return com;
    }

    
    /** 
     * @param card
     */
    public void addCard(Card card) {
        this.hand.add(card);
    }

    
    /** 
     * @param card
     */
    public void removeCard(Card card) { 
        for (Card c : this.hand) {
            if (c.equals(card)) {
                this.hand.remove(c);
                break;
            }
        }
    }



}
