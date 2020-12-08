package com.company;

import edu.mit.jwi.*;
import edu.mit.jwi.item.*;
import edu.mit.jwi.morph.WordnetStemmer;
import edu.mit.jwi.morph.IStemmer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        //Dictionary dict = new Dictionary(new File("/home/jonathanpi/Computer Science/LazyReader/LazyReader/src/dict"));
        Dictionary dict = new Dictionary(new File("/home/jonathanpi/Computer Science/LazyReader/src/dict"));
        dict.open();
        
        System.out.println(getStem("teachers", POS.NOUN, dict));
        System.out.println(getSynonyms("professorasdas", POS.NOUN, dict));
        /*
        IStemmer stemmer = new WordnetStemmer(dict);
        List<String> test = stemmer.findStems("boots", POS.NOUN);
        for (int i = 0; i < test.size(); i++) {
            System.out.println(test.get(i));
        }

        IIndexWord indexWord = dict.getIndexWord("aphorist", POS.NOUN);
        IWordID wordID = indexWord.getWordIDs().get(0);
        IWord word = dict.getWord(wordID);
        System.out.println("Gloss = " + word.getSynset().getGloss());

        getSynonyms(dict, "teacher");
        */
    }

    public static String simplestWord(int min, int max, String word, Map<String, Integer> cWords) {
        return "";
    }

    public static List<String> getSynonyms(String word, POS partOfSpeech, IDictionary dict) {
        List<String> synonyms = new ArrayList<>();

        word = getStem(word, partOfSpeech, dict);
        IIndexWord idxWord = dict.getIndexWord(word, partOfSpeech);
        // .get() must be in stem form else it'll return null
        if(idxWord == null) {
            return null;
        }
        IWordID wordID = idxWord.getWordIDs().get(0);
        IWord dictWord = dict.getWord(wordID);
        ISynset synset = dictWord.getSynset();
        for(IWord w : synset.getWords()) {
            //System.out.println(w.getLemma());
            synonyms.add(w.getLemma());
        }

        return synonyms;
    }
    
    public static String getStem(String original, POS partOfSpeech, IDictionary dict) {
        IStemmer stemmer = new WordnetStemmer(dict);
        List<String> test = stemmer.findStems(original, partOfSpeech);
        /*
        for (int i = 0; i < test.size(); i++) {
            System.out.println(test.get(i));
        }
        */
        if(!test.isEmpty()) {
            return test.get(0);
        }
        return original;
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


    /*

    

    private List<String> getSynonyms(String word, POS partOfSpeech) {
        List<String> synonyms = new ArrayList<>();
        IIndexWord idxWord = dict.getIndexWord(word, partOfSpeech);
        // .get() must be in stem form else it'll return null
        IWordID wordID = idxWord.getWordIDs().get(0);
        IWord dictWord = dict.getWord(wordID);
        ISynset synset = dictWord.getSynset();
        for(IWord w : synset.getWords()) {
            System.out.println(w.getLemma());
            synonyms.add(w.getLemma());
        }

        return synonyms;
    }
    
    private String getStem(String original, POS partOfSpeech) {
        IStemmer stemmer = new WordnetStemmer(dict);
        List<String> test = stemmer.findStems(original, partOfSpeech);
        for (int i = 0; i < test.size(); i++) {
            System.out.println(test.get(i));
        }

        return test.get(0);
    }

    */
}
