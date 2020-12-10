# LazyReader
Authored By: Ayan Gupta, Jonathan Pi, Tony Choi

## Project Overview: 
LazyReader is a program that determines the difficulty level of words in a sentence and replaces said word with an easier to understand synonym. Difficulty is determined by where the specified word stands in terms of the most commonly referenced words in the English language. Difficulty is set on an exponential scale with more words being considered difficult than easy. The user can set the Difficulty scale by selecting the number of container levels they would like the words to be classified into. If a user specifies there to be 5 containers, all the words will be split on an exponential scale with container 1 having the most used words but also the least number of words. Container 4 will contain difficult words and the greatest number of words. The last container, or in this case container 5, is always reserved for words that are not contained in the referenced list of most used words. 
The program utilizes Apache OpenNLP, WordNet, and Apache Evo Inflector APIs. OpenNLP is used for Part of Speech (POS) categorization of words in sentences. Utilizing this data, we are able to determine which words are nouns, verbs, as well as if the word is plural or singular. In the current version of LazyReader, only nouns are replaced. WordNet is utilized to find synonyms of words in the English language. Apache Evo Inflector is used to convert words in singular tense to plural tense based on the classification provided by OpenNLP. 


## LazyReader Class: 
The LazyReader class is the main class that will simplify a sentence. LazyReader will use OpenNLP to first tokenize a sentence that is passed to it. It will determine the POS of each word and provide the POS tags of each word in a separate array. In order to utilize these POS tokens into WordNet we must then also convert the OpenNLP POS tags int WordNet POS tags and store that as a separate array; we do this by passing any OpenNLP POS tag and changing it to its WordNet POS tag equivalent. Using these arrays, we can determine which nouns need replacing and once replaced do we need to convert the root form of the noun back into plural form. We then replace the noun with a synonym set by a difficulty range specified by either taking the test or by the user.

|Sentence    |Children|cause|headaches|to  |humans|all |over|the |county|and |across|the |macrocasm|
|------------|--------|-----|---------|----|------|----|----|----|------|----|------|----|---------|
|OpenNLP tags|NNS     |NN   |NNS      |TO  |NNS   |DT  |IN  |nDT |NN    |CC  |IN    |DT  |NN       |
|WordNet tags|NOUN    |NOUN |NOUN     |null|NOUN  |null|null|null|NOUN  |null|null  |null|NOUN     |
|Pluralize   |p       |s    |p        |x   |p     |x   |x   |x   |s     |x   |x     |x   |s        |

*Note: As you can see the POS tagger is not 100% perfect (as it is just a model), as shown by the fact that cause is being identified as a noun rather than a verb.

Anything synonym replacing a token corresponding to NNS (see column one above: Children, NNS, NOUN, p), needs to be re-pluralized late on (kid -> kids) and sent back to following the original grammar of the sentence. 

This difficulty level is based on a database of word which is sorted from most to least frequent (we are making the assumption that more frequent words are less difficult), it is also able to take in any other database of words which is classified from most to least frequent or from least to most difficult. The DifficultyClassifier Class will provide difficulties to each of the words in the database according which will be used in the LazyReader (see DifficultyClassifier below for more detail).

In order to replace a difficult noun with a simpler one the LazyReader Class will follow a specific set of instructions. First it must identify that the noun indeed has synonyms (else the noun will be returned into the altered “simpler” sentence as it), it will then rank the synonyms from least to most difficult based on the DifficultyClassifier, it will then proceed to pick the synonym closest to the upper bound of the difficulty range (i.e. if range is 5-8, it’ll try to get the synonym closest to difficulty 8) and if a synonym is not found in the range it’ll return the lowest difficulty synonym within the synonym list. This synonym will then go on to replace the original word in the sentence and be pluralized accordingly as we change it to its stem (root aka. most likely singular) form while processing it. The resulting altered sentence will be returned and printed to the console. 

e.g. 
"Children cause headaches to humans all over the country and across the macrocosm."
->
"kids cause worries to men all over the land and across the world"

