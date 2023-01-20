package cards;

//This class is used to help us create cards

public class CardFactory {

    public CardFactory() {
    }

    
    /** 
     * @param text
     * @return RedApple
     */
    public static RedApple createRedApple(String text) {
        return new RedApple(text);
    }

    
    /** 
     * @param text
     * @return GreenApple
     */
    public static GreenApple createGreenApple(String text) {
        return new GreenApple(text);
    }
}