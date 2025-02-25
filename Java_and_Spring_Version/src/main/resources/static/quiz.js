'use strict';

const
    getElementById = (id) => document.getElementById(id),
    choiceBtnAndLabelDivs = [];

for (let i = 1; i <= 5; i++) {
    const radioBtn = document.createElement('input');
    radioBtn.type = 'radio';
    radioBtn.id = 'choiceRadioBtn' + i;
    radioBtn.name = 'choice';

    const label = document.createElement('label');
    label.htmlFor = radioBtn.id;
    label.className = 'smallText';
    if (i === 5) {
        label.innerText = 'None of the above';
    }

    const btnAndLabelDiv = document.createElement('div');
    btnAndLabelDiv.append(radioBtn, label);
    choiceBtnAndLabelDivs.push(btnAndLabelDiv);
}

getElementById('choiceBtnAndLabelDivsDiv').append(...choiceBtnAndLabelDivs);

let questionIndex = 0;
const
    questions = JSON.parse(getElementById('questionsJsonScript').text),
    isCapitalsOfStatesQuiz = questions[0].state !== undefined,
    questionNumSpan = getElementById('questionNumSpan'),
    questionSpan = getElementById('questionSpan');

function showQuestion() {
    questionNumSpan.innerText = `Question ${questionIndex + 1} of ${questions.length}`;
    const question = questions[questionIndex];
    questionSpan.innerText =
        isCapitalsOfStatesQuiz
        ? `What's the capital of ${question.state}?`
        : `${question.capital} is the capital of what state?`;

    for (let i = 0; i <= 4; i++) {
        const choiceDiv = choiceBtnAndLabelDivs[i];
        choiceDiv.firstChild.checked = false;
        if (i < 4) {
            // The 5th choice div is for "None of the above".
            choiceDiv.lastChild.innerText = question.choices[i];
        }
    }
}

showQuestion();

let score = 0;
const
    show = (element) => element.style.display = 'block',
    hide = (element) => element.style.display = 'none',
    curScoreSpan = getElementById('curScoreSpan'),
    choiceSubmitBtn = getElementById('choiceSubmitBtn'),
    quizContentDiv = getElementById('quizContentDiv'),
    gameOverDiv = getElementById('gameOverDiv'),
    finalScoreP = getElementById('finalScoreP');

hide(gameOverDiv);

choiceSubmitBtn.onclick = () => {
    const indexSelected = choiceBtnAndLabelDivs.findIndex(div => div.firstChild.checked);
    switch (indexSelected) {
        case -1: return;

        case questions[questionIndex].correctIndex:
            curScoreSpan.innerText = ++score;
            break;

        default:
    }

    if (questionIndex === questions.length - 1) {
        hide(quizContentDiv);
        show(gameOverDiv);
        finalScoreP.innerText = `Your score is ${score} of ${questions.length}`;
    } else {
        questionIndex++;
        showQuestion();
    }
}

const
    nameInput = getElementById('nameInput'),
    saveScoreBtn = getElementById('saveScoreBtn'),
    scoreStatusP = getElementById('scoreStatusP');

saveScoreBtn.onclick = () => {
    const nameInputText = nameInput.value.trim();
    if (nameInputText.length === 0) return;

    const
        request = new XMLHttpRequest(),
        url =
            (isCapitalsOfStatesQuiz ? 'capitalsOfStates' : 'statesOfCapitals') +
            `QuizScore?score=${score}&name=${nameInputText}`;

    request.open('post', url);

    request.onload = () => {
        hide(saveScoreBtn);
        scoreStatusP.innerText = 'Score successfully saved';
    }

    request.onerror = () => {
        scoreStatusP.innerText = 'Error';
    }

    request.send();
}