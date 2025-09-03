const dateTimeStringsJson = document.getElementById('dateTimeStringsJsonScript').text;
const dateTimeStrings = JSON.parse(dateTimeStringsJson);
const dateTimeCells = document.getElementsByClassName('dateTimeCell');
const dateTimeFormat = new Intl.DateTimeFormat(undefined, { dateStyle: 'long', timeStyle: 'long' });

for (let i = 0; i < dateTimeStrings.length; i++) {
    const dateTimeString = dateTimeFormat.format(new Date(dateTimeStrings[i]));
    dateTimeCells[i].textContent = dateTimeString;
}