# United States Capitals Quiz Game

## Questions

Each question is multiple choice and there are 5 choices, the last of which is "None of the above." For both types of questions, a random whole number in the range of 0-4, inclusive, gets generated. Each number in this range has an approximately equal chance of getting generated. If the random number is 0, then the correct answer to this question is "None of the above." This means that there's approximately a 1 in 5 chance of the correct answer being "None of the above."

### Capitals of States

The question is "What's the capital of _?" 10 random states are generated. These are the states that'll fill in that blank in the question. Whether or not the correct answer is "None of the above," the choices will consists of a random non-capital city from the generated state, a random non-capital city from a different state, and a capital from a a different random state. More info about non-capital cities can be found in the ["Non-Capital Cities" section below](#non-capital-cities). If the correct answer is NOT "None of the above," then the other choice will be the capital of the generated state and this will, of course, be the correct answer. If the correct answer is "None of the above," then the other choice will be a random city from the generated state that's different from the first random city from the generated state.

### States of Capitals

The question is "_ is the capital of what state?" 10 random capitals are generated. These are the capitals that'll fill in that blank in the question. If the correct answer is NOT "None of the above," then 3 random states will be generated and the choices will consist of those states and the state whose capital is the generated capital, or the correct answer. These choices will be in a random order. If the correct answer is "None of the above," then 4 random states will be generated. None of these will be the state whose capital is the generated capital. These states will be the choices and will be in a random order.

## Cities

### Non-Capital Cities

There's a collection of non-capital cities that'll be used as choices for capitals of states quiz questions. This collection contains 5 cities per state for a total of 50 × 5 = 250 cities. The cities from each state vary in size and one of them has a funny or silly name. Some examples are Parachute, Colorado; Waterproof, Louisiana; Dummer, New Hampshire; Okay, Oklahoma; and Jot-Em-Down, Texas.

There are 2 duplicate cities:
- Columbia, the capital of South Carolina and a populous city in central Missouri.
- Portland, the most populous city, but not the capital, in Maine and Oregon.

### All Cities

Below are all the cities. There are 2 links to music videos on YouTube.

<table>
    <thead>
        <tr>
            <th>State</th>
            <th>Capital</th>
            <th>Non-Capital Cities</th>
        </tr>
    </thead>
    <tbody>
    </tbody>
</table>

## Database Tables

### `state` and `city`

#### `state` Columns
- `id` (INT)
- `name` (VARCHAR(30))
- `capital` (VARCHAR(30))

#### `city` Columns
- `id` (INT)
- `stateId` (INT)
- `name` (VARCHAR(30))

### `scoreData`

#### Columns
- `id` (INT)
- `score` (INT)
- `name` (VARCHAR(20))
- `isForCapitalsOfStatesQuiz` (BIT)
- `dateTime`

## Admin Page

The link for the admin page is [/admin](/admin) or [/admin.html](/admin.html). It's username and password protected so if you try going there, you'll get this page:

![Admin Login]()

If you try making an HTTP request to a valid endpoint that starts with /admin, you'll be sent the HTML for the page shown above.

After logging in, this is the admin page:

![Admin Page]()

### Updating States and Cities

The states and cities used for quizzes are updated via the admin page. A file containing state and city names separated by commas must be submitted. Each line in the file should contain values for a state name, that state's capital, and non-capital cities in that state. The [file I used, states_and_cities.txt]() is in the Java_and_Spring_Version folder and here are the first 5 lines of it:

```
Alaska,Juneau,Anchorage,North Pole,Fairbanks,Sitka,Dillingham
Hawaii,Honolulu,Hilo,Volcano,Kailua,Princeville,Lahaina
California,Sacramento,Rough and Ready,Los Angeles,Bakersfield,Redding,San Bernardino
Nevada,Carson City,Jackpot,Las Vegas,Reno,Henderson,Rachel
Oregon,Salem,Boring,Portland,Bend,Corvallis,Medford
```

There are some rules that must be followed for states and cities to get updated:

- The max file size is 10 KB.
- The 1<sup>st</sup> line must contain at least 3 values.
- All the other lines must contain the same amount of values as the 1<sup>st</sup> line.
- There must be no blank values, duplicate state names, nor duplicate non-capital cities for the same state.

State names and cities will have leading and trailing whitespace removed and internal whitespace will be replaced by a single space. Rows will be added to the `state` and `city` tables in the database.

### Getting States Data

### Deleting Scores

## Project History

I think I downloaded MySQL in late 2019, when I was in my last year at Missouri State University. In MySQL, there was a pre-filled sample database called "world" that had tables called "city," "country," and others. In October 2020, I wanted to become more familiar with databases so I studied them and decided I would do a project that involved them. I think one of my ideas was that I would make a game that accesses the "country" table in the "world" database to get countries and their capitals and then quiz the user about these. However, the "country" table doesn't store the capitals of countries, though it gives other info about countries. I think I then came up with the idea of just using U.S. states and their capitals and making a quiz game.

I first made the Python command line version. At some point after this, I came up with the idea of making a website version, though didn't do this until years later, in December 2024. I thought that Spring would be a good tool to learn, since Java and Kotlin were and, at the time of this writing, still are the languages I have the most experience with. I decided to make a website version of this game and use Spring on the back end.