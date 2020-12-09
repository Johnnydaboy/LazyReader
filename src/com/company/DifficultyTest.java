package com.company;

import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class DifficultyTest {
    DifficultyClassifier wordC;
    private int minScore; 
    private int maxScore; 
    private int score; 

    public static void main(String[] args) throws Exception {
        DifficultyClassifier WordClassifier = new DifficultyClassifier("C:\\Users\\toaya\\Documents\\GitHub\\LazyReader\\");
        DifficultyTest test = new DifficultyTest(WordClassifier);
        Map<String, Integer> exam = new HashMap<>();
        exam = test.getRandomWords(WordClassifier);
        System.out.println(exam);
        test.testScore(exam, test.performTest(exam));
        System.out.println(test.getMinScore() + " " + test.getMaxScore());

    }

    /**
     * @param dClassifier - DifficultyClassifier object to be used to construct DifficultyTest
     * Constructor
     */
    public DifficultyTest(DifficultyClassifier dClassifier) {
        wordC = dClassifier;
    }

    /**
     * 
     * @param dClassifier - Difficulty Classifier object that will be used to randomlly select 10
     *                      words for the test
     * @return - Map containing 10 randomlly selected words and their difficulties 
     * @throws IOException
     */
    private Map<String, Integer> getRandomWords(DifficultyClassifier dClassifier) throws IOException {
        Map<String, Integer> testWords =  new TreeMap<>();
        Random random = new Random();
        List<String> keys = new ArrayList<String>(dClassifier.cWords.keySet());
        for(int i = 0; i < 10; i++){
            String randWord = keys.get(random.nextInt(keys.size()));
            int wordDiff = dClassifier.wordClassification(randWord);
            testWords.put(randWord, wordDiff);
        }
        return testWords; 
    }

    /**
     * 
     * @param test - Map containing a randomlly selected 10 words and their difficulty levels
     * @return - list of all the answers in y/n format from the test 
     */
    private List<String> performTest(Map<String, Integer> test){
        
        List<String> answers = new ArrayList<String>();
        //Scanner scanner = new Scanner(System.in);
        System.out.println("Insutrctions: Type Y or y for Yes and N or n for No");
        for (String word : test.keySet()){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Do you know the defenition/meaning of: " + word);
            String ans = scanner.nextLine().toLowerCase();
            
            // If user does not input either Y/y or N/n, question will be asked again until valid input 
            while (!ans.equals("n") && !ans.equals("y")){
                System.out.println("Incorrect input, please enter either Y/y or N/n.");
                Scanner sc = new Scanner(System.in);
                System.out.println("Do you know the defenition/meaning of: " + word);
                ans = sc.nextLine().toLowerCase();
            }

            answers.add(ans);
        }
        return answers; 
    }

    /**
     * 
     * @param test - Map containing a randomlly selected 10 words and their difficulty levels
     * @param answers - List of answers from the test. Returned after calling performTest 
     * @return - integer representing the average difficulty level of all the missed questions
     *           rounded down to the nearest whole number
     */
    private void testScore(Map<String, Integer> test, List<String> answers){
        double answersCorrect = 0;
        int i = 0; 
        for (String word : test.keySet()) {
            if(answers.get(i).equals("y")){
                answersCorrect++;
            }
            i++;
        }
        score = (int) answersCorrect; 
        minScore = (int)(answersCorrect - answersCorrect/2);
        maxScore = (int)(answersCorrect + answersCorrect/2);
        if(minScore < 1) { 
            minScore = 1;
        }
        if(maxScore > wordC.getContainerCount()){
            maxScore = wordC.getContainerCount();
        }

    }

    public int getMinScore(){
        return minScore; 
    }
    
    public int getMaxScore(){
        return maxScore; 
    }

    public int getScore(){
        return score; 
    }
        
}

