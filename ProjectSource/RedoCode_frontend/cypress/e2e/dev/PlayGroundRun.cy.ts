describe('template spec', () => {
  it('passes', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      return false
  })
    cy.visit('http://localhost:4173/')
    cy.get('a.TopBarItemContainer:nth-child(2)').click()
    cy.url().should('eq', 'http://localhost:4173/PlayGround');
    cy.get('#coderunner-dropdown').click()
    cy.get('#coderunner-dropdown_0').click()   
    // cy.get('div.p-dropdown-panel.p-component.p-ripple-disabled div.p-dropdown-items-wrapper').click()
    // cy.get('html body div#app html div#MainPageContainer div.BackGroundContainer main#ContentConatiner.PlayGroundBase div div.AuthLoginScreenConatiner div.LoginPanelConatiner button.p-button.p-component.BasicButton').click()
    // cy.get('html body div#app html div#MainPageContainer div.BackGroundContainer main#ContentConatiner.PlayGroundBase div div.p-splitter.p-component.p-splitter-horizontal div.p-splitter-panel div.CodeResultContainer div.EngineStatusContianer div.EngineStatusPanel div.EngineStatusStatus').contains('AWAITING')
  })
})