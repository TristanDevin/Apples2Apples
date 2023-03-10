package game;

import java.util.ArrayList;

import cards.Card;

public class Displayer {

    public Displayer() {
    }

    
    /** 
     * @param winner
     */
    public void displayLostGame(String winner) {
        System.out.println("");
        System.out.println("*****************************************************");
        System.out.println("**                 GAME OVER                       **");
        System.out.println("**                                                 **");
        System.out.println("**                                                 **");
        System.out.println(winner);
    }

    public void displayWonGame() {
        System.out.println("");
        System.out.println("*****************************************************");
        System.out.println("**                 VICTORY !!!                     **");
        System.out.println("**                                                 **");
        System.out.println("**    CONGRATULATIONS, YOU HAVE WON THE GAME !     **");
    }

    
    /** 
     * @param hand
     */
    public void displayHand(ArrayList<Card> hand) {
        System.out.println("");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println(i + 1 + " : " + hand.get(i));

        }
    }

    
    /** 
     * @param greenApple
     */
    public void displayGreenApple(Card greenApple) {
        System.out.println("");
        System.out.println("Green Apple : " + greenApple);
    }

    
    /** 
     * @param playedCards
     */
    public void displayPlayedCards(ArrayList<Card> playedCards) {
        System.out.println("The cards played are: ");
        System.out.println("");
        for (int i = 0; i < playedCards.size(); i++) {
            System.out.println(i + 1 + " : " + playedCards.get(i));
            System.out.println("");
        }
    }

    
    /** 
     * @param winner
     */
    public void displayWinner(int winner) {
        System.out.println("");
        System.out.println("The winner is player " + winner);
    }

    
    /** 
     * @param judge
     */
    public void displayNewTurn(boolean judge) {
        System.out.println("");
        System.out.println("*****************************************************");
        if (judge) {
            System.out.println("**                 NEW ROUND - JUDGE               **");
        } else {
            System.out.println("**                    NEW ROUND                    **");

        }
        System.out.println("*****************************************************");
        System.out.println("");
    }
}
