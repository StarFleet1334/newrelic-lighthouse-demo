
import fetch from 'node-fetch';
import { headers, urlForAlertPolicies } from '../../common/params.mjs'; //

fetch(urlForAlertPolicies, { method: 'GET', headers: headers })
    .then(response => response.json())
    .then(data => {
        console.log('Alert Policies:', data);
    })
    .catch(error => {
        console.error('Error:', error);
    });