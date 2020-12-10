package com.company;

import edu.mit.jwi.*;
import edu.mit.jwi.item.*;
import edu.mit.jwi.morph.WordnetStemmer;
import edu.mit.jwi.morph.IStemmer;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.atteo.evo.inflector.English;

public class LazyReader {

    IDictionary dict;

    InputStream inputStreamPOS;
    InputStream inputStreamToken;
    POSModel modelPOS;
    POSTaggerME tagger;
    DifficultyClassifier classifier;
    TokenizerModel modelToken;

    /**
     * 
     * @param dirPath - the path for the directory of this project
     * @throws Exception
     * 
     */
    public LazyReader(String dirPath) throws Exception {
        dict = new Dictionary(new File(dirPath + "src" + File.separator + "dict"));

        inputStreamPOS = new FileInputStream(dirPath + "lib" + File.separator + "en-pos-maxent.bin");
        modelPOS = new POSModel(inputStreamPOS);
        tagger = new POSTaggerME(modelPOS); 

        inputStreamToken = new FileInputStream(dirPath + "lib" + File.separator + "en-token.bin");
        modelToken = new TokenizerModel(inputStreamToken);

        classifier = new DifficultyClassifier(dirPath);

        dict.open();
    }
    
    public static void main(String[] args) throws Exception {
        String dirPath = System.getProperty("user.dir") + File.separator;
        LazyReader lazyBook = new LazyReader(dirPath);
        startLazyReader(dirPath, lazyBook);
    }
    
    public static void startLazyReader(String dirPath, LazyReader lazyBook) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to set your own difficulty range? (y/n)");
        String ans = scanner.nextLine().toLowerCase();

        int min = 1;
        int max = 1;
        
        // If user does not input either Y/y or N/n, question will be asked again until valid input 
        while (!ans.equals("n") && !ans.equals("y")){
            System.out.println("Incorrect input, please enter either Y/y or N/n.");
            System.out.println("Do you want to set your own difficulty range? (y/n)");
            ans = scanner.nextLine().toLowerCase();
        }
        // If user wants to set their own range
        if(ans.equals("y")) {
            DifficultyChoose getRange = new DifficultyChoose(scanner);
            min = getRange.getMin();
            max = getRange.getMax();
        } 
        // If user wants to take test to set the range
        else {
            DifficultyTest test = new DifficultyTest(lazyBook.gDifficultyClassifier());

            Map<String, Integer> exam = new HashMap<>();
            exam = test.getRandomWords(lazyBook.gDifficultyClassifier());
            test.testScore(exam, test.performTest(exam));

            min = test.getMinScore();
            max = test.getScore();
        }

        File file = new File(dirPath + "src" + File.separator + "com" + File.separator + 
                             "company" + File.separator + "text.txt");
        // Create Scanner which reads from file
        scanner = new Scanner(file);

        // Read a paragraph from a text file
        String paragraph = "";
        while(scanner.hasNextLine()) {   
            paragraph = paragraph + scanner.nextLine() + " ";
        }
        paragraph = paragraph.trim();

        scanner.close();

