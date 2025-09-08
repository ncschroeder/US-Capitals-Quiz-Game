'use strict';

/**
 * @param {string} id 
 * @returns {?HTMLElement}
 */
const getElementById = (id) => document.getElementById(id);
    
/**
 * @type {HTMLDivElement[]}
 */
const choiceBtnAndLabelDivs = [];

for (let i = 1; i <= 5; i++) {
    const radioBtn = document.createElement('input');
    radioBtn.type = 'radio';
    radioBtn.id = 'choiceRadioBtn' + i;
    radioBtn.name = 'choice';

    const label = document.createElement('label');
    label.htmlFor = radioBtn.id;
    label.className = 'smallText';
    if (i === 5) {
        label.textContent = 'None of the above';
    }

    const btnAndLabelDiv = document.createElement('div');
    btnAndLabelDiv.append(radioBtn, label);
    choiceBtnAndLabelDivs.push(btnAndLabelDiv);
}

getElementById('choiceBtnAndLabelDivsDiv').append(...choiceBtnAndLabelDivs);


/**
 * @typedef CapitalsOfStatesQuestion
 * @type {{ stateName: string, choices: string[], correctIndex: number }}
 * 
 * @typedef StatesOfCapitalsQuestion
 * @type {{ capital: string, choices: string[], correctIndex: number }}
 */

/**
 * @type {{ 
 *     questions: CapitalsOfStatesQuestion[] | StatesOfCapitalsQuestion[],
 *     maxNameLength: number
 * }}
 */
const { questions, maxNameLength } = JSON.parse(getElementById('serverDataJsonScript').text);
let questionIndex = 0;
const isCapitalsOfStatesQuiz = questions[0].stateName !== undefined;
const questionNumSpan = getElementById('questionNumSpan');
const questionSpan = getElementById('questionSpan');

function showQuestion() {
    const question = questions[questionIndex];
    questionNumSpan.textContent = `Question ${questionIndex + 1} of ${questions.length}`;
    questionSpan.textContent =
        isCapitalsOfStatesQuiz
        ? `What's the capital of ${question.stateName}?`
        : `${question.capital} is the capital of what state?`;

    for (let i = 0; i < choiceBtnAndLabelDivs.length; i++) {
        const choiceDiv = choiceBtnAndLabelDivs[i];
        choiceDiv.querySelector('input[type="radio"]').checked = false;
        
        /*
        The last choice div is for the "None of the above" choice.
        That's the only one that doesn't need its label updated.
        */
        if (i < choiceBtnAndLabelDivs.length - 1) {
            choiceDiv.querySelector('label').textContent = question.choices[i];
        }
    }
}

showQuestion();

let score = 0;
const curScoreSpan = getElementById('curScoreSpan');

getElementById('choiceSubmitBtn').onclick = () => {
    const isLastQuestion = questionIndex === questions.length - 1;
    const indexSelected = choiceBtnAndLabelDivs.findIndex(div => div.firstChild.checked);
    
    switch (indexSelected) {
        case -1: return;

        case questions[questionIndex].correctIndex:
            score++;
            if (!isLastQuestion) {
                curScoreSpan.textContent = score;
            }
    }

    if (isLastQuestion) {
        getElementById('finalScoreP').textContent = `Your score is ${score} of ${questions.length}.`;
        getElementById('topMainMenuA').style.visibility = 'hidden';
        getElementById('quizContentDiv').remove();
        getElementById('quizOverDiv').style.display = 'block';
    } else {
        questionIndex++;
        showQuestion();
    }
};

const nameInput = getElementById('nameInput');
const saveScoreBtn = getElementById('saveScoreBtn');
const saveScoreStatusP = getElementById('saveScoreStatusP');

saveScoreBtn.onclick = () => {
    const nameInputText = nameInput.value.trim();
    if (nameInputText.length === 0) {
        saveScoreStatusP.textContent = 'Must enter a name.';
        return;
    }
    if (nameInputText.length > maxNameLength) {
        saveScoreStatusP.textContent = 'Name is too long.';
        return;
    }

    const requestUrl =
        `quizScore?score=${score}&name=${nameInputText}&isForCapitalsOfStatesQuiz=${isCapitalsOfStatesQuiz}`;
    const errorMessage = 'Error saving score.';
        
    fetch(requestUrl, { method: 'POST' })
    .then(async (response) => {
        const responseText = await response.text();
        let messageToShow;
        
        if (response.ok) {
            const dateString =
                new Date(responseText)
                .toLocaleString(undefined, { dateStyle: 'long', timeStyle: 'long' });
            messageToShow = `Score successfully saved at: ${dateString}.`;
            
            getElementById('nameInputDiv').remove();
            saveScoreBtn.remove();
        } else {
            messageToShow = errorMessage;
            console.error(responseText);
        }

        saveScoreStatusP.textContent = messageToShow;
    })
    .catch(reason => {
        saveScoreStatusP.textContent = errorMessage;
        console.error(reason);
    });
};
