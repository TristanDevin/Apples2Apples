package game;

import java.util.*;
import java.util.concurrent.*;

import cards.*;
import deck.*;
import player.*;

public class Game {
    public Deck greenAppleDeck;
    public Deck redAppleDeck;
    public ArrayList<Player> players;
    public Displayer display;
    public ExecutorService threadPool;

    public Game(String redAppleFile, String greenAppleFile, ArrayList<Player> players) {
        this.greenAppleDeck = DeckFactory.createDeck(greenAppleFile, "green");
        this.redAppleDeck = DeckFactory.createDeck(redAppleFile, "red");
        this.display = new Displayer();
        this.players = players;

    }

    public void initGame() {
        this.greenAppleDeck.shuffle();
        this.redAppleDeck.shuffle();
        for (Player p : this.players) {
            for (int i = 0; i < 7; i++) {
                p.hand.add(this.redAppleDeck.get(0));
                this.redAppleDeck.remove(0);
            }
            if (!p.isBot) {
                p.com.sendCards(p.hand);
            }
        }
    }
    // We preselect the judge, so that the first player to play is not the judge

    public void chooseRdnJudge() {
        Random rand = new Random();
        int randjudge = rand.nextInt(players.size());
        players.get(randjudge).isJudge = true;
    }

    public void game() {
        boolean gameOn = true;
        initGame();
        chooseRdnJudge();
        int pointsToWin = pointsToWin();
        while (gameOn) {
            sendRoles();
            sendGreenApple();
            HashMap<Integer, Card> playedCards = waitForPlayers();
            sendCardsToPlayers(playedCards);
            int turnWinner = judgeTurn(playedCards);
            showTurnWinner(turnWinner, playedCards);
            gameOn = checkIsNotOver(pointsToWin);
            updateHand();
            chooseNewJudge();
        }
        endGame();

    }

    public void sendCardsToPlayers(HashMap<Integer, Card> playedCards) {
        Collection<Card> values = playedCards.values();
        ArrayList<Card> played = new ArrayList<>(values);
        Collections.shuffle(played);
        for (Player p : this.players) {
            if (!p.isBot && !p.isJudge) {
                p.com.sendCards(played);
            }
        }
    }

    public void endGame() {
        int winnerId = getWinner();
        for (Player p : this.players) {
            if (!p.isBot) {
                p.com.sendString("end");
                if (p.points.size() == pointsToWin()) {
                    p.com.sendString("winner");
                } else {
                    p.com.sendString("loser");
                    p.com.sendString("Player" + winnerId + " has won the game !");
                }
            }
        }
    }

    public void chooseNewJudge() {
        int currentJudge = getJudge();
        this.players.get(currentJudge).isJudge = false;
        if (currentJudge == this.players.size() - 1) {
            this.players.get(0).isJudge = true;
        } else {
            this.players.get(currentJudge + 1).isJudge = true;
        }
    }

    public void updateHand() {
        for (Player p : this.players) {
            if (!p.isJudge) {
                if (!p.isBot) {
                    while (p.hand.size() < 7) {
                        p.hand.add(redAppleDeck.get(0));
                        redAppleDeck.remove(0);
                    }
                    p.com.sendCards(p.hand);
                } else {
                    while (p.hand.size() < 7) {
                        p.hand.add(redAppleDeck.get(0));
                        redAppleDeck.remove(0);
                    }
                }
            }
        }
    }

    public void showTurnWinner(int turnWinner, HashMap<Integer, Card> playedCards) {
        Card winningCard = playedCards.get(turnWinner);
        // First we see who won the game
        for (Player p : this.players) {
            if (!p.isBot) {
                if (p.isJudge) {
                    p.com.sendString("Player " + turnWinner + " has won this turn");
                } else {
                    if (p.id == turnWinner) {
                        p.com.sendString("winner");
                        p.com.sendCard(greenAppleDeck.get(0));
                        p.points.add((GreenApple) greenAppleDeck.get(0));
                        greenAppleDeck.remove(0);
                    } else {
                        p.com.sendString("loser");
                        p.com.sendString("Player " + turnWinner + " has won this turn, with the card" + winningCard);
                    }
                }
            } else if (p.id == turnWinner) {
                p.points.add((GreenApple) greenAppleDeck.get(0));
                greenAppleDeck.remove(0);
            }
        }
        System.out.println("Player " + turnWinner + " has won this turn");
    }