        // Translate paragraph from text file into simpler sentence
        System.out.println(lazyBook.simplifier(paragraph, min, max));
    }

    /**
     * 
     * @param sentence - The String sentence which is to be simplified
     * @return - The simplified sentence in String form
     * @throws IOException - if the file is not found
     * 
     * This function will take in a String sentence (most likely a complex one) 
     * and will replace the complex nouns in the sentence with simpler ones
     * to return as a simpler String sentence
     */
    public String simplifier(String sentence, int min, int max) throws IOException {
        // Check if sentence is empty and return if so
        if(sentence.equals("")) {
            return "";
        }

        String simpleSentence = "";
        String[] wordTokens = tokenizePuncutation(sentence)''
        String[] tokenTags = tagger(wordTokens);
        POS[] whichToSimplify = toSimplify(tokenTags);

        // Fencepost part, the first part of the sentence should not have a " " + word to avoid
        // extra whitespace
        // If the POS is non-null, replace it in this step. Else concatenate like normal
        if(whichToSimplify[0] != null) {
            // get the simplier synonym here and concatenate it to simpleSentence
            List<String> synonymList = getSynonyms(wordTokens[0], whichToSimplify[0]);

            // if there are no synonyms or if the synonym is itself (by checking if the list is size one)
            // concatenate the original word instead
            if(synonymList == null || synonymList.size() == 1) {
                simpleSentence = simpleSentence + wordTokens[0];
            } else {
                // Get the synonym closest to max (else it'll just be the lowest)
                String simpleSyn = simplestSyn(min, max, synonymList);
                // remember to repluralize if the word was originally plural
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

                List<String> synonymList = getSynonyms(wordTokens[i], whichToSimplify[i]);
                
                if(synonymList == null || synonymList.size() == 1) {
                    simpleSentence = simpleSentence + " " + wordTokens[i];
                } else {
                    String simpleSyn = simplestSyn(min, max, synonymList);
                    if(tokenTags[i].equals("NNS")) {
                        simpleSyn = English.plural(simpleSyn);
                    }
                    simpleSentence = simpleSentence + " " + simpleSyn;
                }

            }
            // if the token currently is a puncutation mark do not add an 
            // extra space before (maintaining sentence spacing convention)
            else if (isPunctuation(tokenTags[i])) {
                simpleSentence = simpleSentence + wordTokens[i];
            } else {
                simpleSentence = simpleSentence + " " + wordTokens[i];
            }
        }

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
     * @param hm - the Map which you want to sort by value
     * @return - the Map which is sorted by value
     */
    private Map<String, Integer> sortByValue(Map<String, Integer> hm) 
    { 
        // Create a list from elements of HashMap 
        List<Map.Entry<String, Integer> > list = 
               new LinkedList<Map.Entry<String, Integer> >(hm.entrySet()); 
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() { 
            public int compare(Map.Entry<String, Integer> o1,  
                               Map.Entry<String, Integer> o2) 
            { 
                return (o1.getValue()).compareTo(o2.getValue()); 
            } 
        }); 
          
        // put data from sorted list to hashmap  
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>(); 
        for (Map.Entry<String, Integer> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    } 

    /**
     * 
     * @param min - the minimum difficulty the synonym should be replaced by
     * @param max -  the maxium difficulty the synonym should be replaced by
     * @param synonyms - the synonyms which will replace the word
     * @return - the synonym whose difficulty falls closest to the int max, if nothing is found the lowest difficulty will be returned
     * @throws IOException - 
     */
    private String simplestSyn(int min, int max, List<String> synonyms) throws IOException {

        Map<String, Integer> synDiffValueOrdered = new HashMap<>();

        for(String synonym : synonyms) {
            synDiffValueOrdered.put(synonym, classifier.wordClassification(synonym));
        }

        // sort the map by ascending order for value
        synDiffValueOrdered = sortByValue(synDiffValueOrdered);

        // save the current lowest difficutly synonym in case highestDiffSyn has no potential value
        String lowestDiffSyn = "";
        boolean firstToken = true;
        
        // get the highestDiffSyn that is closest to the max and still above min
        String highestDiffSyn = "";
        for(String synonym : synDiffValueOrdered.keySet()) {
            if(firstToken) {
                // the first key in the map should be the lowest difficulty synonym
                lowestDiffSyn = synonym;
                firstToken = false;
            }
            int diff = synDiffValueOrdered.get(synonym);
            if(diff >= min && diff <= max) {
                highestDiffSyn = synonym;
            }
        }
        // if such a synonym in range is not found, set it to the lowestDiffSyn and return it
        if(highestDiffSyn.equals("")) {
            highestDiffSyn = lowestDiffSyn;
        }
        return highestDiffSyn;
    }


    /**
     * 
     * @param word - the word of which you want to find syonyms for
     * @param partOfSpeech - which part of speech (noun, verb, adj) does it fall under
     * @return - a list of synonyms to the word or null if none can be found in the database
     */
    private List<String> getSynonyms(String word, POS partOfSpeech) {
        List<String> synonyms = new ArrayList<>();

        word = getStem(word, partOfSpeech);
        IIndexWord idxWord = dict.getIndexWord(word, partOfSpeech);
        if(idxWord == null) {
            return null;
        }
        // Get synonyms from the dictionary and add it to a list
        IWordID wordID = idxWord.getWordIDs().get(0);
        IWord dictWord = dict.getWord(wordID);
        ISynset synset = dictWord.getSynset();
        for(IWord w : synset.getWords()) {
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
        switch(token) {
            case "NN": 
                return POS.NOUN;
            case "NNS": 
                return POS.NOUN;
        }

        return null;
    }   
    
    public DifficultyClassifier gDifficultyClassifier() {
        return classifier;
    }
}
