# US-Capitals-Quiz-Game

This is a Python program with a command line interface that was made solo. The notable feature about it is that it accesses a database. 
When the program is started, a database is accessed to get info about states and their capitals. The user is then at a menu and they have 
the option to either do a state capitals quiz, study the state capitals, look at the scores other players got from taking quizzes, or exit. 
If the user decides to take a quiz, the program will ask the user 10 questions about what the capital of a randomly selected state is. 
There are then 4 state capitals that the user can answer, 1 of which is correct and the other 3 are random. The user's score starts at 0 
and increases by 1 each time they answer a question correctly. After a game, the user has the option to upload their name and score to a 
database.

## Directions
1. Have a MySQL database management system on your computer, a database named 'state_capitals_quiz', and 2 tables in that database: 
1 named 'state_capitals' and 1 named 'scores'.<br>
SQL commands for creating those:<br>
        create database state_capitals_quiz;
        use state_capitals_quiz;
        create table state_capitals (state varchar(20), capital varchar(20), primary key (state));
        create table scores (name varchar(20), score tinyint);
2. Modify FillDatabseScript.py and StateCapitalsQuiz.py so that the dictionaries named 'config' in each file contain your username and password 
for your MySQL connection.
3. Run FillDatabaseScript.py using Python
4. Run StateCapitalsQuiz.py using Python and have fun!