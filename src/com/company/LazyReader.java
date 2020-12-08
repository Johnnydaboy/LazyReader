package com.company;

import edu.mit.jwi.*;
import edu.mit.jwi.item.*;
import edu.mit.jwi.morph.WordnetStemmer;
import edu.mit.jwi.morph.IStemmer;

import opennlp.tools.postag.POSModel;
//import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.atteo.evo.inflector.English;

public class LazyReader {

    IDictionary dict;

    InputStream inputStreamPOS;
    InputStream inputStreamToken;
    POSModel modelPOS;
    POSTaggerME tagger;
    DifficultyClassifier classifier;

    /**
     * 
     * @param dictFilePath       - the String filepath for the WordNet dictionary
     * @param modelPOSFilePath   - the String filepath for the POS identifier model
     * @param modelTokenFilePath - the String filepath for the tokenizer model
     * @throws Exception
     * 
     */
    public LazyReader(String dictFilePath, String modelPOSFilePath, String modelTokenFilePath, String filePath)
            throws Exception {
        dict = new Dictionary(new File(dictFilePath));

        inputStreamPOS = new FileInputStream(modelPOSFilePath);
        modelPOS = new POSModel(inputStreamPOS);
        tagger = new POSTaggerME(modelPOS); 

        inputStreamToken = new FileInputStream(modelTokenFilePath);

        classifier = new DifficultyClassifier(filePath);

        dict.open();
    }
    
    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        InputStream propInputStrem = new FileInputStream("/home/jonathanpi/Computer Science/LazyReader/src/com/company/config.properties");
        prop.load(propInputStrem);

        LazyReader lazyBook = new LazyReader(prop.getProperty("filepath") + "src/dict", 
            prop.getProperty("filepath") + "lib/en-pos-maxent.bin", 
            prop.getProperty("filepath") + "lib/en-token.bin",
            prop.getProperty("filepath"));
        
        String sentence = "I woke up to a pounding headache and a roaring migraine.";

        sentence = "";

        /*
        String[] wordTokens = lazyBook.tokenizePuncutation(sentence);
        for(int i = 0; i < wordTokens.length; i++) {
            System.out.println(wordTokens[i]);
        }
        */

        String simpleSentence = lazyBook.simplifier(sentence, 1, 3);

        System.out.println(simpleSentence);
        
