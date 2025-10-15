const dateTimeStringsJson = document.getElementById('date-time-strings-json-script').text;
const dateTimeStrings = JSON.parse(dateTimeStringsJson);
const dateTimeCells = document.getElementsByClassName('date-time-cell');
const dateTimeFormat = new Intl.DateTimeFormat(undefined, { dateStyle: 'long', timeStyle: 'long' });

for (let i = 0; i < dateTimeStrings.length; i++) {
    const dateTimeString = dateTimeFormat.format(new Date(dateTimeStrings[i]));
    dateTimeCells[i].textContent = dateTimeString;
}

document.getElementById('scores-table').style.visibility = 'visible';