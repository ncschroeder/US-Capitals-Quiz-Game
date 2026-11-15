(function () {
    const submittedAts = JSON.parse(document.getElementById('submitted-ats-data').textContent);
    const formatter = new Intl.DateTimeFormat(undefined, { dateStyle: 'long', timeStyle: 'long' });
    document.querySelectorAll('.submitted-at').forEach((cell, i) => {
        cell.textContent = formatter.format(new Date(submittedAts[i]));
    });
})();