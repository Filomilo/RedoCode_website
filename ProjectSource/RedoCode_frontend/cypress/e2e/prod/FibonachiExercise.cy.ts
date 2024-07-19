

describe('spec', () => {
  it('passes', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      return false
  })
    cy.visit('/')
    cy.get("a.TopBarItemContainer:nth-child(3)").click()
    cy.url().should('eq', Cypress.config().baseUrl+'/Exercises');
    cy.get('html body div#app html div#MainPageContainer div.BackGroundContainer main#ContentConatiner.PlayGroundBase.locked div.datatable-container div.vue3-easy-data-table.dataTableStyle div.vue3-easy-data-table__main.fixed-header.hoverable table tbody.vue3-easy-data-table__body.row-alternation tr td.direction-left button.p-button.p-component').click()
    cy.url().should('eq', Cypress.config().baseUrl+'/Exercises/1');
    cy.get('#coderunner-dropdown').click()   
    cy.get('#coderunner-dropdown_0').click()
    // eslint-disable-next-line cypress/no-unnecessary-waiting
    cy.wait(1000);
    cy.get('.p-button-label').click()
    cy.get('html body div#app html div#MainPageContainer div.BackGroundContainer main#ContentConatiner.PlayGroundBase div.heightLimit div.p-splitter.p-component.p-splitter-horizontal div.p-splitter-panel div.CodeResultContainer div.EngineStatusContianer div.EngineStatusPanel div.EngineStatusStatus').contains('ACTIVE')
    // cy.get('html body div#app html div#MainPageContainer div.BackGroundContainer main#ContentConatiner.PlayGroundBase div.heightLimit div.p-splitter.p-component.p-splitter-horizontal div.p-splitter-panel div.CodeEditorContainer div div div.monaco-editor.no-user-select.showUnused.showDeprecated.vs-dark div.overflow-guard div.monaco-scrollable-element.editor-scrollable.vs-dark div.lines-content.monaco-editor-background div.view-lines.monaco-mouse-cursor-text div.view-line').type("console.log('Hello World!')")

    })
  })

    // todo: protabaly better to create cypress tests that only run mock
    // and addtional tests that run on relase version