package com.company;
import java.util.Scanner;

public class DifficultyChoose {
    public static void main(String[] args) {
        int min = 0;
        int max = 0;
        Scanner input = new Scanner(System.in);
        boolean minIsLessThanMax = false;
        while (!minIsLessThanMax) {
            System.out.println("Insutrctions: Type in the minimum level of difficulty you want to choose from 0 to 9.");
            boolean minGot = false;
            while(!minGot) {
                if (input.hasNextInt()) {
                    int minTemp = input.nextInt();
                    if (minTemp >= 0 && minTemp <= 9) {
                        min = minTemp;
                        minGot = true;
                    } else {
                        System.out.println("Incorrect input, please type in a level from 0 to 9.");
                    }
                } else {
                    System.out.println("Incorrect input, please type in an integer.");
                }
            }
            boolean maxGot = false;
            while (!maxGot) {
                System.out.println("Insutrctions: Type in the maximum level of difficulty you want to choose from 0 to 9.");
                if (input.hasNextInt()) {
                    int maxTemp = input.nextInt();
                    if (maxTemp >= 0 && maxTemp <= 9) {
                        max = maxTemp;
                        maxGot = true;
                    } else {
                        System.out.println("Incorrect input, please type in a level from 0 to 9.");
                    }
                } else {
                    System.out.println("Incorrect input, please type in an integer.");
                }
            }
            if (min > max) {
                System.out.println("ERROR: minimum level has to be less than maximum.");
            } else {
                minIsLessThanMax = true;
            }
        }
    }
}