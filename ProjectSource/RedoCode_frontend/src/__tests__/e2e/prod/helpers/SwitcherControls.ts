namespace SwitcherControls{
    export function switchHome()
    {

    }
    export function switchPlayground()
    {
        cy.get('#switch-playground').click()
        cy.url().should('eq', Cypress.config().baseUrl + '/PlayGround')
    }
    export function switchExercises()
    {
        cy.get('#switch-exercises').click()
        cy.url().should('eq', Cypress.config().baseUrl + '/Exercises')
    }
    export function switchUserPanel()
    {
        cy.get('#switch-account').click()
        cy.url().should('eq', Cypress.config().baseUrl + '/Account')

    }
}
export default SwitcherControls;