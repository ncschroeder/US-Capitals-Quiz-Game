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
 * @returns {HTMLParagraphElement}
 */
const createP = (textContent) => createElement('p', textContent);


/**
 * @param {string} [textContent]
 * @returns {HTMLButtonElement}
 */
const createBtn = (textContent) => createElement('button', textContent);

getElementById('statesSubmitBtn').onclick = () => {

    /**
     * @type {FileList}
     */
    const files = getElementById('statesFileInput').files;
    if (files.length === 0) return;
    
    const formData = new FormData();
    formData.set('file', files[0]);
    
    const submitStatusP = getElementById('statesSubmitStatusP');

    fetch('/admin/states', { method: 'PUT', body: formData })
    .then(response => response.text())
    .then(text => submitStatusP.textContent = text)
    .catch(reason => {
        submitStatusP.textContent = 'Error updating states. See console.';
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
    const divId = `getStatesFrom${getStatesFromDatabase ? 'Database' : 'RandomnessService'}Div`;
    const div = getElementById(divId);
    const statesOrErrorDiv = div.querySelector('.statesOrErrorDiv');
    
    div.querySelector('.getStatesBtn').onclick = () => {
        const requestEndpoint = 'statesFrom' + (getStatesFromDatabase ? 'Database' : 'RandomnessService');
        
        fetch('/admin/' + requestEndpoint)
        .then(async (response) => {
            let elementsToShow;
            
            if (!response.ok) {
                elementsToShow = [createP(await response.text())];
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
            statesOrErrorDiv.replaceChildren(createP('Error getting states data. See console.'));
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
        `There are ${states.length} states in the ${statesAreFromDatabase ? 'database' : 'randomness service'}`;
    const caption = createElement('caption', captionText);
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

getElementById('getStatesHtmlBtn').onclick = () => {
    const htmlOrErrorDiv = getElementById('statesHtmlOrErrorDiv');

    fetch('admin/statesTableRowsHtml')
    .then(async (response) => {
        let elementsToShow;

        if (!response.ok) {
            elementsToShow = [createP(await response.text())];
        } else {
            const heading = createElement('h2', 'States Data Table Rows HTML');
            
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
        htmlOrErrorDiv.replaceChildren(createP('Error getting HTML. See console.'));
        console.error(reason);
    });
};


/**
 * @param {boolean} deleteCapitalsOfStatesScores 
 */
function addOnclickForDeleteScoresBtn(deleteCapitalsOfStatesScores) {
    const divId =
        `delete${deleteCapitalsOfStatesQuizScores ? 'CapitalsOfStates' : 'StatesOfCapitals'}QuizScoresDiv`;
    const div = getElementById(divId);
    const deleteStatusP = div.querySelector('.deleteStatusP');
    
    div.querySelector('.deleteScoresBtn').onclick = () => {
        fetch(`/admin/scores?forCapitalsOfStatesQuizzes=${deleteCapitalsOfStatesScores}`, { method: 'DELETE' })
        .then(response => response.text())
        .then(text => deleteStatusP.textContent = text)
        .catch(reason => {
            deleteStatusP.textContent = 'Error deleting scores. See console.';
            console.error(reason);
        });
    };
}

addOnclickForDeleteScoresBtn(true);
addOnclickForDeleteScoresBtn(false);
