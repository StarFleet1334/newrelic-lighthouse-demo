import fetch from 'node-fetch';
import { promises as fs } from 'fs';
import { headers } from '../../common/params.mjs'; // Adjust the import path as necessary

// Set the policy ID of the policy you want to delete
const condition_id = '5656698'; // Replace with the actual ID of the policy you want to delete

// Construct the URL for the DELETE request
const urlForDeletingPolicy = `https://api.eu.newrelic.com/v2/alerts_conditions/${condition_id}.json`; // Ensure you're using the correct regional endpoint

fetch(urlForDeletingPolicy, {
    method: 'DELETE',
    headers: headers
})
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => {
                throw new Error(`Failed to delete policy, status: ${response.status}, response: ${text}`);
            });
        }
        console.log(`Alert Condition with ID ${condition_id} deleted successfully.`);
        // return fs.readFile('../create/alertConditionsLog.txt', 'utf8');
    })
    // .then(data => {
    //     // Parse each line as JSON and update the entry with the matching condition ID
    //     const lines = data.split('\n').filter(line => line);
    //     const updatedLines = lines.map(line => {
    //         const entry = JSON.parse(line);
    //         // Compare conditionId as a string since IDs from the file are strings
    //         if (entry.id === conditionId) {
    //             entry.deleted_timestamp = new Date().toISOString();
    //         }
    //         return JSON.stringify(entry);
    //     });
    //
    //     // Write the updated entries back to the file
    //     return fs.writeFile('../create/alertConditionsLog.txt', updatedLines.join('\n') + '\n');
    // })
    // .then(() => {
    //     console.log(`Updated deletion timestamp for condition ID ${conditionId} in alertConditionsLog.txt.`);
    // })
    .catch(error => {
        console.error('Error:', error);
    });
