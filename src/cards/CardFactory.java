package cards;

//This class is used to help us create cards

public class CardFactory {

    public CardFactory() {
    }

    public static RedApple createRedApple(String text) {
        return new RedApple(text);
    }

    public static GreenApple createGreenApple(String text) {
        return new GreenApple(text);
    }
}