import { url } from "inspector";

describe('open first exercise', () => {
  it('passes', () => {
    cy.visit('http://localhost:4173/')
    cy.get('a.TopBarItemContainer:nth-child(3)').click()
    cy.url().should('eq', 'http://localhost:4173/Exercises');
    cy.get('.vue3-easy-data-table__body > tr:nth-child(1) > td:nth-child(5)').click()
    cy.url().should('eq', 'http://localhost:4173/Exercises/1');
    })
})