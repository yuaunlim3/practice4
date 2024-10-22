package app.CardGame;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Deck {
    protected Queue<Cards> deck;
    private Cards[] cards;
    protected int total;
    public static final List<String> SUIT = Arrays.asList(
    "Diamond", "Spade", "Heart", "Clubs"
    );

    public static final String[] NAME ={
        "Ace", "Two", "Three","Four", 
        "Five","Six","Seven","Eight","Nine",
        "Ten","Jack","Queen","King"
    };

    public static final int[] VALUE = {
        1, 2, 3, 4, 
        5, 6, 7, 8, 9, 
        10, 10, 10, 10
    };

    public Deck(){
        this.deck = new LinkedList<Cards>();
        this.total = 52;
        this.cards = new Cards[this.total];
        int idx = 0;
        for (String suit : SUIT) {
            for (int i = 0; i < NAME.length; i++) {
                String name = NAME[i];
                int value = VALUE[i];
                Cards card = new Cards(name, suit, value);
                this.cards[idx] = card;
                idx++; 
            }
        }

        createDeck();
  

    }

    public Cards drawCards(){
        if(this.total >0){
            this.total--;
            //System.out.printf("Number of cards left: %d\n",this.total);
            return this.deck.poll();
        }else{
            createDeck();
            shuffle();
        }
        return null;

    }
    
    private void createDeck(){
        Arrays.stream(this.cards).forEach(item -> this.deck.add(item));
    }

    public void shuffle(){
        this.deck = new LinkedList<Cards>();
        Random rand = new SecureRandom();
        for (int idx = this.cards.length - 1; idx > 0; idx--) {
            
            int pos = rand.nextInt(idx + 1);
            Cards temp = this.cards[idx];
            this.cards[idx] = this.cards[pos];
            this.cards[pos] = temp;
        }

        createDeck();

    }


}
