package app.CardGame;

public class Game {
    protected int playerTotal;
    protected int hostTotal;
    protected Deck deck;

    public Game(){
        this.deck = new Deck();
        this.playerTotal = 0;
        this.hostTotal = 0;
    }
    

    public int getPlayerTotal() {
        return playerTotal;
    }


    public int getHostTotal() {
        return hostTotal;
    }


    public String play(){
        this.deck.shuffle();
        for(int round  = 1 ; round <= 2; round++){
            System.out.printf("After round %d\n",round);
            Cards playerCard = drawPlayer();
            Cards hostCard = drawHost();
            System.out.printf("Player draw: %s\nHost draw: %s\n",playerCard.toString(),hostCard,toString());
            playerTotal += playerCard.getValue();
            hostTotal += hostCard.getValue();
            System.out.printf("Host: %d\nPlayer: %d\n",hostTotal,playerTotal);
        }
        if(hostTotal > playerTotal){
            return "Host win";
        }else if (hostTotal < playerTotal){
            return "Player win";
        }else{
            return "Its a draw";
        }
    }

    private Cards drawPlayer(){
        return this.deck.drawCards();
    }

    private Cards drawHost(){
        return this.deck.drawCards();
    }
}
