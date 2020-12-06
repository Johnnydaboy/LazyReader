package com.company;

import edu.mit.jwi.*;
import edu.mit.jwi.item.*;
import edu.mit.jwi.morph.WordnetStemmer;
import edu.mit.jwi.morph.IStemmer;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class LazyReader {

    IDictionary dict;

    InputStream inputStream;
    POSModel model;
    POSTaggerME tagger;

    public LazyReader(String dictFilePath, String modelFilePath) throws IOException {
        dict = new Dictionary(new File(dictFilePath));
        inputStream = new FileInputStream(modelFilePath);
        model = new POSModel(inputStream);
        tagger = new POSTaggerME(model); 

        dict.open();
    }
    
    public static void main(String[] args) throws IOException {
        //LazyReader lazyBook = new LazyReader("/home/jonathanpi/Computer Science/LazyReader/LazyReader/src/dict", 
        //    "/home/jonathanpi/Computer Science/LazyReader/LazyReader/lib/en-pos-maxent.bin");

        LazyReader lazyBook = new LazyReader("C:\\Users\\toaya\\Documents\\GitHub\\LazyReader\\src\\dict", 
            "C:\\Users\\toaya\\Documents\\GitHub\\en-pos-maxent.bin");


            
            
        
        String sentence = "This is heresey, these are complex, disturbing, terrible, teacher";
        String simpleSentence = lazyBook.simplifier(sentence);

        System.out.println(simpleSentence);
        //POS result = null;
        //lazyBook.getStem("boots", POS.NOUN);
    }

    public String getStem(String original, POS partOfSpeech) {
        IStemmer stemmer = new WordnetStemmer(dict);
        List<String> test = stemmer.findStems(original, partOfSpeech);
        for (int i = 0; i < test.size(); i++) {
            System.out.println(test.get(i));
        }

        return test.get(0);
    }

    private String[] tokenize(String sentence) {
        WhitespaceTokenizer whitespaceTokenizer = WhitespaceTokenizer.INSTANCE; 
        return whitespaceTokenizer.tokenize(sentence);
    }

    private String[] tagger(String[] tokens) {
        return tagger.tag(tokens);
    }

    public String simplifier(String sentence) {
        String simpleSentence = "";
        String[] wordTokens = tokenize(sentence);
        String[] tokenTags = tagger(wordTokens);
        POS[] whichToSimplify = toSimplify(tokenTags);

        for(int i = 0; i < whichToSimplify.length; i++) {
            if(whichToSimplify[i] != null) {
                // get the simplier synonym here and replace
                getSynonyms(wordTokens[i], whichToSimplify[i]);
            } else {
                simpleSentence = simpleSentence + wordTokens[i] + " ";
            }
        }

        simpleSentence = simpleSentence.trim();
        return simpleSentence;
    }

    public void getSynonyms(String word, POS partOfSpeech) {
        IIndexWord idxWord = dict.getIndexWord(word, partOfSpeech);
        IWordID wordID = idxWord.getWordIDs().get(0);
        IWord dictWord = dict.getWord(wordID);
        ISynset synset = dictWord.getSynset();

        for(IWord w : synset.getWords()) {
            System.out.println(w.getLemma());
        }
    }

    private POS[] toSimplify(String[] tags) {
        POS[] whatToSimplify = new POS[tags.length];
        for(int i = 0; i < tags.length; i++) {
            whatToSimplify[i] = nounTags(tags[0]);
        }

        return whatToSimplify;
    }

    // expand in the future to work with POS.VERB potentially if 
    // we can manage to change the verb tense back to it's original form
    private POS nounTags(String token) {
        switch(token) {
            case "NN": 
                return POS.NOUN;
            case "NNS": 
                return POS.NOUN;
        }

        return null;
    }    
}
