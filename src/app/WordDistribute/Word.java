package app.WordDistribute;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Word {
    protected String name;
    protected Map<Word,Integer> nextWordList;
    private int total;
    private List<Word> nextWords;

    public Word(String name){
        this.name = name;
        this.nextWordList = new HashMap<>();
        this.total = 0;
        this.nextWords = new ArrayList<>();
    }
    

    public String getName() {
        return name;
    }

    public void addNextWord(Word nextWord){
        //System.out.printf("Word: %s added as nextword to Word: %s\n",nextWord.getName(),this.name);
        this.nextWordList.computeIfPresent(nextWord,(k,v) -> v + 1);
        this.nextWordList.computeIfAbsent(nextWord, v -> 1);
        this.total += 1;
        this.nextWords.add(nextWord);
    }

    public void getNext(){
        //System.out.printf("The next words for %s:\n",this.name);
        //System.out.printf("Number: %d\n",this.total);
        for(Word words: nextWordList.keySet()){
            double res = (double) nextWordList.get(words) / (double) this.total;
            System.out.printf("%s %.2f\n",words.getName(),res);
        }
    }

    public Word chooseNext(){
        if (total > 0){
            Random rand = new SecureRandom();
            int pos = rand.nextInt(total);
            return nextWords.get(pos);

        }
        else{
            System.out.println("NO NEXT WORDS");
            return null;
        }
    }



}
