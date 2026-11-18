'use strict';

const scoreEpochMillisecondsJson = document.getElementById('score-epoch-milliseconds-json-script').text;
/** @type {number[]} */
const scoreEpochMilliseconds = JSON.parse(scoreEpochMillisecondsJson);
const dateTimeCells = document.getElementsByClassName('date-time-cell');
const dateTimeFormat = new Intl.DateTimeFormat(undefined, { dateStyle: 'long', timeStyle: 'long' });

scoreEpochMilliseconds.forEach((millisecond, i) =>
    dateTimeCells[i].textContent = dateTimeFormat.format(new Date(millisecond))
);

document.getElementById('scores-table').style.visibility = 'visible';
