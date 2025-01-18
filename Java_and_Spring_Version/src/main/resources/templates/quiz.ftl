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
    <a href='index.html' class='smallText'>Main Menu</a>
    <h1 id='quizHeading' class='largeText'>${titleAndHeading}</h1>

    <div id='quizContentDiv'>
        <p class='smallText'>Score: <span id='curScoreSpan'>0</span></p>
        <fieldset>
            <legend>
                <span id='questionNumSpan' class='mediumText'></span>
                <br>
                <span id='questionSpan' class='mediumText'></span>
            </legend>
            <div id='choiceBtnAndLabelDivsDiv'></div>
            <button id='choiceSubmitBtn'>Submit</button>
        </fieldset>
    </div>

    <div id='gameOverDiv'>
        <p id='finalScoreP' class='smallText'></p>
        <label for='nameInput' class='smallText'>Name:</label>
        <input type='text' id='nameInput' />
        <button id='saveScoreBtn'>Save Score</button>
        <p id='scoreStatusP' class='smallText'></p>
    </div>

    <script id='questionsJsonScript' type='application/json'>
        ${questionsJson}
    </script>
    <script src='quiz.js'></script>
</body>
</html>