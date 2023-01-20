package tests;

import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import org.junit.*;

import deck.*;
import game.*;
import player.Player;
import cards.*;
import utils.FileReader;

public class requirements {

    @Before
    public void hideSystemOut() {
        PrintStream out = System.out;
        System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {
            }
        }));

    }

    
    /** 
     * @param nbPlayers
     * @return Game
     */
    public Game gameSetup(int nbPlayers) {
        String greenAppleDeck = "C:/Users/User/Desktop/INSA/INSA4TC/S1Erasmus/SoftwareEngineering/reexam/mycode/greenApple.txt";
        String redAppleDeck = "C:/Users/User/Desktop/INSA/INSA4TC/S1Erasmus/SoftwareEngineering/reexam/mycode/redApples.txt";
        Game game = new Game(redAppleDeck, greenAppleDeck, new ArrayList<Player>(nbPlayers));
        for (int i = 0; i < nbPlayers; i++) {
            game.players.add(new Player(i + 1));
        }
        game.initGame();
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
        Deck greenAppleDeck = DeckFactory.createDeck(
                "greenApple.txt",
                "green");
        System.out.println(greenAppleDeck);
        System.out.println(game.greenAppleDeck);
        Deck redAppleDeck = DeckFactory.createDeck(
                "redApples.txt",
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
        for (int i = 0; i < game.players.size(); i++) {
            assertEquals(7, game.players.get(i).hand.size());
        }
    }

    @Test
    public void requirement5() {
        // We test that at least once, the judge isnt the same player
        Game game = gameSetup(4);
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
    public void requirement6() {
        // We cannot check it has been shown to everyone, but we can verify it has been
        // drawn at the end of a turn
        Game game = gameSetup(4);
        game.chooseRdnJudge();
        Card firstGreen = game.greenAppleDeck.get(0);
        game.showTurnWinner(1, game.waitForPlayers());
        assertNotEquals(firstGreen, game.greenAppleDeck.get(0));

    }

    @Test
    public void requirement7() {
        Game game = gameSetup(4);
        game.chooseRdnJudge();
        game.waitForPlayers();
        for (Player player : game.players) {
            if (player.id != game.getJudge()) {
                assertEquals(6, player.hand.size());
            }
        }

    }

    @Test
    public void requirement8() {
        Game game = gameSetup(4);
        game.chooseRdnJudge();
        HashMap<Integer, Card> played = game.waitForPlayers();
        // We show to everyone after the judge played
        game.showTurnWinner(1, played);

    }

    @Test
    public void requirement9() {
        Game game = gameSetup(4);
        game.chooseRdnJudge();
        HashMap<Integer, Card> played = game.waitForPlayers();
        // We check that every players has played
        assertEquals(game.players.size(), played.size() + 1); // Add one for judge
    }

    @Test
    public void requirement10() {
        Game game = gameSetup(4);
        game.chooseRdnJudge();
        Card greenPlayed = game.greenAppleDeck.get(0);
        game.showTurnWinner(1, game.waitForPlayers());
        assertEquals(greenPlayed.toString(), game.players.get(0).points.get(0).toString());
    }

    @Test
    public void requirement11() {
        Game game = gameSetup(4);
        HashMap<Integer, Card> played = game.waitForPlayers();
        // We check that the red apples tgat were played are not in the players hands
        // anymore
        for (Player player : game.players) {
            for (Card card : played.values()) {
                assertFalse(player.hand.contains(card));
            }
        }
    }

    @Test
    public void requirement12() {
        Game game = gameSetup(4);
        game.waitForPlayers();
        for (Player player : game.players) {
            assertEquals(6, player.hand.size());
        }
        game.updateHand();
        for (Player player : game.players) {
            assertEquals(7, player.hand.size());
        }
    }

    @Test
    public void requirement13() {
        Game game = gameSetup(4);
        game.chooseRdnJudge();
        int judge1 = game.getJudge();
        game.chooseNewJudge();
        int judge2 = game.getJudge();
        System.out.println(judge1 + " " + judge2);
        if (judge1 == 4) {
            assertEquals(1, judge2);
        } else {
            assertEquals(judge1 + 1, judge2);
        }

    }

    @Test
    public void requirement14() {
        // I guess we wll do this by saving the first card of the greenAppleDeck
        // Then make a player win a round
        // Then checking that it is the same card
        Game game = gameSetup(4);
        Card firstCard = game.greenAppleDeck.get(0);
        HashMap<Integer, Card> cards = game.waitForPlayers();
        game.showTurnWinner(1, cards);
        assertEquals(firstCard, game.players.get(0).points.get(0));
    }

    @Test
    public void requirement15() {
        for (int i = 4; i <= 8; i++) {
            Game game = gameSetup(i);
            for (int j = 0; j < 12 - i; j++) {
                game.players.get(0).points.add(new GreenApple("whatever"));
            }
            assertFalse(game.checkIsNotOver(game.pointsToWin()));
        }
    }

}
