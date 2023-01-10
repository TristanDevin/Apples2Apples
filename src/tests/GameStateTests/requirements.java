package tests.GameStateTests;

import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import org.junit.*;

import deck.*;
import game.*;
import player.Player;
import cards.*;
import utils.*;
import utils.FileReader;

public class requirements {

    public Game gameSetup(int nbPlayers) {
        String greenAppleDeck = "C:/Users/User/Desktop/INSA/INSA4TC/S1Erasmus/SoftwareEngineering/reexam/mycode/greenApple.txt";
        String redAppleDeck = "C:/Users/User/Desktop/INSA/INSA4TC/S1Erasmus/SoftwareEngineering/reexam/mycode/redApples.txt";
        Game game = new Game(redAppleDeck, greenAppleDeck, new ArrayList<Player>(nbPlayers));
        for (int i = 0; i < nbPlayers; i++) {
            game.players.add(new Player(i + 1));
        }
        return game;
    }

    @Test
    public void requirement1() {
        Deck greenAppleDeck = DeckFactory.createDeck(
                "C:/Users/User/Desktop/INSA/INSA4TC/S1Erasmus/SoftwareEngineering/reexam/mycode/greenApple.txt",
                "green");
        ArrayList<String> greenAppleDeckText = new ArrayList<String>();
        greenAppleDeckText = new FileReader()
                .scan("C:/Users/User/Desktop/INSA/INSA4TC/S1Erasmus/SoftwareEngineering/reexam/mycode/greenApple.txt");

        for (int i = 0; i < greenAppleDeckText.size(); i++) {
            assertEquals(greenAppleDeckText.get(i), greenAppleDeck.get(i).toString());
        }
    }

    @Test
    public void requirement2() {
        Deck greenAppleDeck = DeckFactory.createDeck(
                "C:/Users/User/Desktop/INSA/INSA4TC/S1Erasmus/SoftwareEngineering/reexam/mycode/redApples.txt",
                "red");
        ArrayList<String> greenAppleDeckText = new ArrayList<String>();
        greenAppleDeckText = new FileReader()
                .scan("C:/Users/User/Desktop/INSA/INSA4TC/S1Erasmus/SoftwareEngineering/reexam/mycode/redApples.txt");

        for (int i = 0; i < greenAppleDeckText.size(); i++) {
            assertEquals(greenAppleDeckText.get(i), greenAppleDeck.get(i).toString());
        }
    }

    @Test
    public void requirement3() {
        Game game = gameSetup(4);
        game.initGame();
        Deck greenAppleDeck = DeckFactory.createDeck(
                "C:/Users/User/Desktop/INSA/INSA4TC/S1Erasmus/SoftwareEngineering/reexam/mycode/greenApple.txt",
                "green");
        System.out.println(greenAppleDeck);
        System.out.println(game.greenAppleDeck);
        Deck redAppleDeck = DeckFactory.createDeck(
                "C:/Users/User/Desktop/INSA/INSA4TC/S1Erasmus/SoftwareEngineering/reexam/mycode/redApples.txt",
                "red");
        // Now we just asserts that they are the same, but in a different order.
        assertEquals(greenAppleDeck.size(), game.greenAppleDeck.size());
        assertEquals(redAppleDeck.size(), game.redAppleDeck.size() + 7 * 4);
        // We deal 7*4 cards to the players, so we have to add the cards already dealt.
        assertNotSame(greenAppleDeck, game.greenAppleDeck);
        assertNotSame(redAppleDeck, game.redAppleDeck);
    }

    @Test
    public void requirement4() {
        Game game = gameSetup(4);
        game.initGame();
        for (int i = 0; i < game.players.size(); i++) {
            assertEquals(7, game.players.get(i).hand.size());
        }
    }

    @Test
    public void requirement5() {
        // We test that at least once, the judge isnt the same player
        Game game = gameSetup(4);
        game.initGame();
        game.chooseRdnJudge();
        int judge1 = game.getJudge();
        int judge2;
        do {
            game.chooseRdnJudge();
            judge2 = game.getJudge();
        } while (judge1 == judge2);
        assertNotSame(judge1, judge2);
    }

    @Test
    public void requirement6f() {
        // It makes no sense to test this with bots, so we test it with a human player,
        // and set up a connection   
    }

    public void requirement


}
