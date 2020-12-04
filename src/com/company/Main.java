package com.company;

import edu.mit.jwi.*;
import edu.mit.jwi.item.*;
import edu.mit.jwi.morph.WordnetStemmer;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Dictionary dict = new Dictionary(new File("/home/jonathanpi/Computer Science/LazyReader/LazyReader/src/dict"));
        dict.open();
        WordnetStemmer stemmer = new WordnetStemmer(dict);

        List<String> test = stemmer.findStems("feet", POS.NOUN);
        for (int i = 0; i < test.size(); i++) {
            System.out.println(test.get(i));
        }

        IIndexWord indexWord = dict.getIndexWord("aphorist", POS.NOUN);
        IWordID wordID = indexWord.getWordIDs().get(0);
        IWord word = dict.getWord(wordID);
        System.out.println("Gloss = " + word.getSynset().getGloss());

        getSynonyms(dict, "aphorist");
    }

    public static void getSynonyms(IDictionary dict, String newWord) {
        IIndexWord idxWord = dict.getIndexWord(newWord, POS.NOUN);
        IWordID wordID = idxWord.getWordIDs().get(0);
        IWord word = dict.getWord(wordID);
        ISynset synset = word.getSynset();

        for(IWord w : synset.getWords()) {
            System.out.println(w.getLemma());
        }
    }
}
