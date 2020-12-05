package com.company;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class DifficultyClassifier {
    public static void main(String[] args) throws IOException {
        String wordFileLocation = "C:\\Users\\toaya\\Documents\\GitHub\\LazyReader\\mostFreqWords.txt";
        Map<String, Integer> cWords = new TreeMap<>();
        cWords = classifyWords(wordFileLocation);
        System.out.println("difficulty " + wordClassification("victimizer", cWords));
    }

    /**
     * @param wordFile - file location of word list txt document with words in difficulty order 
     * @return - Map with each word mapped to a difficulty level from 1 - 9 on a linear scale 
     * @throws IOException
     */
    public static Map<String, Integer> classifyWords(String wordFile) throws IOException {
        Map<String, Integer> classifiedWords = new TreeMap<>();
        int totalWords = totalNumberWords(wordFile);
        double varX = totalWords / 1022.0;

        File file = new File(wordFile);
        Scanner sc = new Scanner(file); 

        Integer[] levelCount = new Integer[9];
        int wordCount = 0; 
        for (int i = 0; i < 9; i++){
            levelCount[i] = (int) (Math.pow(2.0, i + 1) * varX);
            wordCount += levelCount[i];
            if(i == 8){
                levelCount[i] += (totalWords - wordCount); ;
            }
        }

        for(int i = 0; i < 9; i++){
            for (int v = levelCount[i]; v > 0; v--){
                classifiedWords.put(sc.nextLine(), i+1);
            }
        }
        sc.close();
        return classifiedWords; 
    }

    /**
     * @param wordFile - file location of word list txt document with words in difficulty order 
     * @return - Integer representing the total number of words in wordFile 
     * @throws IOException
     */
    public static int totalNumberWords(String wordFile) throws IOException {
        int wordCount = 0; 
        File file = new File(wordFile);
        Scanner sc = new Scanner(file); 
        while (sc.hasNextLine()) {
            wordCount++; 
            sc.nextLine();
        }
        sc.close();
        return wordCount; 
    }

    /**
     * @param word - String that needs to be found in wordList
     * @param wordList - all words in word file mapped to difficulty level 
     * @return - integer representing the difficult on a scale of 1 to 9. If word is not
     *           found in wordList, difficulty level 10 is returned 
     * @throws IOException
     */
    public static int wordClassification(String word, Map<String, Integer> wordList) throws IOException {
        word = word.toLowerCase().replaceAll("\\s", ""); 
        if(wordList.containsKey(word)){
            return wordList.get(word);
        } else {
            return 10; 
        }
    }
}

