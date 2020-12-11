# LazyReader
Ever wanted to make a book easier to read? Well with lazy reader, now you can!

## Quick Start Guide and Overview Video

A quick start guide, troubleshooting tips, and a demo of the working program can be found [here](https://youtu.be/LbDbAqgzuek). 

## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

## Dependency Management

The `JAVA DEPENDENCIES` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-pack/blob/master/release-notes/v0.9.0.md#work-with-jar-files-directly).
![alt text](https://github.com/Microsoft/vscode-java-pack/raw/master/release-notes/v0.9.0/reference-jar-files.gif)

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
