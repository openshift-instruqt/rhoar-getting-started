'use strict';

const express = require('express');
const path = require('path');
const bodyParser = require('body-parser');
const jsyaml = require('js-yaml');

const app = express();

// Health Check Middleware
const probe = require('kube-probe');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(express.static(path.join(__dirname, 'public')));

let configMap;
let message = "Default hard-coded greeting: Hello, %s!";

app.use('/api/greeting', (request, response) => {
  const name = (request.query && request.query.name) ? request.query.name : 'World';
  return response.send({content: message.replace(/%s/g, name)});
});

// set health check
probe(app);

// TODO: Periodic check for config map update

// TODO: Retrieve ConfigMap

module.exports = app;
