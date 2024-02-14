import { url } from "inspector";

describe('open first exercise', () => {
  it('passes', () => {
    cy.visit('http://localhost:4173/')
    cy.get('#Excersices_button > b-nav-text:nth-child(1)').click()
    cy.url().should('eq', 'http://localhost:4173/Exercises');
    cy.get('tr.excercise-table-row:nth-child(1) > th:nth-child(1)').click()
    cy.url().should('eq', 'http://localhost:4173/Exercises/1');
    })
})