# LazyReader
Ever wanted to make a book easier to read? Well with lazy reader, now you can!

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
![alt text](https://github.com/Johnnydaboy/LazyReader/blob/master/pictures/constructorFinal.PNG?raw=true)

Construct all instances of the fields with all of the file paths to the libraries and specify the file path for the input stream and the libraries.

![alt text](https://github.com/Johnnydaboy/LazyReader/blob/master/pictures/mainFinal.PNG?raw=true)

Specify the directory to the class. Then create the LazyReader class called lazyBook which will contain all of the synonym replacing capabilities. Call the startLazyReader method to set the classification of the minimum and maximum difficulties and the program will simplify the specified sentences.

## startLazyReader
![alt text](https://github.com/Johnnydaboy/LazyReader/blob/master/pictures/startLazyReader.PNG?raw=true)
![alt text](https://github.com/Johnnydaboy/LazyReader/blob/master/pictures/startLazyReader2.PNG?raw=true)

Call the startLazyReader in main, with the parameters of directory path of lazyReader and the LazyReader class lazyBook which we create in the main. Then the console will prompt the classifier questions to choose the minimum and maximum level classifications manually or through a test. Then call the simplifier method by referencing the lazyBook class with the specified sentence and calling the minimum and maximum classification levels of synonyms. This method will print out a new string sentence with the nouns replaced by the specified classification levels. Then specify the directory of the .txt file containing the sentences that you want to simplify (line 103).

## simplifier
![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/simplifer1.PNG?raw=true)

In order to user WordNet, we need to convert OpenNLP (line 110).

![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/simplifier2.PNG?raw=true)


## User Guide Video
