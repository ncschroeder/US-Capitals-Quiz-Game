'use strict';

/**
 * @param {string} id 
 * @returns {?HTMLElement}
 */
const getElementById = (id) => document.getElementById(id);

/**
 * @param {string} elementType 
 * @param {string} [textContent] 
 * @returns {HTMLElement}
 */
function createElement(elementType, textContent) {
    const element = document.createElement(elementType);
    if (textContent) {
        element.textContent = textContent;
    }
    return element;
}

/**
 * @param {string} [textContent] 
 * @returns {HTMLDivElement}
 */
const createDiv = (textContent) => createElement('div', textContent);

/**
 * @param {string} [textContent]
 * @returns {HTMLButtonElement}
 */
const createBtn = (textContent) => createElement('button', textContent);


getElementById('states-submit-btn').onclick = () => {
    /**
     * @type {FileList}
     */
    const files = getElementById('states-file-input').files;
    if (files.length === 0) return;
    
    const formData = new FormData();
    formData.set('file', files[0]);
    
    const submitStatusDiv = getElementById('states-submit-status-div');

    fetch('/admin/states', { method: 'PUT', body: formData })
    .then(response => response.text())
    .then(text => submitStatusDiv.textContent = text)
    .catch(reason => {
        submitStatusDiv.textContent = 'Error updating states. See console.';
        console.error(reason);
    });
};


/**
 * @typedef State
 * @type {{ name: string, capital: string, cities: string[] }}
 */

/**
 * @param {boolean} getStatesFromDatabase
 */
function addOnclickForGetStatesBtn(getStatesFromDatabase) {
    const divId = `get-states-from-${getStatesFromDatabase ? 'database' : 'quiz-creation-class'}-div`;
    const div = getElementById(divId);
    const statesOrErrorDiv = div.querySelector('.states-or-error-div');
    
    div.querySelector('.get-states-btn').onclick = () => {
        const endpointEnd = 'states-from-' + (getStatesFromDatabase ? 'database' : 'quiz-creation-class');
        
        fetch('/admin/' + endpointEnd)
        .then(async (response) => {
            let elementsToShow;
            
            if (!response.ok) {
                elementsToShow = [createDiv(await response.text())];
            } else {
                /**
                 * @type {State[]}
                 */
                const states = await response.json();
                const table = createStatesTable(states, getStatesFromDatabase);
                const removeBtn = createBtn('Remove Table');
                removeBtn.onclick = () => statesOrErrorDiv.innerHTML = '';
                elementsToShow = [table, removeBtn];
            }
            
            statesOrErrorDiv.replaceChildren(...elementsToShow)
        })
        .catch(reason => {
            statesOrErrorDiv.replaceChildren(createDiv('Error getting states data. See console.'));
            console.error(reason);
        });
    };
}

/**
 * @param {State[]} states
 * @param {boolean} statesAreFromDatabase 
 * @returns {HTMLTableElement}
 */
function createStatesTable(states, statesAreFromDatabase) {
    const table = createElement('table');
    
    const captionText =
        `There are ${states.length} states in the ${statesAreFromDatabase ? 'database' : 'QuizCreation class'}`;
    const caption = createElement('caption', captionText);
    caption.className = 'medium-text';
    table.appendChild(caption);
    
    const tHead = createElement('thead');
    let tr = createElement('tr');

    for (const heading of ['State', 'Capital', 'Cities']) {
        tr.appendChild(createElement('th', heading));
    }

    tHead.appendChild(tr);
    table.appendChild(tHead);
    const tBody = createElement('tbody');

    for (const state of states) {
        tr = createElement('tr');
        for (const text of [state.name, state.capital, state.cities.join(', ')]) {
            tr.appendChild(createElement('td', text));
        }
        tBody.appendChild(tr);
    }

    table.appendChild(tBody);
    return table;
}

addOnclickForGetStatesBtn(true);
addOnclickForGetStatesBtn(false);

getElementById('get-states-html-btn').onclick = () => {
    const htmlOrErrorDiv = getElementById('states-html-or-error-div');

    fetch('admin/states-table-rows-html')
    .then(async (response) => {
        let elementsToShow;

        if (!response.ok) {
            elementsToShow = [createDiv(await response.text())];
        } else {
            const heading = createElement('h2', 'States Data Table Rows HTML');
            heading.className = 'medium-text';
            
            const pre = createElement('pre');
            for (const codeLine of await response.json()) {
                pre.appendChild(createElement('code', codeLine));
            }
            
            const removeBtn = createBtn('Remove HTML');
            removeBtn.onclick = () => htmlOrErrorDiv.innerHTML = '';
            
            elementsToShow = [heading, pre, removeBtn];
        }
        
        htmlOrErrorDiv.replaceChildren(...elementsToShow);
    })
    .catch(reason => {
        htmlOrErrorDiv.replaceChildren(createDiv('Error getting HTML. See console.'));
        console.error(reason);
    });
};


/**
 * @param {string} quizTypeWithHyphens
 */
function addOnclickForDeleteScoresBtn(quizTypeWithHyphens) {
    const divId = `delete-${quizTypeWithHyphens}-scores-div`;
    const div = getElementById(divId);
    const deleteStatusDiv = div.querySelector('.delete-status-div');
    
    div.querySelector('.delete-scores-btn').onclick = () => {
        fetch(`/admin/${quizTypeWithHyphens}-quiz-scores`, { method: 'DELETE' })
        .then(response => response.text())
        .then(text => deleteStatusDiv.textContent = text)
        .catch(reason => {
            deleteStatusDiv.textContent = 'Error deleting scores. See console.';
            console.error(reason);
        });
    };
}

addOnclickForDeleteScoresBtn('capitals-of-states');
addOnclickForDeleteScoresBtn('states-of-capitals');

