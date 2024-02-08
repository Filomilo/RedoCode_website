

describe('spec', () => {
  it('passes', () => {
    cy.visit('/')
    cy.get('#Excersices_button > b-nav-text:nth-child(1)').click()
    cy.url().should('eq', Cypress.config().baseUrl+'/Exercises');
    cy.contains("Hello world");
    cy.get('tr.excercise-table-row:nth-child(1) > th:nth-child(1)').click()
    cy.url().should('eq', Cypress.config().baseUrl+'/Exercises/1');
    })
  })


    // todo: protabaly better to create cypress tests that only run mock
    // and addtional tests that run on relase version