package deck;

public class DeckFactory {

    public DeckFactory() {
    }

    
    /** 
     * @param file
     * @param type
     * @return Deck
     */
    public static Deck createDeck(String file, String type) {
        switch (type) {
            case "red":
                return new Deck(file, "red");
            case "green":
                return new Deck(file, "green");
            default:
                System.out.println("Invalid deck type");
                return null;
        }
    }
}
