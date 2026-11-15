(function () {
    const getElementById = (id) => document.getElementById(id);

    /** @type {{prompt: string, choices: string[], correctAnswer: string}[]} */
    const questions = JSON.parse(getElementById('questions-data').textContent);
    const homeLinkEl = getElementById('home-link');
    const scoreEl = getElementById('score');
    const questionNumberEl = getElementById('question-number');
    const questionEl = getElementById('question');
    const choicesEl = getElementById('choices-form');
    const submitBtn = getElementById('submit-btn');

    /** @type {string} */
    const quizType = JSON.parse(getElementById('quiz-type').textContent);
    const postQuizEl = getElementById('post-quiz');
    const resultEl = getElementById('result');
    const submitScoreForm = getElementById('submit-score-form');
    const nameInput = getElementById('name-input');
    const submitScoreBtn = submitScoreForm.querySelector('button[type="submit"]');
    const submitStatusEl = getElementById('submit-status');

    let questionIndex = 0;
    let score = 0;

    function renderQuestion() {
        if (questionIndex >= questions.length) {
            submitBtn.style.display = 'none';
            homeLinkEl.style.display = 'none';
            scoreEl.style.display = 'none';
            questionNumberEl.style.display = 'none';
            questionEl.style.display = 'none';
            choicesEl.style.display = 'none';
            resultEl.textContent = `Your score is ${score} of ${questions.length}`;
            postQuizEl.style.display = '';
            return;
        }

        const question = questions[questionIndex];
        questionNumberEl.textContent = `Question ${questionIndex + 1} of ${questions.length}`;
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
            label.append(choice);

            const wrapper = document.createElement('div');
            wrapper.addEventListener('click', () => input.checked = true);
            wrapper.append(input, label);
            choicesEl.append(wrapper);
        });
    }

    submitBtn.addEventListener('click', () => {
        const selected = choicesEl.querySelector('input[name="choice"]:checked');
        if (!selected) return;
        if (selected.value === questions[questionIndex++].correctAnswer) {
            scoreEl.textContent = 'Score: ' + ++score;
        }
        renderQuestion();
    });

    submitScoreForm.addEventListener('submit', (event) => {
        event.preventDefault();
        const name = nameInput.value.trim();
        // Check if only whitespace was entered.
        if (!name) return;

        // Prevent a second click from saving the score twice while the request is in flight.
        submitScoreBtn.disabled = true;

        fetch('/scores', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ name, score, quizType }),
        })
        .then((response) => {
            if (!response.ok) throw new Error(`HTTP ${response.status}`);
            return response.json();
        })
        .then((body) => {
            submitScoreForm.style.display = 'none';
            const dateString =
                new Date(body.submittedAt)
                .toLocaleString(undefined, { dateStyle: 'long', timeStyle: 'long' });
            submitStatusEl.textContent = 'Score submitted at ' + dateString;
        })
        .catch(() => {
            submitStatusEl.textContent = 'Failed to submit score.';
            submitScoreBtn.disabled = false;
        });
    });

    renderQuestion();
})();