(function () {
    const questions = JSON.parse(document.getElementById('questions-data').textContent);
    const quizType = JSON.parse(document.getElementById('quiz-type').textContent);
    const questionEl = document.getElementById('question');
    const choicesEl = document.getElementById('choices-form');
    const submitBtn = document.getElementById('submit-btn');
    const scoreEl = document.getElementById('score');
    const resultEl = document.getElementById('result');
    const submitScoreForm = document.getElementById('submit-score-form');
    const nameInput = document.getElementById('name-input');
    const submitStatusEl = document.getElementById('submit-status');

    let currentIndex = 0;
    let score = 0;

    function renderQuestion() {
        if (currentIndex >= questions.length) {
            questionEl.textContent = '';
            choicesEl.innerHTML = '';
            submitBtn.style.display = 'none';
            resultEl.textContent = 'quiz over';
            submitScoreForm.style.display = '';
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

    submitScoreForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const name = nameInput.value.trim();

        const response = await fetch('/scores', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ name, score, quizType }),
        });

        if (response.ok) {
            const body = await response.json();
            submitScoreForm.style.display = 'none';
            const dateString =
                new Date(body.submittedAt)
                .toLocaleString(undefined, { dateStyle: 'long', timeStyle: 'long' });
            submitStatusEl.textContent = 'Score submitted at ' + dateString;
        } else {
            submitStatusEl.textContent = 'Failed to submit score.';
        }
    });

    renderQuestion();
})();