![alt text](https://github.com/Johnnydaboy/LazyReader/blob/master/pictures/constructorFinal.PNG?raw=true)

Construct all instances of the fields with all of the file paths to the libraries and specify the file path for the input stream and the libraries.

![alt text](https://github.com/Johnnydaboy/LazyReader/blob/master/pictures/mainFinal.PNG?raw=true)

Specify the directory to the class. Then create the LazyReader class called lazyBook which will contain all of the synonym replacing capabilities. Call the startLazyReader method to set the classification of the minimum and maximum difficulties and the program will simplify the specified sentences.

![alt text](https://github.com/Johnnydaboy/LazyReader/blob/master/pictures/startLazyReader.PNG?raw=true)
![alt text](https://github.com/Johnnydaboy/LazyReader/blob/master/pictures/startLazyReader2.PNG?raw=true)

Call the startLazyReader in main, with the parameters of directory path of lazyReader and the LazyReader class lazyBook which we create in the main. Then the console will prompt the classifier questions to choose the minimum and maximum level classifications manually or through a test. Then call the simplifier method by referencing the lazyBook class with the specified sentence and calling the minimum and maximum classification levels of synonyms. This method will print out a new string sentence with the nouns replaced by the specified classification levels. Then specify the directory of the .txt file containing the sentences that you want to simplify (line 103).

![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/simplifer1.PNG?raw=true)

In order to user WordNet, we need to convert OpenNLP (line 110).

![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/simplifier2.PNG?raw=true)

## DifficultyClassifier Class: 
The DifficultyClassifier class is utilized to parse data from a .txt file containing a sorted list of the most commonly used words in the English language and classify them (can also use any other text document of words in order of least to most difficulty or any other difficulty related way of ranking English words). Difficulty is determined by the number of containers specified by the user. If the user does not define the container count, the program will default to 10 containers. Classification occurs on an exponential scale. 
The first container will contain 2x words, second container will container 4x words, third container will contain 8x words, and so on. 

bin 1: x 
bin 2: 2x 
bin 3: 4x 
bin 4: 8x
....

x = words in document/ total bins added up (x + 2x + 4x...)

The last container is always reserved for words that are not included in the .txt document with most used words. The classified words are placed into a map where the key is the word and the corresponding value (the difficulty) is the classification integer (which difficulty bin does it belong to). 

Classifies words based on an exponential pattern. Uses scanner to read a document with most commonlly used words at the top of the document sorted in decending order. Words and their corresponding difficulty classification are stored in a map. 

![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/DifficultyClassifier_classifyWords.PNG?raw=true)

Method to identify the difficulty of specific word. If the word has not been classified, it is not contained in the current word dataset. Therefore, it is assigned the last container. Last container corresponds to the most difficult words. 

![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/DifficultyClassifier_wordClassification.PNG?raw=true)

## DifficultyChoose Class
The DifficultyChoose Class is used to prompt the user to manually enter in their own minimum and maximum classification levels through a scanner in the console. The scanner will prompt the user to first enter a minimum difficulty level from 0 to 9, then the maximum level from 0 to 9. The difficulty level must be entered as an integer, not a string, and will prompt an error message and ask the user to reenter levels if the maximum level is less than the minimum level. After the scanner has received inputs, the min and max variables will be updated as the input from the scanner.

![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/chooseFinal.PNG?raw=true)

Prompt the user to input the minimum level and make sure it is an integer from 0 to 9.

![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/chooseFinal2.PNG?raw=true)

Prompt the user to input the maximum level and make sure it is an integer from 0 to 9. Then check to see if min is less than max, otherwise prompt the user to enter the correct numbers.

## DifficultyTest Class 
The DifficultyTest Class is primarily used to determine the reading level of each user. By default, when this options is chosen over manual difficulty selection, the user will be given a 10 question quiz. DifficultyTest will randomlly pull 10 words from mostFreqWords.txt and ask if the user knows the word. Using scanner, we take y/n input from the user for each of the 10 questions. The answers are stored in a 1D String array that will be used to calculate the overall score of the user. Using the count of correct answers, a range is set for acceptable difficulty level. This test is unique in that it can either simplify sentences based on the score of the test, or sentences can be made more complicated to challenge the user. 

Randomlly store 10 words and their corresponding difficulty score. 

![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/DifficultyTest_getRandomWords.PNG?raw=true)

Perform the test using the random words from the getDifficultyTest method. Prompt user to enter y/n depending on if they know the word. If a character other than y/n or Y/N is entered, user will be prompted to enter a valid input. Adds answer provided by the user into an array. 

![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/DifficultyTest_performTest.PNG?raw=true)

Score the test based on how many words the user answers "y" to. Calculate range for word difficulty. 

![alt text](https://github.com/Johnnydaboy/LazyReader/blob/dev/pictures/DifficultyTest_testScore.PNG?raw=true)

## Future Improvements: 
LazyReader has very limited functionality in this release version. Currently there is no support for word replacement other than nouns. The main reasoning behind this is POS like verbs require us to identify and then apply the correct verb tense to the replacement word. While OpenNLP does identity the verb tense, we did not have time to implement a verb tense modifier for the synonym word. OpenNLP does not container functionality to do this. However, the Stanford University NLP API contains functionality that allows for verb tense conversion that can be utilized in the future. Additionally, future releases of this program will include a difficulty test functionality where a user will be able to take a short exam with words to determine their current reading level. Utilizing this data, we will be able to select appropriate words to replace based on the user. This functionality has been abstracted, implementation did not occur due to time restrictions. 
