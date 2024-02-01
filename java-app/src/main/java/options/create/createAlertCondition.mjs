import fetch from 'node-fetch';
import { headers, urlForAlertPolicies } from '../../common/params.mjs'; // Adjust the import path as necessary
import { promises as fs } from 'fs';

const policyNameToFind = 'Integration_Policy'; // Replace with the name of the policy you want to find

let policyId = null;

fetch(urlForAlertPolicies, { method: 'GET', headers: headers })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Failed to retrieve alert policies, status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        const policy = data.policies.find(p => p.name === policyNameToFind);
        if (policy) {
            policyId = policy.id; // Update policyId with the found ID
            console.log(`Policy ID for '${policyNameToFind}': ${policy.id}`);

            const urlForCreatingCondition = `https://api.eu.newrelic.com/v2/alerts_conditions/policies/${policyId}.json`;
            const conditionData = {
                condition: {
                    type: 'apm_app_metric', // This is an example for an APM application metric condition
                    name: 'New_Condition_Alert', // Name for the condition
                    enabled: true, // Set to 'true' to enable the condition
                    entities: ['462731246'], // Replace with your New Relic application ID
                    metric: 'error_percentage', // Metric to be monitored
                    condition_scope: 'application', // Scope of the condition,
                    terms: [ // Thresholds for the condition
                        {
                            duration: '5', // Duration (in minutes) for the condition to be met
                            operator: 'above', // Operator for the threshold
                            priority: 'critical', // Priority of the threshold
                            threshold: '1', // Threshold value
                            time_function: 'all' // Time function for evaluating the threshold
                        }
                    ]
                }
            };

            return fetch(urlForCreatingCondition, {
                method: 'POST',
                headers: headers,
                body: JSON.stringify(conditionData)
            });
        } else {
            throw new Error(`Policy named '${policyNameToFind}' not found.`);
        }
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => {
                throw new Error(`Failed to create condition, status: ${response.status}, response: ${text}`);
            });
        }
        return response.json();
    })
    .then(data => {
        console.log('New Condition Created:', data);

        // Prepare the data to be written to the file
        const conditionInfo = {
            name: data.condition.name,
            id: data.condition.id,
            timestamp: new Date().toISOString()
        };

        // Convert the data to a string and append it to the file
        return fs.appendFile('alertConditionsLog.txt', JSON.stringify(conditionInfo) + '\n');
    })
    .then(() => {
        console.log('Condition information saved to file.');
    })
    .catch(error => {
        console.error('Error:', error);
    });
