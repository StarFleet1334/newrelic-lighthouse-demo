import fetch from 'node-fetch';
import { headers } from '../../common/params.mjs'; // Adjust the import path as necessary

/**
 *
 *
 *  DOes not work, I think problem with API
 *
 *
 * @type {string}
 */

const conditionId = '5656237'; // Replace with your actual condition ID
const urlForAlertCondition = `https://api.eu.newrelic.com/v2/alerts_conditions/${conditionId}.json`;

fetch(urlForAlertCondition, { method: 'GET', headers: headers })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Failed to retrieve alert condition, status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        console.log('Alert Condition:', data);
    })
    .catch(error => {
        console.error('Error:', error);
    });


