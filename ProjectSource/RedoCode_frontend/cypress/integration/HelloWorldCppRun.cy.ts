describe('template spec', () => {
  it('passes', () => {
      Cypress.on('uncaught:exception', (err, runnable) => {
        return false
    })
    cy.visit('/')
    cy.get("a.TopBarItemContainer:nth-child(2)").click()
    cy.url().should('eq', Cypress.config().baseUrl+'/PlayGround');
    cy.get('#pv_id_5').click()   
    cy.get('#pv_id_5_0').click()
    cy.get('.p-button-label').click()
    cy.get('html body div#app html div#MainPageContainer div.BackGroundContainer main#ContentConatiner.PlayGroundBase div.heightLimit div.p-splitter.p-component.p-splitter-horizontal div.p-splitter-panel div.CodeResultContainer div.EngineStatusContianer div.EngineStatusPanel div.EngineStatusStatus').contains('ACTIVE')
    cy.get('html body div#app html div#MainPageContainer div.BackGroundContainer main#ContentConatiner.PlayGroundBase div.heightLimit div.p-splitter.p-component.p-splitter-horizontal div.p-splitter-panel div.CodeEditorContainer div div div.monaco-editor.no-user-select.showUnused.showDeprecated.vs-dark div.overflow-guard div.monaco-scrollable-element.editor-scrollable.vs-dark div.lines-content.monaco-editor-background div.view-lines.monaco-mouse-cursor-text div.view-line').type("#include <iostream>\n\n int main(){\n std::cout<<\"Hello World!\";\n return 0;}")
    cy.get('html body div#app html div#MainPageContainer div.BackGroundContainer main#ContentConatiner.PlayGroundBase div.heightLimit div.p-splitter.p-component.p-splitter-horizontal div.p-splitter-panel div.CodeEditorPanelSetting div.CodeEditorPlayButton button.p-button.p-component svg').click()
    cy.get('html body div#app html div#MainPageContainer div.BackGroundContainer main#ContentConatiner.PlayGroundBase div.heightLimit div.p-splitter.p-component.p-splitter-horizontal div.p-splitter-panel div.CodeResultContainer div.ConsoleResultConsoleCOntainer div.ConsoleResultConsoleCOntainerText').contains(">> Hello World!")
    })
})