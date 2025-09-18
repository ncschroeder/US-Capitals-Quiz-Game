<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <title>${titleAndHeading}</title>
    <link rel='stylesheet' href='common.css' />
    <link rel='stylesheet' href='quiz.css' />
</head>
<body>
    <a href='/' id='topMainMenuLink'>Main Menu</a>
    <h1>${titleAndHeading}</h1>

    <div id='quizContentDiv'>
        <p>Score: <span id='curScoreSpan'>0</span></p>
        <fieldset>
            <legend id='questionLegend'>
                <span id='questionNumSpan'></span>
                <br>
                <span id='questionSpan'></span>
            </legend>
            <div id='choiceBtnAndLabelDivsDiv'></div>
            <button id='choiceSubmitBtn'>Submit</button>
        </fieldset>
    </div>

    <div id='quizOverDiv' style='display: none;'>
        <p id='finalScoreP'></p>
        
        <div id='saveScoreDiv'>
            <div id='nameInputDiv'>
                <label for='nameInput'>Name (1-${maxNameLength} characters)</label>
                <br />
                <input type='text' id='nameInput' />
            </div>
            <button id='saveScoreBtn'>Save Score</button>
            <p id='saveScoreStatusP'></p>
        </div>
        
        <div id='quizOverLinksDiv'>
            <a href='/'>Main Menu</a>
            <a href='quizScores?forCapitalsOfStatesQuizzes=${isCapitalsOfStatesQuiz}'>
                See Scores for ${quizType} Quizzes
            </a>
        </div>
    </div>

    <script id='serverDataJsonScript' type='application/json'>
        { "questions": ${questionsJson}, "maxNameLength": ${maxNameLength} }
    </script>
    <script src='quiz.js'></script>
</body>
</html>