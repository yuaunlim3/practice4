package app.WordDistribute;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordList {

    protected List<Word> listOfWords;

    public WordList(){
        listOfWords = new ArrayList<>();
    }

    public Word getWord(String input){
        //System.out.printf("Word to get: %s\n",input);
        Word intendWord = new Word(input);
        for(Word word:this.listOfWords){
            //System.out.printf("Word check with: %s\n",word.getName());
            if(word.getName().equals(input)){
                //System.out.println("Word already exist");
                intendWord = word;
            }
        }
        return intendWord;

    }
    
    public void addWord(Word input){
        //System.out.printf("Word to add: %s\n",input.getName());
        int counter = 0;
        for(Word word:this.listOfWords){
            if(word.getName().equals(input.getName())){
                //System.out.println("Word already exist");
                counter++;
                break;
            }
        }
        if(counter == 0){
            this.listOfWords.add(input);
        }
    }

    public Word choose(){
        int numWords = this.listOfWords.size();
        if(numWords > 0){
            Random rand = new SecureRandom();
            int pos = rand.nextInt(numWords);
            return this.listOfWords.get(pos);
        }
        return null;
    }

    public List<Word> getListOfWords() {
        return listOfWords;
    }
    
}
