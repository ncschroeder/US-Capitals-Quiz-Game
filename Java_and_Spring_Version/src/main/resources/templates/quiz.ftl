<!DOCTYPE html>
<html lang="en-US">
<head>
    <meta charset="utf-8" />
    <title>${titleAndHeading}</title>
    <link rel="icon" href="us-favicon.png" type="image/x-icon" />
    <link rel="stylesheet" href="common.css" />
    <link rel="stylesheet" href="quiz.css" />
</head>
<body>
    <a href="/" id="top-left-home-link">Home</a>
    <h1>${titleAndHeading}</h1>

    <div id="quiz-content-div">
        <div class="small-text">Score: <span id="cur-score-span">0</span></div>
        <fieldset>
            <legend class="medium-text">
                <span id="question-num-span"></span>
                <br>
                <span id="question-span"></span>
            </legend>
            <div id="choice-btn-and-label-divs-div"></div>
            <button id="choice-submit-btn">Submit</button>
        </fieldset>
    </div>

    <div id="quiz-over-div" style="display: none;">
        <div id="final-score-div" class="small-text"></div>
        
        <div id="save-score-div">
            <div id="name-input-div">
                <label for="name-input">Name (1-${maxNameLength} characters)</label>
                <br />
                <input type="text" id="name-input" />
            </div>
            <button id="save-score-btn">Save Score</button>
            <div id="save-score-status-div" class="small-text"></div>
        </div>
        
        <div id="quiz-over-links-div" class="small-text">
            <a href="/">Home</a>
            <a href="${quizUrl}">${takeAnotherQuizText}</a>
            <a href="${seeScoresUrl}">${seeScoresUrlText}</a>
        </div>
    </div>

    <script id="server-data-json-script" type="application/json">
        { "questionDataArr": ${questionDataArrJson}, "maxNameLength": ${maxNameLength}, "addScoreUrl": "${addScoreUrl}" }
    </script>
    <script src="quiz.js"></script>
</body>
</html>