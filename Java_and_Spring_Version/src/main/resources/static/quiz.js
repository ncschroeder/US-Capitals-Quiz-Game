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
    if (i === 5) {
        label.textContent = 'None of the above';
    }

    const btnAndLabelDiv = document.createElement('div');
    btnAndLabelDiv.append(radioBtn, label);
    choiceBtnAndLabelDivs.push(btnAndLabelDiv);
}

getElementById('choice-btn-and-label-divs-div').append(...choiceBtnAndLabelDivs);


/**
 * @typedef CapitalsOfStatesQuestion
 * @type {{ stateName: string, choices: string[], correctIndex: number }}
 * 
 * @typedef StatesOfCapitalsQuestion
 * @type {{ capital: string, choices: string[], correctIndex: number }}
 */

const serverDataJsonScript = getElementById('server-data-json-script');
/**
 * @type {{ 
 *     questions: CapitalsOfStatesQuestion[] | StatesOfCapitalsQuestion[],
 *     maxNameLength: number
 * }}
 */
const { questions, maxNameLength } = JSON.parse(serverDataJsonScript.text);
// Remove script so people can't look at the HTML to see the answers.
serverDataJsonScript.remove();

let questionIndex = 0;
const isCapitalsOfStatesQuiz = questions[0].stateName !== undefined;
const questionNumSpan = getElementById('question-num-span');
const questionSpan = getElementById('question-span');

/**
 * @param {HTMLDivElement} div
 * @returns {?HTMLInputElement}
 */
const getRadioBtnIn = (div) => div.querySelector('input[type="radio"]');

function showQuestion() {
    const question = questions[questionIndex];
    questionNumSpan.textContent = `Question ${questionIndex + 1} of ${questions.length}`;
    questionSpan.textContent =
        isCapitalsOfStatesQuiz
        ? `What's the capital of ${question.stateName}?`
        : `${question.capital} is the capital of what state?`;

    for (let i = 0; i < choiceBtnAndLabelDivs.length; i++) {
        const choiceDiv = choiceBtnAndLabelDivs[i];
        getRadioBtnIn(choiceDiv).checked = false;
        
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
const curScoreSpan = getElementById('cur-score-span');

getElementById('choice-submit-btn').onclick = () => {
    const isLastQuestion = questionIndex === questions.length - 1;
    const indexSelected = choiceBtnAndLabelDivs.findIndex(div => getRadioBtnIn(div).checked);
    
    switch (indexSelected) {
        case -1: return;

        case questions[questionIndex].correctIndex:
            score++;
            if (!isLastQuestion) {
                curScoreSpan.textContent = score;
            }
    }

    if (isLastQuestion) {
        getElementById('top-left-home-link').remove();
        getElementById('final-score-div').textContent = `Your score is ${score} of ${questions.length}.`;
        getElementById('quiz-content-div').remove();
        getElementById('quiz-over-div').style.display = 'flex';
    } else {
        questionIndex++;
        showQuestion();
    }
};

const nameInput = getElementById('name-input');
const saveScoreBtn = getElementById('save-score-btn');
const saveScoreStatusDiv = getElementById('save-score-status-div');

saveScoreBtn.onclick = () => {
    const nameInputText = nameInput.value.trim();
    if (nameInputText.length === 0) {
        saveScoreStatusDiv.textContent = 'You must enter a name.';
        return;
    }
    if (nameInputText.length > maxNameLength) {
        saveScoreStatusDiv.textContent = 'That name is too long.';
        return;
    }
    
    const requestUrl =
        `quizScore?score=${score}&name=${nameInputText}&isForCapitalsOfStatesQuiz=${isCapitalsOfStatesQuiz}`;
    const errorMessage = 'Error saving score.';
        
    fetch(requestUrl, { method: 'POST' })
    .then(async (response) => {
        const responseText = await response.text();
        
        if (response.ok) {
            const dateString =
                new Date(responseText)
                .toLocaleString(undefined, { dateStyle: 'long', timeStyle: 'long' });
            saveScoreStatusDiv.innerHTML =
                `Score successfully saved at: ${dateString}.
                <br />
                This date and time will be shown on the scores page.`;
            
            getElementById('name-input-div').remove();
            saveScoreBtn.remove();
        } else {
            saveScoreStatusDiv.textContent = errorMessage;
            console.error(responseText);
        }
    })
    .catch(reason => {
        saveScoreStatusDiv.textContent = errorMessage;
        console.error(reason);
    });
};
