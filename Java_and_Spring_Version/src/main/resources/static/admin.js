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
 * @returns {HTMLHeadingElement}
 */
const createH2 = (textContent) => createElement('h2', textContent);


getElementById('statesDataSubmitBtn').onclick = () => {
    /**
     * @type {FileList}
     */
    const files = getElementById('fileInput').files;
    if (files.length === 0) return;

    const formData = new FormData();
    formData.set('file', files[0]);
    formData.set('updateRandomnessService', getElementById('updateRsCheckbox').checked);

    const
        submitStatusP = getElementById('statesDataSubmitStatusP'),
        errorMessage = 'Error with updating states data.';

    fetch('/admin/statesData', { method: 'PUT', body: formData })
    .then(response =>
        submitStatusP.textContent =
            response.ok ? 'States data successfully updated.' : errorMessage
    )
    .catch(reason => {
        submitStatusP.textContent = errorMessage;
        console.error(errorMessage, reason);
    });
}

/**
 * @typedef State
 * @type {object}
 * @property {string} name
 * @property {string} capital
 * @property {string[]} cities
 */

getElementById('getStatesDataBtn').onclick = () => {
    const 
        statesDataOrErrorDiv = getElementById('statesDataOrErrorDiv'),
        errorP = createP('Error with getting states data.');

    fetch('admin/statesData')
    .then(async (response) => {
        const elementsToShow = [];

        if (!response.ok) {
            elementsToShow.push(errorP);
            return elementsToShow;
        }
        
        /**
         * @type {{statesInDatabase: State[], statesInRandomnessService: State[] | undefined}}
         */
        const { statesInDatabase, statesInRandomnessService } = await response.json();
        const databaseDataHeading = createH2('States Data in Database');
        elementsToShow.push(databaseDataHeading, createStatesDataTable(statesInDatabase));

        if (statesInRandomnessService) {
            const rsDataHeading = createH2('States Data in Randomness Service');
            elementsToShow.push(rsDataHeading, createStatesDataTable(statesInRandomnessService));
        } else {
            const pText = 'The randomness service has the same states data as the database.';
            elementsToShow.push(createP(pText));
        }

        return elementsToShow;
    })
    .then(elementsToShow => statesDataOrErrorDiv.replaceChildren(...elementsToShow))
    .catch(reason => {
        statesDataOrErrorDiv.replaceChildren(errorP);
        console.error(errorP.textContent, reason);
    });
}

/**
 * @param {State[]} states
 * @returns {HTMLTableElement}
 */
function createStatesDataTable(states) {
    const
        table = createElement('table'),
        tHead = createElement('thead');
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

getElementById('getStatesDataHtmlBtn').onclick = () => {
    const
        htmlOrErrorDiv = getElementById('statesDataHtmlOrErrorDiv'),
        errorP = createP('Error with getting states data table rows HTML.');

    fetch('admin/statesDataTableRowsHtml')
    .then(async (response) => {
        const elementsToShow = [];

        if (!response.ok) {
            elementsToShow.push(errorP);
            return elementsToShow;
        }

        const pre = createElement('pre');
        for (const codeLine of await response.json()) {
            pre.appendChild(createElement('code', codeLine));
        }
        const heading = createH2('States Data Table Rows HTML');
        elementsToShow.push(heading, pre);

        return elementsToShow;
    })
    .then(elementsToShow => htmlOrErrorDiv.replaceChildren(...elementsToShow))
    .catch(reason => {
        htmlOrErrorDiv.replaceChildren(errorP);
        console.error(errorP.textContent, reason);
    });
}