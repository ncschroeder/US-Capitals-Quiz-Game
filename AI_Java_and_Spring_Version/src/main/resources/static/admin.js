(function () {
    const uploadForm = document.getElementById('upload-form');
    const uploadResultEl = document.getElementById('upload-result');

    uploadForm.addEventListener('submit', (event) => {
        event.preventDefault();
        fetch('/states/upload', {
            method: 'POST',
            body: new FormData(uploadForm),
        })
        .then((response) =>
            response.text().then((text) => {
                if (!response.ok) throw new Error(text);
                uploadResultEl.textContent = text;
            })
        )
        .catch((error) => uploadResultEl.textContent = `Error: ${error.message}`);
    });

    const deleteResultEl = document.getElementById('delete-result');
    function wireDelete(buttonId, url) {
        document.getElementById(buttonId).addEventListener('click', () => {
            fetch(url, { method: 'DELETE' })
            .then((response) =>
                response.text().then((text) => {
                    if (!response.ok) throw new Error(text);
                    deleteResultEl.textContent = text;
                })
            )
            .catch((error) => deleteResultEl.textContent = `Error: ${error.message}`);
        });
    }

    wireDelete('delete-capitals-btn', '/admin/scores/capitals');
    wireDelete('delete-states-btn', '/admin/scores/states');

    const tablesContainer = document.getElementById('states-tables');

    function showError(error) {
        const errorEl = document.createElement('div');
        errorEl.append(`Error: ${error.message}`);
        tablesContainer.append(errorEl);
    }

    function makeRow(tagName, values) {
        const row = document.createElement('tr');
        for (const value of values) {
            const cell = document.createElement(tagName);
            cell.append(value);
            row.append(cell);
        }
        return row;
    }

    /**
     * @typedef State
     * @type {{ name: string, capital: string, cities: string[] }}
     */

    function wireStatesTable(buttonId, url) {
        document.getElementById(buttonId).addEventListener('click', () => {
            fetch(url)
            .then(async (response) => {
                if (!response.ok) {
                    throw new Error(await response.text());
                }

                /** @type {State[]} */
                const states = await response.json();
                const table = document.createElement('table');
                table.append(makeRow('th', ['State', 'Capital', 'Cities']));
                for (const { name, capital, cities } of states) {
                    table.append(makeRow('td', [name, capital, cities.join(', ')]));
                }

                const removeBtn = document.createElement('button');
                removeBtn.type = 'button';
                removeBtn.append('Remove table');

                const wrapper = document.createElement('div');
                wrapper.append(table, removeBtn);
                removeBtn.addEventListener('click', () => wrapper.remove());

                tablesContainer.append(wrapper);
            })
            .catch(showError);
        });
    }

    wireStatesTable('states-from-db-btn', '/admin/states-from-database');
    wireStatesTable('states-from-service-btn', '/admin/states-from-state-service-class');

    document.getElementById('markdown-rows-btn').addEventListener('click', () => {
        fetch('/admin/states-from-database')
        .then(async (response) => {
            if (!response.ok) {
                throw new Error(await response.text());
            }

            /** @type {State[]} */
            const states = await response.json();

            const code = document.createElement('code');
            for (const { name, capital, cities } of states) {
                code.append(
                    `| ${name} | ${capital} | ${cities.join(', ')} |`,
                    document.createElement('br')
                );
            }

            const removeBtn = document.createElement('button');
            removeBtn.type = 'button';
            removeBtn.append('Remove code');

            const wrapper = document.createElement('div');
            wrapper.append(code, removeBtn);
            removeBtn.addEventListener('click', () => wrapper.remove());

            tablesContainer.append(wrapper);
        })
        .catch(showError);
    });
})();
