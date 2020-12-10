package com.company;
import java.util.Scanner;

public class DifficultyChoose {

    private int min = 0;
    private int max = 0;
    
    public DifficultyChoose(Scanner scanner) { 
        Scanner input = scanner;
        boolean minIsLessThanMax = false;

        // Exit while loop when min is less than max
        while (!minIsLessThanMax) {
            
            boolean minGot = false;
            // Loop until a valid min is got
            while(!minGot) {
                System.out.println("Instructions: Type in the minimum level of difficulty you want to choose from 1 to 10.");
                // If it is an int check if it's between 1-10 if so exit 
                if (input.hasNextInt()) {
                    int minTemp = input.nextInt();
                    if (minTemp >= 1 && minTemp <= 10) {
                        min = minTemp;
                        minGot = true;
                    } else {
                        System.out.println("Incorrect input, please type in a level from 1 to 10.");
                    }
                } else {
                    System.out.println("Incorrect input, please type in an integer.");
                    // swallow the token if not int
                    String temp = input.next();
                }
            }

            boolean maxGot = false;
            // Loop until a valid min is got
            while (!maxGot) {
                System.out.println("Instructions: Type in the maximum level of difficulty you want to choose from 1 to 10.");
                // If it is an int check if it's between 1-10 if so exit 
                if (input.hasNextInt()) {
                    int maxTemp = input.nextInt();
                    if (maxTemp >= 1 && maxTemp <= 10) {
                        max = maxTemp;
                        maxGot = true;
                    } else {
                        System.out.println("Incorrect input, please type in a level from 1 to 10.");
                    }
                } else {
                    System.out.println("Incorrect input, please type in an integer.");
                    // swallot the token if not int
                    String temp = input.next();
                }
            }

            // only exit outer while loop if min is less than max
            if (min > max) {
                System.out.println("ERROR: minimum level has to be less than maximum.");
            } else {
                // Check if min is less than max to exit the while loop.
                minIsLessThanMax = true;
            }
        }
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}