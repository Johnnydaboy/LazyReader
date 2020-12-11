# LazyReader
Ever wanted to make a book easier to read? Well with lazy reader, now you can!

## Quick Start Guide and Overview Video

A quick start guide, troubleshooting tips, and a demo of the working program can be found [here](https://youtu.be/LbDbAqgzuek). 
Full start guide will be below as well as images of troubleshooting.

## Getting Started

Welcome to the LazyReader. Here is a guideline to help you get started with it.

Step 1: Clone the github repository

$ git clone https://github.com/Johnnydaboy/LazyReader.git

![alt text]()

Step 2: Enter the LazyReader repository 

![alt text]()

Step 3: Open IDE of choice at the LazyReader repository

![alt text]()

wait until the project is fully detected by the IDE

Step 4: run main method in LazyReader.java and have fun!

![alt text]()


## Possible troubleshooting

Problem 1: package com.company not detected

    a) restart the IDE
    b) delete and re-clone the repository

Problem 2: LazyReader won't start

    a) make sure your set the source file correctly and make sure you opened the proejct on /LazyReader (not one directory above or below)
    b) JDK 11 or above must be installed for the extensions to work

Problem 3: Packages not detected

    a) add the the dependencies required for this project to the referenced library
        -) The jars for this project will be in a folder called /jars in /LazyReader
            - ) Add all the jars located in apache-opennlp-1.9.3/lib/
            - ) Add edu.mit.jwi_2.4.0_jdk.jar
            - ) Add evo-inflector-1.2.2.jar
    b) restart your IDE after adding the dependencies if it still doesn't work after adding
    
    *Dependency Management can be found in the gif below as well as in our quick start guide

## Dependency Management

The `JAVA DEPENDENCIES` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-pack/blob/master/release-notes/v0.9.0.md#work-with-jar-files-directly).
![alt text](https://github.com/Microsoft/vscode-java-pack/raw/master/release-notes/v0.9.0/reference-jar-files.gif)

## Folder Structure

The workspace contains three folders by default, where:

- `src` : the folder to maintain sources
- `jars`: the folder to maintain dependencies
- `lib` : the folder to maintain various binary and text files



## Purpose of the Software
This LazyReader program classifies words in the English vocabulary based on the frequency of the word used in everyday life based on the NGLS database. It features several libraries: MIT JWI, OpenNLP, WordNet, and Apache to retrieve and replace synonyms of nouns based on the input level of "difficulty" by the user in the given sentence.

## Getting Started
![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/constructor.PNG?raw=true)

Construct all instances of the fields with all of the file paths to the libraries.

![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/mainAll.PNG?raw=true)

Specify the file path for the input stream and the libraries and create the LazyReader class called lazyBook which will contain all of the synonym replacing capabilities. Also, using a scanner, specify a file path to a .txt file to read more than one sentence and run the simplifier method for individual sentences within the .txt file.

Call the simplifier method by referencing the lazyBook class with the specified sentence and calling the minimum and maximum classification levels of synonyms. This method will print out a new string sentence with the nouns replaced by the specified classification levels

## simplifier
![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/simplifer1.PNG?raw=true)

In order to user WordNet, we need to convert OpenNLP (line 110).
![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/simplifier2.PNG?raw=true)

## DifficultyClassifier
![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/classifyFile.PNG?raw=true)

Load the .txt file of the data of frequency of words.

![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/classifyMethod.PNG?raw=true)

Returns an integer representing the difficulty level of the word selected. The classification is based on an exponential formula which is also based on the specified number of containers and divide the classification categories into the desired number of containers.
