# LazyReader
Ever wanted to make a book easier to read? Well with lazy reader, now you can!

## Quick Start Guide and Overview Video

A quick start guide, troubleshooting tips, and a demo of the working program can be found [here](https://youtu.be/LbDbAqgzuek). 
Full start guide will be below as well as images of troubleshooting.

## Getting Started

Welcome to the LazyReader. Here is a guideline to help you get started with it.

Step 1: Clone the github repository

Type the following command into your git terminal: 

$ git clone https://github.com/Johnnydaboy/LazyReader.git

![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/Getting%20Started/gif/gitclone.gif?raw=true)

Step 2: Enter the LazyReader repository 

![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/Getting%20Started/gif/cdLazyReader.gif?raw=true)

Step 3: Open IDE of choice at the LazyReader repository

![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/Getting%20Started/gif/openIDE.gif?raw=true)

*note wait until the project is fully detected by the IDE

Step 4: run main method in LazyReader.java and have fun! (Running main examples below)

## Possible troubleshooting

Problem 1: package com.company not detected

    a) restart the IDE
    b) delete and re-clone the repository

Problem 2: LazyReader won't start

    a)  make sure your set the source file correctly and make sure you opened the project on 
        /LazyReader (not one directory above or below)
    b) JDK 11 or above must be installed for the extensions to work
    c) DifficultyClassifier or other classes not detected
        -) Wait a few moments until the IDE project finder can detect everything

Problem 3: Packages not detected

    a) add the the dependencies required for this project to the referenced library
        -) The jars for this project will be in a folder called /jars in /LazyReader
            - ) Add all the jars located in apache-opennlp-1.9.3/lib/
            - ) Add edu.mit.jwi_2.4.0_jdk.jar
            - ) Add evo-inflector-1.2.2.jar
    b) restart your IDE after adding the dependencies if it still doesn't work after adding

    *Dependency Management for VS code can be found in the gif below as well as in our quick start guide

## Dependency Management

The `JAVA DEPENDENCIES` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-pack/blob/master/release-notes/v0.9.0.md#work-with-jar-files-directly).
![alt text](https://github.com/Microsoft/vscode-java-pack/raw/master/release-notes/v0.9.0/reference-jar-files.gif)

## Folder Structure

The workspace contains three folders by default, where:

- `src` : the folder to maintain sources (as well as the WordNet dictionary)
- `jars`: the folder to maintain dependencies
- `lib` : the folder to maintain various binary and text files

## Purpose of the Software
This LazyReader program classifies words in the English vocabulary based on the frequency of the word used in everyday life based on the NGLS database. It features several libraries: MIT JWI, OpenNLP, WordNet, and Apache to retrieve and replace synonyms of nouns based on the input level of "difficulty" by the user in the given sentence. This difficulty replacement tool can be used to both replace words to make it simpler to understand or can be used to replace words to add variety to word choice within a sentence.

## Running Main

When you run main you will be prompted with two options:

    Do you want to set your own difficulty range? (y/n)

![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/Getting%20Started/gif/running/runningMain.gif?raw=true)

Choosing y will allow you to pick 2 numbers (min and max) to set as the difficulty for your sentence translation

![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/Getting%20Started/gif/running/runningSetRange.gif?raw=true)

Choosing n will create a test which based on your score will generate a range to set as the difficulty for your sentence translation

![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/Getting%20Started/gif/running/runningTest.gif?raw=true)

You can change the text which you want to translate in the file text.txt which is located in LazyReader/src/com/company/text.txt
The translated sentence will then be outputted to the console.

![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/Getting%20Started/gif/running/text.PNG?raw=true)
![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/Getting%20Started/gif/running/changingtext.gif?raw=true)