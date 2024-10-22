package app.WordDistribute;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordDistribution {
    
    public static void main(String[] args) {

        try{
            if(args.length < 1){
                System.exit(0);
            }
            List<File> files = new ArrayList<>();
            File path = new File(args[0]);
            File[] listOfFiles = path.listFiles();
            if (listOfFiles != null){
                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].isFile()) {
                      files.add(listOfFiles[i]);
                      //System.out.println(listOfFiles[i].getName());
                    }
                   }
                }
            WordList listofWord = new WordList();
            for(File file: files){
                //System.out.println(file.getName());
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);

                String line = "1";
                 while(line != null){
                    line = br.readLine();
                    if(line == null){
                        break;
                    }
                    //System.out.println(line);
                    //remove punctuation
                    line = line.replaceAll("\\p{Punct}","").toLowerCase().trim();
                    String[] inputs = line.split(" ");
                    for(int idx = 0 ; idx < inputs.length - 1; idx++){
                        Word current = listofWord.getWord(inputs[idx]);
                        Word nextWord = listofWord.getWord(inputs[idx+1]);

                        current.addNextWord(nextWord);
                        listofWord.addWord(current);
                        listofWord.addWord(nextWord);
                    }
                 }
                 br.close();
                 fr.close();
                 //System.out.println("Done with the reading");

            }

            Word start = listofWord.choose();

            while(start != null){
                System.out.println(start.getName());
                //start.getNext();
                start = start.chooseNext();
            }

            
        }
        catch(IOException ex){
            System.out.println("ERROR");
            System.err.println(ex.getMessage());
        }
    }
}
