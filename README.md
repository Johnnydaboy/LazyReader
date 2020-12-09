# LazyReader
LazyReader is a program that determines the difficulty level of words in a sentence and replaces said word with an easier to understand synonym. Difficulty is determined by where the specified word stands in terms of the most commonly referenced words in the English language. Difficulty is set on an exponential scale with more words being considered difficult than easy. The user can set the Difficulty scale by selecting the number of container levels they would like the words to be classified into. If a user specifies there to be 5 containers, all the words will be split on an exponential scale with container 1 having the most used words but also the least number of words. Container 4 will contain difficult words and the greatest number of words. The last container, or in this case container 5, is always reserved for words that are not contained in the referenced list of most used words. The program utilizes Apache OpenNLP, WordNet, and Apache Evo Inflector APIs. OpenNLP is used for Part of Speech (POS) categorization of words in sentences. Utilizing this data, we are able to determine which words are nouns, verbs, as well as if the word is plural or singular. In the current version of LazyReader, only nouns are replaced. WordNet is utilized to find synonyms of words in the English language. Apache Evo Inflector is used to convert words in singular tense to plural tense based on the classification provided by OpenNLP.

## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

## Dependency Management

The `JAVA DEPENDENCIES` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-pack/blob/master/release-notes/v0.9.0.md#work-with-jar-files-directly).
