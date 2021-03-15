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
```sql
create database state_capitals_quiz;
use state_capitals_quiz;
create table state_capitals (state varchar(20), capital varchar(20), primary key (state));
create table scores (name varchar(20), score tinyint);
```
2. Have the MySQL connector for Python installed.
3. Modify FillDatabseScript.py and StateCapitalsQuiz.py so that for the dictionaries named 'config' in each file, the values for the 'user' and 
'password' keys are your username and password, respectively, for your MySQL connection.
4. Run FillDatabaseScript.py using Python
5. Run StateCapitalsQuiz.py using Python and have fun!

## Why I decided to make this
When I first downloaded MySQL, there was a pre-filled sample database called "world" that had tables called "city", "country", and others. In October 2020, I wanted to become more familiar with
databases so I studied them and decided I would do a project that involved them. I think one of my ideas was that I would make a game that accesses the "country" table in the "world" database to get countries and 
their capitals and then quizzes the user about these. However, the "country" table does not store the capitals of countries, though it gives other info about countries. I think I then came up with 
the idea of just using US states and their capitals and making a quiz game.

## Thoughts on this project
I decided that this game would feature quizzes that have 10 questions each and have each question ask what the capital of a randomly selected state is and have there be 4 choices. It would be 
a good idea to make sure that each question got a unique state so I had to come up with a way to ensure that. I had to make it so that the correct answer 
was randomly placed and had a roughly equal chance of being either the 1st, 2nd, 3rd, or 4th choice. I had to make it so that the other choices were random capitals and that each choice was 
unique. All this thinking was enjoyable for me.