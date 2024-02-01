
import fetch from 'node-fetch';
import { headers, urlForAlertPolicies } from '../../common/params.mjs'; //

const policyData = {
    policy: {
        incident_preference: 'PER_POLICY', // Options: 'PER_POLICY', 'PER_CONDITION', 'PER_CONDITION_AND_TARGET'
        name: 'Integration_Policy' // Replace with the desired name for the new policy
    }
};

fetch(urlForAlertPolicies, {
    method: 'POST',
    headers: headers,
    body: JSON.stringify(policyData)
})
    .then(response => {
        // Check if the response status code is in the 200 range
        if (!response.ok) {
            // If not, throw an error with the status
            throw new Error('Network response was not ok, status: ' + response.status);
        }
        return response.json(); // Attempt to parse response as JSON
    })
    .then(data => {
        console.log('New Policy Created:', data);
    })
    .catch(error => {
        console.error('Error:', error);
    });