import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;

public class OpenNLPTest {
    public static void main(String[] args) throws IOException {
        //Loading Parts of speech-maxent model       
        InputStream inputStream = new 
            //FileInputStream("/home/jonathanpi/Computer Science/LazyReader/LazyReader/lib/en-pos-maxent.bin");
            FileInputStream("C:\\Users\\toaya\\Documents\\GitHub\\en-pos-maxent.bin");
            
        POSModel model = new POSModel(inputStream); 
            
        //Instantiating POSTaggerME class 
        POSTaggerME tagger = new POSTaggerME(model); 
            
        String sentence = "This is a test sentence!"; 
            
        //Tokenizing the sentence using WhitespaceTokenizer class  
        WhitespaceTokenizer whitespaceTokenizer= WhitespaceTokenizer.INSTANCE; 
        String[] tokens = whitespaceTokenizer.tokenize(sentence);         
        
        //Generating tags 
        String[] tags = tagger.tag(tokens);


        // Building word and POS relationship 
        Map<String, String> POSSentence = wordPOSCreator(tokens, tags);

        // Prints word and corresponding POS
        print(POSSentence);

        // Instantiating the POSSample class
        POSSample sample = new POSSample(tokens, tags);
        System.out.println(sample.toString());
    }

    // Prints word and corresponding simplified POS 
    public static void print(Map<String, String> words) {
        for (String key : words.keySet()){
            System.out.println(key + ", " + words.get(key));
            
        }
    }

    // Word and POS Identifier placed into a map
    // Simplifies POS tag to "N", "A", "V", "D", etc. 
    // LinkedHashMap keeps order of elements inserted into Map 
    public static Map<String, String> wordPOSCreator (String[] tokens, String[] tags) {
        Map<String, String> POSIdentifier = new LinkedHashMap<>();
        for (int i = 0; i < tokens.length; i++){
            POSIdentifier.put(tokens[i], tags[i].substring(0,1));
        }
        return POSIdentifier; 
    }
}