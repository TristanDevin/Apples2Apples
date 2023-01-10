package utils;

import java.util.*;

import cards.Card;

public class utils {

    public static int getIdFromCard(HashMap<Integer, Card> hm, Card value) {
        System.out.println("hm: " + hm);
        System.out.println("value: " + value);
        for (int i : hm.keySet()) {

            if (hm.get(i).equals(value)) {
                return i;
            }
        }
        return -1;
    }
}
