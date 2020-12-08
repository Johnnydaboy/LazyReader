package com.company;

import org.atteo.evo.inflector.English;

public class Pluralizer {
    public static void main(String[] args) throws Exception {
        System.out.println(pluralize("foot"));
    }

    public static String pluralize (String word){
        return English.plural(word);
    }
}
 