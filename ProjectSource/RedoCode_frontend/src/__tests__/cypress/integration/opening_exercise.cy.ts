

describe('spec', () => {
  it('passes', () => {
    cy.visit('/')
    // cy.get('#Excersices_button > b-nav-text:nth-child(1)').click()
    // cy.url().should('eq', Cypress.config().baseUrl+'/Exercises');
    // cy.get('html > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(3) > tr:nth-child(1) > td:nth-child(5) > button:nth-child(1) > svg:nth-child(1)').click()
    // cy.url().should('eq', Cypress.config().baseUrl+'/Exercises/1');
    })
  })

    // todo: protabaly better to create cypress tests that only run mock
    // and addtional tests that run on relase version