    public void sendGreenApple() {
        for (Player p : this.players) {
            if (!p.isBot) {
                p.com.sendCard(greenAppleDeck.get(0));
            }
        }
    }

    public boolean checkIsNotOver(int pointsToWin) {
        for (Player p : this.players) {
            System.out.println("Player " + p.id + " has " + p.points.size() + " points");
            if (p.points.size() >= pointsToWin) {
                System.out.println("Player " + p.id + " has won the game");
                return false;
            }
        }
        return true;

    }

    public int pointsToWin() {
        switch (players.size()) {
            case 4:
                return 8;
            case 5:
                return 7;
            case 6:
                return 6;
            case 7:
                return 5;
            default:
                return 4;
        }
    }

    public HashMap<Integer, Card> waitForPlayers() {
        this.threadPool = Executors.newFixedThreadPool(players.size());
        HashMap<Integer, Card> playedCards = new HashMap<>();
        // We need to make sure that every player can play at the same time
        // We create an array of players without the judge, so we dont create a unused
        // thread
        for (Player p : this.players) {
            if (!p.isJudge) {
                Runnable task = new Runnable() { // Make sure every player can answer at the same time
                    @Override
                    public void run() {
                        if (!p.isBot) {
                            int cardId = p.com.getInt() - 1;
                            playedCards.put(p.id, p.hand.get(cardId));
                            boolean isRemoved = p.hand.remove(p.hand.get(cardId));
                            if (!isRemoved) {
                                System.out.println("Card not removed");
                            }
                        } else {
                            System.out.println("Bot playing");
                            playedCards.put(p.id, p.hand.get(0));
                            System.out.println("Player " + p.id + " played" + p.hand.get(0));
                            p.hand.remove(0);
                        }
                    }
                };
                threadPool.execute(task);
            }
        }
        threadPool.shutdown();
        try

        {
            threadPool.awaitTermination(5, TimeUnit.MINUTES);
            System.out.println("All players have played");
            return playedCards;
        } catch (InterruptedException e) {
            System.out.println("We waited for too long");
            e.printStackTrace();
        }
        System.out.println("If we made it here, we did not wait for the threads");
        return null;
    }

    public int judgeTurn(HashMap<Integer, Card> playedCards) {
        Collection<Card> values = playedCards.values();
        ArrayList<Card> played = new ArrayList<>(values);
        Collections.shuffle(played);
        int chosenId = -1;
        for (Player p : this.players) {
            if (p.isJudge) {
                if (!p.isBot) {
                    p.com.sendCards(played);
                    int chosenCardId = p.com.getInt(); // We get the id of the cards chosen by the judge
                    // Then map it to know the id of the player who played it
                    chosenId = getIdFromCard(playedCards, played.get(chosenCardId - 1));
                    System.out.println("chosen card is " + played.get(chosenCardId - 1));
                    return chosenId;
                } else {
                    // For the bot, it is simpler, we can simply randomly choose a player id, that
                    // is not its own
                    System.out.println("Bot judging");
                    do {
                        chosenId = (int) (Math.random() * this.players.size() + 1);
                    } while (chosenId == p.id);
                    return chosenId;
                }
            }

        }
        return chosenId;
    }

    public void sendRoles() {
        for (Player p : this.players) {
            if (!p.isBot) {
                if (p.isJudge == true) {
                    p.com.sendString("judge");
                } else {
                    p.com.sendString("player");
                }
            }
        }
    }

    public int getWinner() {
        int winnerId = -1;
        for (Player p : this.players) {
            if (p.points.size() == pointsToWin()) {
                winnerId = p.id;
            }
        }
        return winnerId;
    }

    public int getJudge() {
        for (Player p : this.players) {
            if (p.isJudge) {
                return p.id;
            }
        }
        return -1;
    }

    public static int getIdFromCard(HashMap<Integer, Card> hm, Card value) {
        for (int i : hm.keySet()) {
            if (hm.get(i).equals(value)) {
                return i;
            }
        }
        return -1;
    }

}
