import { url } from "inspector";

describe('open first exercise', () => {
  it('passes', () => {
    cy.visit('http://localhost:4173/')
    cy.get('#Excersices_button > b-nav-text:nth-child(1)').click()
    cy.url().should('eq', 'http://localhost:4173/Exercises');
    cy.get('html > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(3) > tr:nth-child(1) > td:nth-child(5) > button:nth-child(1) > svg:nth-child(1)').click()
    cy.url().should('eq', 'http://localhost:4173/Exercises/1');
    })
})