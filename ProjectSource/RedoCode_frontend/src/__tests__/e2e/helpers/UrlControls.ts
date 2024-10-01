namespace UrlControls {
  export function startPage() {
    cy.visit('/')
  }

  export function urlShouldBe(url: string) {
    cy.url({ timeout: 15000 }).should(
      'eq',
      Cypress.config().baseUrl + '/' + url
    )
  }
  export function urlShouldContain(url: string) {
    cy.url({ timeout: 15000 }).should('contain', url)
  }

  export function visitUserPanel(auth: boolean = true) {
    cy.visit('/Account')
    if (auth) 
      {
        cy.url().should('eq', Cypress.config().baseUrl + '/Account')
        cy.get('#Account_Info_Panel',{timeout: 10000}).should("be.visible");
      }else cy.url().should('eq', Cypress.config().baseUrl + '/Home')
    
  }

  export function visitPlayground() {
    cy.visit('/Playground')
    cy.url().should('eq', Cypress.config().baseUrl + '/Playground')
  }

  export function visitCreatePanel(auth: boolean = true) {
    cy.visit('/Create')
    if (auth) cy.url().should('eq', Cypress.config().baseUrl + '/Create')
    else cy.url().should('eq', Cypress.config().baseUrl + '/Home')
  }
  export function visitResults(id: number) {
    cy.visit('/Results/' + id)
    cy.url().should('eq', Cypress.config().baseUrl + '/Results/' + id)
  }
  export function visitSolutions(id: number) {
    cy.visit('/Solutions/' + id)
    cy.url().should('eq', Cypress.config().baseUrl + '/Solutions/' + id)
  }
}

export default UrlControls
