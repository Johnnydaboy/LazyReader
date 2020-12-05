package com.company;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class DifficultyClassifier {

    private static int numberOfContainers;

    public static void main(String[] args) throws Exception {
        String wordFileLocation = "C:\\Users\\toaya\\Documents\\GitHub\\LazyReader\\mostFreqWords.txt";
        Map<String, Integer> cWords = new TreeMap<>();
        cWords = classifyWords(wordFileLocation, 5);
        System.out.println("difficulty " + wordClassification("victimizer", cWords));
    }

    /**
     * @param wordFile - file location of word list txt document with words in
     *                   difficulty order
     * @return - Map with each word mapped to a difficulty level from 1 to 10.
     *           Scale is set exponentially (2x, 4x, 8x, 16x, 32x, etc.)
     */
    public static Map<String, Integer> classifyWords(String wordFile) throws Exception {
        return classifyWords(wordFile, 10);
    }

    /**
     * 
     * @param wordFile - file location of word list txt document with words in
     *                   difficulty order
     * @param containers - number of containers to be created representing the number of difficulty levels
     * @return - Map with each word mapped to a difficulty container from a scale of 1 to the container
     *           valued specified. Scale is set exponentially (2x, 4x, 8x, 16x, 32x, etc.)
     * @throws Exception - If container value passed is less than 2 
     */
    public static Map<String, Integer> classifyWords(String wordFile, int containers) throws Exception {
        if (containers < 3) {
            throw new Exception("Container Error: Number of containers must be at least 2.");
        }
        // Global container constant set to container value.
        numberOfContainers = containers;

        // Subtract one from containers, largest container value reserved for words not in mostFreqWords.txt 
        containers -= 1; 
        Map<String, Integer> classifiedWords = new TreeMap<>();
        int totalWords = totalNumberWords(wordFile);
        
        // Calculate splitter value based on number of containers 
        double growth = 2; 
        double splitter = 0; 
        for(int i = 0; i < containers; i++){
            splitter += growth; 
            growth *= 2; 
            System.out.println(splitter);
        }
        double varX = totalWords / splitter;
        
        // Calculating the number of words in each container level and storing in array
        File file = new File(wordFile);
        Scanner sc = new Scanner(file); 

        Integer[] levelCount = new Integer[containers];
        int wordCount = 0; 
        for (int i = 0; i < containers; i++){
            levelCount[i] = (int) (Math.pow(2.0, i + 1) * varX);
            wordCount += levelCount[i];

            // If the total words are not split evenly, all extra words are placed into the
            // second to last container element in array
            if(i == containers-1){
                levelCount[i] += (totalWords - wordCount); ;
            }
        }

        // Words are mapped to difficulty container based on array, levelCount, created above
        for(int i = 0; i < containers; i++){
            for (int v = levelCount[i]; v > 0; v--){
                classifiedWords.put(sc.nextLine(), containers);
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
            // If the word is not found in Map, the word is defined to be in the highest difficulty container 
            return numberOfContainers; 
        }
    }
}

