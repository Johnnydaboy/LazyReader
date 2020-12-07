package com.company;

import org.atteo.evo.inflector.English;

public class Pluralizer {
    public static void main(String[] args) throws Exception {
        System.out.println(pluralize("Friend"));
    }

    public static String pluralize (String word){
        return English.plural(word);
    }
}
