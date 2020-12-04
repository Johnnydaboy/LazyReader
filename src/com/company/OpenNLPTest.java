

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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

        for (int i = 0; i < tokens.length; i++) {
            System.out.printf("%s, %s\n", tokens[i], tags[i]);
        }
        
        //Instantiating the POSSample class
        POSSample sample = new POSSample(tokens, tags); 
        System.out.println(sample.toString()); 
    }
}