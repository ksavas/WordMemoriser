# WordMemoriser

[Türkçe](https://github.com/ksavas/IddaAnalyser/edit/master/README.md)

WordMemoriser is a web application which is being developed in order to improve english vocabulary. This appllication is still being developed, but the application 
provides the core needs at the moment, so it can be used.

The server side is being developed with jdk 14.0.1 and Spring 2.3.1.RELEASE for developing, Apache Maven 3.6.3 for deploying and Mysql 8.0.20 for database.
If you want to use that application you need JRE 14.0.1 at least. (If you want to use that application please contact with me)

The UI (Web App) part is developed with javascript and jquery 3.5.1.

The application is delevoped with spring boot because I'm planning to transform this application to microservices in future. 
The plans for the future are:
- Using this application for any kind of languages, not just Turkish - english.
- Besides vocabulary, using this application in order to improve grammar skills.
- Exam service for different exam styles and tracing exam result (e.g. displaying exam results in point/time graph and also displaying exam results in table view)
- Creating meaningful sentences by taking advantage of AI for testing grammar.
- Creating account system for the users.
- Putting all these things above into microservice architecture.

## Description
WordMemoriser is a web application in order to improve english vocabulary. The usage of the application;

### Adding New Learnt Word
- When the user learns a new word, the user stores the new word to the application.
- The new word is stored to the application with:
  - The Turkish Word.
  - The English Word.
  - The Turkish Meaning of the Word.
  - The English Meaning of the Word.
  - The Word Type of The Word (e.g. noun, verb, adverb etc.)
- The figure below shows the AddWord page:
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
- The figure below shows the exam page
<img src="https://github.com/ksavas/WordMemoriser/blob/master/SS/Exam.png"><br>
- The figure below shows what happens when the user selects the correct answer
<img src="https://github.com/ksavas/WordMemoriser/blob/master/SS/ExamTrueAnswer.png"><br>
- The figure below shows what happens when the user selects the wrong answer
<img src="https://github.com/ksavas/WordMemoriser/blob/master/SS/ExamFalseAnswer.png"><br>

### Results
- The user can see the words with meanings, examples and points and the user also can delete the words from the Results page.
- The figure below shows the Results page
<img src="https://github.com/ksavas/WordMemoriser/blob/master/SS/Results.png"><br>
