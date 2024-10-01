import ToastHelper from "./ToastHelper"

namespace UserAuthentication {
  export function login(email: string, password: string, shouldSucceed: boolean=true) {
    cy.visit('/')
    cy.get('#login-email').clear()
    cy.get('#login-email').type(email)
    cy.get('#login-password').clear()
    cy.get('#login-password').type(password)
    cy.get('#login').click()
    ToastHelper.shouldToastAtLeastOne();
    if(shouldSucceed)
    ToastHelper.shouldHaveSuccessToast('Successfully logged in')
  else
  ToastHelper.shouldHaveErrorToast("Couldn't Login")
    ToastHelper.shouldToastNo();
  }

  export function Register(email: string, password: string,nickname: string) {
    cy.get('#switch-register').click()
    cy.url().should('eq', Cypress.config().baseUrl + '/Register')
    cy.get('#register-email').clear().type(email)
    cy.get('#register-nick').clear().type(nickname)
    cy.get('#register-password').clear().type(password)
    cy.get('#register-repeatpassword').clear().type(password)
    cy.get('#register-button').click()
    cy.url().should('eq', Cypress.config().baseUrl + '/Home')
    ToastHelper.shouldToastNo();
  }
}

export default UserAuthentication
