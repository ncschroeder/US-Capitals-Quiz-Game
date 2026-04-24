<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${heading}</title>
</head>
<body>
    <h1>${heading}</h1>
    <div id="score">Score: 0</div>
    <div id="question"></div>
    <form id="choices-form"></form>
    <button id="submit-btn" type="button">Submit</button>
    <div id="result"></div>

    <script id="questions-data" type="application/json">${questionsJson?no_esc}</script>
    <script src="/quiz.js"></script>
</body>
</html>