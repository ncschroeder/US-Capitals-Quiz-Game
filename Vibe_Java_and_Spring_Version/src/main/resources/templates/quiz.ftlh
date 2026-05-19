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

    <div id="post-quiz" style="display: none;">
        <div id="result"></div>
        <form id="submit-score-form">
            <label for="name-input">Enter your name (${minNameLength} to ${maxNameLength} characters):</label>
            <input type="text" id="name-input" required maxlength="${maxNameLength}">
            <button type="submit">Submit score</button>
        </form>
        <div id="submit-status"></div>
        <div><a href="${scoresEndpoint}">See scores for ${quizTypeLabel} quizzes</a></div>
    </div>

    <script id="questions-data" type="application/json">${questionsJson?no_esc}</script>
    <script id="quiz-type" type="application/json">"${quizType}"</script>
    <script src="/quiz.js"></script>
</body>
</html>