namespace UserAuthentication{



export function login(email: string, password: string)
{
    cy.visit('/')
    cy.get('#login-email').clear()
    cy.get('#login-email').type(email)
    cy.get('#login-password').clear()
    cy.get('#login-password').type(password)
    cy.get('#login').click()
    cy.get('.p-toast-detail')
      .contains('Succesfully logged in')
      .should('be.visible')
    cy.get('.p-toast-detail').should('not.exist')
}

export function Register(email:string, password:string)
{
  cy.get('#switch-register').click()
  cy.url().should('eq', Cypress.config().baseUrl + '/Register')
  cy.get('#register-email').clear().type(email)
  cy.get('#register-nick').clear().type('testtest')
  cy.get('#register-password').clear().type(password)
  cy.get('#register-repeatpassword').clear().type(password)
  cy.get('#register-button').click()
  cy.url().should('eq', Cypress.config().baseUrl + '/Home')
}

}


export default UserAuthentication;