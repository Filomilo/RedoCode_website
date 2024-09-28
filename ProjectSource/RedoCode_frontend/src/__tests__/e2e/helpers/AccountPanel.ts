namespace AccountPanel {
  export function logout() {
    cy.get('#logout').click()
    cy.url().should('eq', Cypress.config().baseUrl + '/Home')
  }
}

export default AccountPanel
