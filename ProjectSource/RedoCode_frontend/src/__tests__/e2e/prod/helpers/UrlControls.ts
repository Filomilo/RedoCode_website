namespace UrlControls{
    export function startPage()
    {
        cy.visit('/')
    }

    export function visitUserPanel(auth: boolean=true)
    {
        cy.visit('/Account')
        if(auth)
        cy.url().should('eq', Cypress.config().baseUrl + '/Account')
        else
        cy.url().should('eq', Cypress.config().baseUrl + '/Home')
    }

export function visitPlayground()
{
    cy.visit('/Playground')
    cy.url().should('eq', Cypress.config().baseUrl + '/Playground')
}


export function visitCreatePanel(auth: boolean=true)
{
    cy.visit('/Create')
    if(auth)
    cy.url().should('eq', Cypress.config().baseUrl + '/Create')
    else
    cy.url().should('eq', Cypress.config().baseUrl + '/Home')
}

}




export default UrlControls