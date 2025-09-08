<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <title>${titleAndHeading}</title>
    <link rel='stylesheet' href='styles.css'>
    <style>
        fieldset {
            border: none;
        }

        #choiceSubmitBtn {
            margin-top: 15px;
        }
    </style>
</head>
<body>
    <a href='index.html' id='topMainMenuA' class='smallText'>Main Menu</a>
    <h1 class='largeText'>${titleAndHeading}</h1>

    <div id='quizContentDiv'>
        <p class='smallText'>Score: <span id='curScoreSpan'>0</span></p>
        <fieldset>
            <legend class='mediumText'>
                <span id='questionNumSpan'></span>
                <br>
                <span id='questionSpan'></span>
            </legend>
            <div id='choiceBtnAndLabelDivsDiv'></div>
            <button id='choiceSubmitBtn'>Submit</button>
        </fieldset>
    </div>

    <div id='quizOverDiv' style='display: none;'>
        <p id='finalScoreP' class='smallText'></p>
        <div id='nameInputDiv'>
            <label for='nameInput' class='smallText'>Name (1-${maxNameLength} characters):</label>
            <input type='text' id='nameInput' />
        </div>
        <button id='saveScoreBtn'>Save Score</button>
        <p id='saveScoreStatusP' class='smallText'></p>
        <a href='index.html' class='smallText'>Main Menu</a>
        <a href='quizScores?forCapitalsOfStatesQuizzes=${isCapitalsOfStatesQuiz}' class='smallText'>
            See Scores for ${quizType} Quizzes
        </a>
    </div>

    <script id='serverDataJsonScript' type='application/json'>
        { "questions": ${questionsJson}, "maxNameLength": ${maxNameLength} }
    </script>
    <script src='quiz.js'></script>
</body>
</html>