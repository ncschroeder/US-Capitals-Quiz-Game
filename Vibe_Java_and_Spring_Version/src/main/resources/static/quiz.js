(function () {
    const questions = JSON.parse(document.getElementById('questions-data').textContent);
    const questionEl = document.getElementById('question');
    const choicesEl = document.getElementById('choices-form');
    const submitBtn = document.getElementById('submit-btn');
    const scoreEl = document.getElementById('score');
    const resultEl = document.getElementById('result');

    let currentIndex = 0;
    let score = 0;

    function renderQuestion() {
        if (currentIndex >= questions.length) {
            questionEl.textContent = '';
            choicesEl.innerHTML = '';
            submitBtn.style.display = 'none';
            resultEl.textContent = 'quiz over';
            return;
        }

        const question = questions[currentIndex];
        questionEl.textContent = question.prompt;

        choicesEl.innerHTML = '';
        question.choices.forEach((choice, i) => {
            const id = 'choice-' + i;
            const input = document.createElement('input');
            input.type = 'radio';
            input.name = 'choice';
            input.value = choice;
            input.id = id;

            const label = document.createElement('label');
            label.htmlFor = id;
            label.appendChild(document.createTextNode(' ' + choice));

            const wrapper = document.createElement('div');
            wrapper.appendChild(input);
            wrapper.appendChild(label);
            choicesEl.appendChild(wrapper);
        });
    }

    submitBtn.addEventListener('click', () => {
        const selected = choicesEl.querySelector('input[name="choice"]:checked');
        if (!selected) {
            return;
        }
        if (selected.value === questions[currentIndex].correctAnswer) {
            score++;
            scoreEl.textContent = 'Score: ' + score;
        }
        currentIndex++;
        renderQuestion();
    });

    renderQuestion();
})();