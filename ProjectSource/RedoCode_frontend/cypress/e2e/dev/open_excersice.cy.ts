import { url } from "inspector";

describe('open first exercise', () => {
  it('passes', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      return false
  })
    cy.visit('http://localhost:4173/')
    cy.get('a.TopBarItemContainer:nth-child(3)').click()
    cy.url().should('eq', 'http://localhost:4173/Exercises');
    cy.get('#ContentConatiner > div > div > div.vue3-easy-data-table__main.fixed-header.hoverable > table > tbody > tr:nth-child(1) > td:nth-child(5) > button > svg',{timeout: 10000}).click()
    cy.url().should("eq",'http://localhost:4173/Exercises/1')  
    cy.get('#pv_id_5').click()   
    cy.get('div.p-dropdown-panel.p-component.p-ripple-disabled div.p-dropdown-items-wrapper').click()
    // cy.get('html body div#app html div#MainPageContainer div.BackGroundContainer main#ContentConatiner.PlayGroundBase div div.AuthLoginScreenConatiner div.LoginPanelConatiner button.p-button.p-component.BasicButton').click()
    // cy.get('html body div#app html div#MainPageContainer div.BackGroundContainer main#ContentConatiner.PlayGroundBase div div.p-splitter.p-component.p-splitter-horizontal div.p-splitter-panel div.CodeResultContainer div.EngineStatusContianer div.EngineStatusPanel div.EngineStatusStatus').contains('AWAITING')
  })
})