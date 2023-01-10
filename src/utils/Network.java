package utils;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import cards.Card;

public class Network {
    public ObjectInputStream in;
    public ObjectOutputStream out;

    public Network(ObjectInputStream in, ObjectOutputStream out) {
        this.in = in;
        this.out = out;

    }

    public Network(Socket connSocket) throws IOException {
        this.out = new ObjectOutputStream(connSocket.getOutputStream());
        this.in = new ObjectInputStream(connSocket.getInputStream());
    }

    public int getInt() {
        try {
            return in.readInt();
        } catch (IOException e) {
            System.out.println("Could not get int");
            e.printStackTrace();
            return -1;
        }
    }

    public void sendInt(int i) {
        try {
            out.writeInt(i);
            out.flush();
        } catch (IOException e) {
            System.out.println("Could not send int");
        }
    }

    public void sendCards(ArrayList<Card> cards) {
        for (Card c : cards) {
            try {
                out.writeObject(c);
                out.flush();
            } catch (IOException e) {
                System.out.println("Could not send cards");
            }
        }
    }

    public ArrayList<Card> getCards(int nbCards) {
        ArrayList<Card> cards = new ArrayList<Card>();
        try {
            for (int i = 0; i < nbCards; i++) {
                cards.add((Card) in.readObject());
            }
            return cards;
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Could not get cards");
            return null;

        }
    }

    public void sendCard(Card c) {
        try {
            out.writeObject(c);
            out.flush();
        } catch (IOException e) {
            System.out.println("Could not send card");
        }
    }

    public Card getCard() {
        try {
            return (Card) in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Could not get card");
            return null;
        }
    }

    public String getString() {
        try {
            return (String) in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Could not get string");
            return null;
        }
    }

    public void sendString(String s) {
        try {
            out.writeObject(s);
            out.flush();
        } catch (IOException e) {
            System.out.println("Could not send string");
        }
    }

    public void close() {
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            System.out.println("Could not close network");
        }
    }
}
