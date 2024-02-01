import fetch from 'node-fetch';
import { headers, urlForApplication } from '../../common/params.mjs'; //

fetch(urlForApplication, { method: 'GET', headers: headers })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Failed to retrieve applications, status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        const applicationIds = data.applications.map(app => ({
            id: app.id,
            name: app.name
        }));
        console.log('Application IDs:', applicationIds);
    })
    .catch(error => {
        console.error('Error:', error);
    });
