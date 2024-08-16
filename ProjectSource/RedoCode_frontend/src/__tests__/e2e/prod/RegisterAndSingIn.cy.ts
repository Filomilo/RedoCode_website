describe('register and sign in', () => {
  it('passes', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      return false
    })
    const email = 't1ea122223t1@test.com'
    const password = 'Password+789'
    cy.visit('/')
    cy.get('#switch-register').click()
    cy.url().should('eq', Cypress.config().baseUrl + '/Register')
    cy.get('#register-email').clear().type(email)
    cy.get('#register-nick').clear().type('testtest')
    cy.get('#register-password').clear().type(password)
    cy.get('#register-repeatpassword').clear().type(password)
    cy.get('#register-button').click()
    cy.url().should('eq', Cypress.config().baseUrl + '/Home')
    cy.get('#switch-account').click()
    cy.url().should('eq', Cypress.config().baseUrl + '/Account')
    cy.get('#logout').click()
    cy.url().should('eq', Cypress.config().baseUrl + '/home')
    cy.get('#login-email').clear().type(email)
    cy.get('#login-password').clear().type(password)
    cy.get('#login').click()
    cy.get('#switch-account').click()
    cy.url().should('eq', Cypress.config().baseUrl + '/Account')
  })
})
