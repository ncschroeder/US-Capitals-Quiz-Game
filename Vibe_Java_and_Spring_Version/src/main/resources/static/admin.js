(function () {
    const uploadForm = document.getElementById('upload-form');
    const uploadResultEl = document.getElementById('upload-result');

    uploadForm.addEventListener('submit', (event) => {
        event.preventDefault();
        fetch('/states/upload', {
            method: 'POST',
            body: new FormData(uploadForm),
        })
            .then((response) => response.text())
            .then((text) => uploadResultEl.textContent = text);
    });

    const deleteResultEl = document.getElementById('delete-result');
    function wireDelete(buttonId, url) {
        document.getElementById(buttonId).addEventListener('click', () => {
            fetch(url, { method: 'DELETE' })
            .then((response) => response.text())
            .then((text) => deleteResultEl.textContent = text);
        });
    }

    wireDelete('delete-capitals-btn', '/admin/scores/capitals');
    wireDelete('delete-states-btn', '/admin/scores/states');
})();
