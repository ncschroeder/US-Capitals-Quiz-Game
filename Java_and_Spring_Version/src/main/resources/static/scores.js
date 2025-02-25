const
    dateTimeStringsJson = document.getElementById('dateTimeStringsJsonScript').text,
    dateTimeStrings = JSON.parse(dateTimeStringsJson),
    dateTimeCells = document.getElementsByClassName('dateTimeCell'),
    dateTimeFormat = new Intl.DateTimeFormat(undefined, { dateStyle: 'long', timeStyle: 'long' });

for (let i = 0; i < dateTimeStrings.length; i++) {
    const dateTimeString = dateTimeFormat.format(new Date(dateTimeStrings[i]));
    dateTimeCells[i].append(dateTimeString);
}
