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
import java.util.List;
import java.util.Properties;

public class LazyReader {

    IDictionary dict;

    InputStream inputStreamPOS;
    InputStream inputStreamToken;
    POSModel modelPOS;
    POSTaggerME tagger;

    public LazyReader(String dictFilePath, String modelPOSFilePath, String modelTokenFilePath) throws IOException {
        dict = new Dictionary(new File(dictFilePath));

        inputStreamPOS = new FileInputStream(modelPOSFilePath);
        modelPOS = new POSModel(inputStreamPOS);
        tagger = new POSTaggerME(modelPOS); 

        inputStreamToken = new FileInputStream(modelTokenFilePath);

        dict.open();
    }
    
    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        InputStream propInputStrem = new FileInputStream("/home/jonathanpi/Computer Science/LazyReader/src/com/company/config.properties");
        prop.load(propInputStrem);

        LazyReader lazyBook = new LazyReader(prop.getProperty("filepath") + "src/dict", 
            prop.getProperty("filepath") + "lib/en-pos-maxent.bin", 
            prop.getProperty("filepath") + "lib/en-token.bin");
        
        String sentence = "This is heresey, these are complex, disturbing, terrible, teacher?";

        /*
        String[] wordTokens = lazyBook.tokenizePuncutation(sentence);
        for(int i = 0; i < wordTokens.length; i++) {
            System.out.println(wordTokens[i]);
        }
        */

        String simpleSentence = lazyBook.simplifier(sentence);

        System.out.println(simpleSentence);
        
        //POS result = null;
        //lazyBook.getStem("boots", POS.NOUN);
    }

    public String simplifier(String sentence) throws IOException {
        String simpleSentence = "";
        String[] wordTokens = tokenizePuncutation(sentence);
        String[] tokenTags = tagger(wordTokens);
        POS[] whichToSimplify = toSimplify(tokenTags);

        for(int i = 0; i < whichToSimplify.length; i++) {
            if(whichToSimplify[i] != null) {
                // get the simplier synonym here and replace
                getSynonyms(wordTokens[i], whichToSimplify[i]);
            } else {
                simpleSentence = simpleSentence + wordTokens[i] + " ";
            }

            System.out.printf("%s, %s\n", tokenTags[i], wordTokens[i]);
        }

        simpleSentence = simpleSentence.trim();
        return simpleSentence;
    }

    /*
    public String getStem(String original, POS partOfSpeech) {
        IStemmer stemmer = new WordnetStemmer(dict);
        List<String> test = stemmer.findStems(original, partOfSpeech);
        for (int i = 0; i < test.size(); i++) {
            System.out.println(test.get(i));
        }

        return test.get(0);
    }
    */

    private String[] tokenizePuncutation(String sentence) throws IOException {
        TokenizerModel modelToken = new TokenizerModel(inputStreamToken);
        Tokenizer tokenizer = new TokenizerME(modelToken);
        return tokenizer.tokenize(sentence);
    }

    private String[] tokenizeWhiteSpace(String sentence) {
        WhitespaceTokenizer whitespaceTokenizer = WhitespaceTokenizer.INSTANCE; 
        return whitespaceTokenizer.tokenize(sentence);
    }

    private String[] tagger(String[] tokens) {
        return tagger.tag(tokens);
    }

    public void getSynonyms(String word, POS partOfSpeech) {
        IIndexWord idxWord = dict.getIndexWord(word, partOfSpeech);
        // .get() must be in stem form else it'll return null
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
            whatToSimplify[i] = nounTags(tags[i]);
        }

        return whatToSimplify;
    }

    // expand in the future to work with POS.VERB potentially if 
    // we can manage to change the verb tense back to it's original form
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
