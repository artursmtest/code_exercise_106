# Code Exercise 106 Documentation

## Overview

- The App is designed to analyze the organizational structure of 'BIG COMPANY'(Company with the big amount of employees).
- The goal is to improve these kind of things: Salaries(Who earns more/less than needed) and Reporting lines.

## Main goal in details

- Ensure every manager earns at least 20% more than the average salary of its direct subordinates, but no more than 50%
  more.
- Identify employees with reporting lines longer than 4 managers between them and the CEO.
- Create the report with this data.

## Implementation Overview

- The program reads data from a CSV file containing information about all the employees. Each line represents an
employee, including the CEO, and the CEO has no specified manager.
- The file structure is as follows: id,firstName,lastName,salary,managerId(The CEO has no specified manager)
- The App prints output to the console as a report:
  Managers earning less than the average salary of subordinates and by how much.
  Managers earning more than the average salary of subordinates and by how much.
  Employees with reporting lines longer than 4 managers between them and the CEO, and by how much.

## Implementation Details

- Developed using Java SE and Junit for tests.
- Maven is used for project structure and build.

## Repository

- Here is the link to the code repository: 
