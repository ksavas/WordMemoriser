# WordMemoriser

[Türkçe](https://github.com/ksavas/WordMemoriser/blob/master/README-Tr.md)

WordMemoriser is a web application that is being developed in microservices architecture. This appllication is still being developed, but the application provides the core needs at the moment, so it can be used.

The purpose of the application is improving english vocabulary by testing about new learnt words. The detailed information has been explained belowe.

## Technical details
- The application is a web application which means, the application has a client side and and server side. The server side is developed in microservices architecture that shall   be deployed on cloud. 
- The microservices are communicating each other over http and the files are tranport as JSON (RESTful).
- The client side is designed in mvc framework, which means whole business logic are performed in server side and the html result is returned.
  - The html result is developed in html/css and also javascirpt language and jquery 3.5.1. and of course mvc technology.
- The application has 5 microservices:
  - Eureka Server: For holding the list of microservices in a place by registering them.
    - JDK 11, Spring Boot 2.3.8 RELEASE (Different Java version for containers)
  - Gateway Service: For abstracting microservices from the user by using gateway technology. Netflix Zuul Gateway is used for gateway proxy.
    - JDK 8, Spring Boot 2.3.10 BUILD-SNAPSHOT
  - Word Service: Java spring boot mvc and RESTful webservice. The Word Service has an mvc interface for user ineraction and also has RESTful web service for communicating with       other microservices. It has its own database.
    - JDK 8, Spring Boot 2.3.1 RELEASE
  - Account Service: Java spring boot mvc and RESTful webservice. The Word Service has an mvc interface for user ineraction and also has RESTful web service for interacting with     other microservices. It has its own database.
    - JDK 8, Spring Boot 2.3.1 RELEASE
  - Mvc Service is going to be removed from project
- Spring Jpa framework is used for ORM with mysql db. (h2db is going to be used in future)
- Whole project is installed, packaged and deployed via Apache Maven 3.6.3ç
- Mysql 8.0.20 is used for database. (h2db is going to be used in future)

There are 2 microservices that provide main needs of the project:
- Word Service (Eureka Client): For word and test operations.
- Account Service (Eureka Client): For account and account related word operations.

This figure represents microservice design of the project:
<img src="https://github.com/ksavas/WordMemoriser/blob/develop/SS/Word%20Memories%20Architecture.PNG"><br>


If you want to use that application you need JRE 11 at least. (If you want to use that application please contact with me)

The application is delevoped with spring boot because I'm planning to transform this application to microservices in future. 
The plans for the future are:
- Using this application for any kind of languages, not just Turkish - english.
- Besides vocabulary, using this application in order to improve grammar skills.
- Exam service for different exam styles and tracing exam result (e.g. displaying exam results in point/time graph and also displaying exam results in table view)
- Creating meaningful sentences by taking advantage of AI for testing grammar.

## Project Details
WordMemoriser is a web application in order to improve english vocabulary. The usage of the application;

### Adding New Learnt Word
- When the user learns a new word, the user stores the new word to the application.
- The new word is stored to the application with:
  - The Turkish Word.
  - The English Word.
  - The Turkish Meaning of the Word.
  - The English Meaning of the Word.
  - The Word Type of The Word (e.g. noun, verb, adverb etc.)
  - An example.
- The figure below represents the AddWord page:
<img src="https://github.com/ksavas/WordMemoriser/blob/master/SS/AddWord.png"><br>


### Exam Yourself
- If the application filled with enough words (5 words at least) the user tests himself/herself for new learnt words.
- The tests are performed in 6 different ways and they can be divided into 2 in parts of languages:
  - TR to EN
    - Word to Word
    - Word to Meaning
    - Meaning to Word
  - EN to TR
    - Word to Word
    - Word to Meaning
    - Meaning to Word
- Every answered word takes +1 or -1 point according to the answer.
  - If the answer is correct the word gets +1 point.
  - If the answer is wrong the word gets -1 point.
- The user can filter the exam by word points:
  - For example, the user might think the words which have more than 20 points are learnt words and the user can say "I want to be examed for words which have most 20 points".
  - Or the user might want to improve the words which the user couldn't learn and the user can say "I want to be examed for the words which have most 0 points" which means the 
    user wants to be examed for the words which the user haven't answered corretly yet.
- The figure below represents the exam page
<img src="https://github.com/ksavas/WordMemoriser/blob/master/SS/Exam.png"><br>
- The figure below represents what happens when the user selects the correct answer
<img src="https://github.com/ksavas/WordMemoriser/blob/master/SS/ExamTrueAnswer.png"><br>
- The figure below represents what happens when the user selects the wrong answer
<img src="https://github.com/ksavas/WordMemoriser/blob/master/SS/ExamFalseAnswer.png"><br>

### Results
- The user can see the words with meanings, examples and points and the user also can delete the words from the Results page.
- The figure below represents the Results page
<img src="https://github.com/ksavas/WordMemoriser/blob/master/SS/Results.png"><br>
