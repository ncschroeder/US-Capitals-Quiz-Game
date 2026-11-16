'use strict';

const scoreEpochMillisecondsJson = document.getElementById('score-epoch-milliseconds-json-script').text;
/** @type {number[]} */
const scoreEpochMilliseconds = JSON.parse(scoreEpochMillisecondsJson);
const dateTimeCells = document.getElementsByClassName('date-time-cell');
const dateTimeFormat = new Intl.DateTimeFormat(undefined, { dateStyle: 'long', timeStyle: 'long' });

for (let i = 0; i < scoreEpochMilliseconds.length; i++) {
    const dateTimeString = dateTimeFormat.format(new Date(scoreEpochMilliseconds[i]));
    dateTimeCells[i].textContent = dateTimeString;
}

document.getElementById('scores-table').style.visibility = 'visible';
