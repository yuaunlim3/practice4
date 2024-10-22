package app.CardGame;


public class Cards {

    public final String suit;
    public final String name;
    public final int value;

    public Cards(String name, String suit, int value){
        this.name = name;
        this.suit = suit;
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
    
    @Override
    public String toString(){
        return String.format("Your card is:\n SUIT: %s\n NAME: %s\n VALUE: %d\n",suit,name, value);
    }
    
}