        //POS result = null;
        //lazyBook.getStem("boots", POS.NOUN);
    }

    /**
     * 
     * @param sentence - The sentence which is to be simplified
     * @return - The simplified sentence in String form
     * @throws IOException - if the file is not found
     * 
     * This function will take in a sentence (most likely a complex one) 
     * and will replace the complex words in the sentence with simpler ones
     * to return as a simpler sentence
     */
    public String simplifier(String sentence, int min, int max) throws IOException {
        String simpleSentence = "";
        String[] wordTokens = tokenizePuncutation(sentence);
        String[] tokenTags = tagger(wordTokens);
        POS[] whichToSimplify = toSimplify(tokenTags);

        if(whichToSimplify[0] != null) {
            // get the simplier synonym here and replace
            List<String> synonymList = getSynonyms(wordTokens[0], whichToSimplify[0]);

            if(synonymList == null || synonymList.size() == 1) {
                simpleSentence = simpleSentence + wordTokens[0];
            } else {
                String simpleSyn = simplestSyn(min, max, synonymList);
                if(tokenTags[0].equals("NNS")) {
                    simpleSyn = English.plural(simpleSyn);
                }
                simpleSentence = simpleSentence + simpleSyn;
            }

        } else {
            simpleSentence = simpleSentence + wordTokens[0];
        }

        for(int i = 1; i < whichToSimplify.length; i++) {
            if(whichToSimplify[i] != null) {
                // get the simplier synonym here and replace
                List<String> synonymList = getSynonyms(wordTokens[i], whichToSimplify[i]);
                
                if(synonymList == null || synonymList.size() == 1) {
                    simpleSentence = simpleSentence + " " + wordTokens[i];
                } else {
                    String simpleSyn = simplestSyn(min, max, synonymList);
                    if(tokenTags[i].equals("NNS")) {
                        simpleSyn = English.plural(simpleSyn);
                        //System.out.printf("%s NNS\n", simpleSyn);
                    }
                    //System.out.printf("%s NN\n", simpleSyn);
                    simpleSentence = simpleSentence + " " + simpleSyn;
                }

            } else if (isPunctuation(tokenTags[i])) {
                simpleSentence = simpleSentence + wordTokens[i];
            } else {
                simpleSentence = simpleSentence + " " + wordTokens[i];
            }

            //System.out.printf("|%s|, |%s|\n", tokenTags[i], wordTokens[i]);
        }

        simpleSentence = simpleSentence.trim();
        return simpleSentence;
    }

    /**
     * 
     * @param token - the word passed in to be checked if it's a puncutation or not
     * @return - true if it is a puncutation (non-word)
     */
    private boolean isPunctuation(String token) {
        switch(token) {
            case ".": 
                return true;
            case ",": 
                return true;
            case ":": 
                return true;
            case "(": 
                return true;
            case ")":
                return true;
            case "$":
                return true;
            case "'":
                return true;
            case "\"":
                return true;
        }
        return false;
    }

    /**
     * 
     * @param word - 
     * @param partOfSpeech - 
     * @throws IOException
     * 
     */
    /*
    private void getSynonyms(String word, POS partOfSpeech) {
        IIndexWord idxWord = dict.getIndexWord(word, partOfSpeech);
        // .get() must be in stem form else it'll return null
        IWordID wordID = idxWord.getWordIDs().get(0);
        IWord dictWord = dict.getWord(wordID);
        ISynset synset = dictWord.getSynset();
        for(IWord w : synset.getWords()) {
            System.out.println(w.getLemma());
        }
    }
    */

    private int getLowest(List<Integer> difficulties) {
        int placement = 0;
        for(int i = 0; i < difficulties.size(); i++) {
            if(difficulties.get(i) < difficulties.get(placement)) {
                placement = i;
            }
        }

        return placement;
    }

    private String simplestSyn(int min, int max, List<String> synonyms) throws IOException {
        // Grab the difficulties of the synonyms and put them into a list
        List<Integer> synonymDifficulty = new ArrayList<>();
        for(int i = 0; i < synonyms.size(); i++) {
            synonymDifficulty.add(classifier.wordClassification(synonyms.get(i)));
        }

        // save the current lowest difficutly synonym in case highestDiffSyn has no potential value
        String lowestDiffSyn = "";
        int lowest = getLowest(synonymDifficulty);
        lowestDiffSyn = synonyms.get(lowest);

        // Create a map which will be orded by ascending value
        Map<String, Integer> synDiffValueOrdered = new HashMap<>();
        // while the synonyms list is not empty
        while(!synonyms.isEmpty()) {
            // find out which placement has the lowest synonym difficulty
            lowest = getLowest(synonymDifficulty);
            // put that in the map 
            synDiffValueOrdered.put(synonyms.get(lowest), synonymDifficulty.get(lowest));

            // remove the synonym from both lists and continue organizing the map until both lists are empty
            synonyms.remove(lowest);
            synonymDifficulty.remove(lowest);
        }
        
        // get the highestDiffSyn that is closest to the max and still above min
        String highestDiffSyn = "";
        for(String synonym : synDiffValueOrdered.keySet()) {
            int diff = synDiffValueOrdered.get(synonym);
            if(min < diff && diff < max) {
                highestDiffSyn = synonym;
            }
        }

        // if such a synonym is not found, set it to the lowestDiffSyn and return it
        if(highestDiffSyn.equals("")) {
            highestDiffSyn = lowestDiffSyn;
        }
        
        return highestDiffSyn;
    }


    private List<String> getSynonyms(String word, POS partOfSpeech) {
        List<String> synonyms = new ArrayList<>();

        word = getStem(word, partOfSpeech);
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
    
    /**
     * 
     * @param original - the original word which to find the root form of (teachers -> teacher, geese -> goose)
     * @param partOfSpeech - which part of speech it is (verb, noun, adjective)
     * @return - the root form of the word passed in
     */
    private String getStem(String original, POS partOfSpeech) {
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

    /**
     * 
     * @param sentence - the sentence which you want to tokenize into an array of words, tokenized by puncutation
     * @return - the array of Strings from the sentence we want to tokenize
     * @throws IOException - if the file is not found
     * 
     * This function will return the sentence as an array of Strings by seperating them by puncutation
     */
    private String[] tokenizePuncutation(String sentence) throws IOException {
        TokenizerModel modelToken = new TokenizerModel(inputStreamToken);
        Tokenizer tokenizer = new TokenizerME(modelToken);
        return tokenizer.tokenize(sentence);
    }

    /**
     * 
     * @param sentence - the sentence which you want to tokenize into an array of words, tokenized by white space
     * @return - the array of Strings from the sentence we want to tokenize
     * 
     * This function will return the sentence as an array of Strings by seperating them by whitespace
     */
    private String[] tokenizeWhiteSpace(String sentence) {
        WhitespaceTokenizer whitespaceTokenizer = WhitespaceTokenizer.INSTANCE; 
        return whitespaceTokenizer.tokenize(sentence);
    }

    /**
     * 
     * @param tokens - the array of words that need to be converted to opennlp POS 
     * @return - the words which have been converted to an array of opennlp POSs (Strings)
     * 
     * This function will take in a String array of words and return their POS (opennlp POS)
     */
    private String[] tagger(String[] tokens) {
        return tagger.tag(tokens);
    }

    /**
     * 
     * @param tags - the array of opennlp POSs (Strings) which need to be converted to WordNet POSs
     * @return -  an array of WordNet POS
     * 
     * This function will take in an array of String POS (from opennlp) and convert it into an array
     * of WordNet POSs
     */
    private POS[] toSimplify(String[] tags) {
        POS[] whatToSimplify = new POS[tags.length];
        for(int i = 0; i < tags.length; i++) {
            whatToSimplify[i] = nounTags(tags[i]);
        }

        return whatToSimplify;
    }

    // expand in the future to work with POS.VERB potentially if 
    // we can manage to change the verb tense back to it's original form
    /**
     * 
     * @param token - a single opennlp POS 
     * @return - a WordNet POS
     * 
     * This method will take in a String POS (from opennlp) and convert it into a WordNet POS
     */
    private POS nounTags(String token) {
        //System.out.printf("inside nounTags: %s\n", token);
        switch(token) {
            case "NN": 
                return POS.NOUN;
            case "NNS": 
                return POS.NOUN;
        }

        return null;
    }   
    
}
