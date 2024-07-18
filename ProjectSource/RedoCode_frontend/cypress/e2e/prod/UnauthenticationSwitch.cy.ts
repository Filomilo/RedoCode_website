describe('spec', () => {
  it('passes', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      return false
  })
  const email="mailmail@test.com";
  const password="Password+789";

    cy.visit('/')
    cy.visit('/Account')
    cy.url().should('eq', Cypress.config().baseUrl+'/Home');
    cy.visit('/Create')
    cy.url().should('eq', Cypress.config().baseUrl+'/Home');
    cy.visit('/Playground')
    cy.url().should('eq', Cypress.config().baseUrl+'/Playground');

    })
  })