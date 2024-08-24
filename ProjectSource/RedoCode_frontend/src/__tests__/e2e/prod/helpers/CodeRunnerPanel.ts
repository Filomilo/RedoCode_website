namespace CodeRunnerPanel {
  export namespace CodeRunnerInput {
    const codeEditorSequance = '#coderunner-editor-panel textarea'

    const backspaces = '{selectAll}{backspace}'

    export function codeRunnerShouldContain(code: string) {
      cy.get('#code-preview').invoke('text').should('contain', code)
    }

    export function clearCodeRunner() {
      if (Cypress.platform === 'linux') {
        cy.wait(1000)
        cy.get(codeEditorSequance).focus()
        cy.wait(1000)
        cy.get(codeEditorSequance).type(backspaces, { force: true })
      } else {
        cy.get(codeEditorSequance).focus()
        cy.get(codeEditorSequance).type(backspaces, { force: true })
        cy.get(codeEditorSequance).type(backspaces, { force: true })
        cy.get(codeEditorSequance).clear()
      }
    }

    export function inputToCodeRunner(txt: string) {
      if (Cypress.platform === 'linux') {
        for (let i = 0; i < txt.length; i++) {
          cy.get(codeEditorSequance).focus()
          cy.get(codeEditorSequance).type(txt[i], { force: true })
        }
      } else {
        cy.get(codeEditorSequance).type(txt, { force: true })
      }
    }

    export function moveToEndOfCodeRunner() {
      if (Cypress.platform === 'linux') cy.wait(1000)
      cy.get(codeEditorSequance).type('{moveToEnd}', { force: true })
    }
  }

  export namespace Tests {
    export function shouldAllTestFail(amountOfTest: number) {
      cy.get(
        '#TestResultCard' + '0' + ' > div.testValidationSection.wrong'
      ).should('have.text', 'Failed')
      for (let index = 1; index < amountOfTest; index++) {
        cy.get(
          '#TestResultCard' + index + ' > div.testValidationSection'
        ).should('be.empty')
      }
    }
    export function shouldAllTesCorrect(amountOfTest: number) {
      for (let index = 0; index < amountOfTest; index++) {
        cy.get(
          '#TestResultCard' + index + ' > div.testValidationSection.correct'
        ).should('have.text', 'Correct')
      }
    }

    export function checkTest(checks: any[]) {
      for (let index = 0; index < checks.length; index++) {
        cy.get('#TestResultCard' + index)
          .contains('span', 'Result')
          .click()
        cy.get('#tab-result-expected-container-' + index).contains(
          'expeteced: "' + checks[index].output + '"'
        )
        cy.get('#tab-result-achived-container-' + index).contains(
          'achived: "' + checks[index].output + '"'
        )
      }
    }
  }

  export function selectInitialLanguage(lang: string) {
    cy.get('#coderunner-dropdown').click()
    cy.get('.p-dropdown-item').contains(lang).click()
    cy.get('#connect-button').click()
    cy.get('#coderunner-loading-dialog').should('not.exist')
  }

  export function run() {
    cy.get('#coderunner-run-button').should('be.enabled')
    cy.get('#coderunner-run-button').should('be.visible')

    cy.get('#coderunner-run-button').click()
  }

  export function submit() {
    cy.get('#coderunner-submit-button').click()
  }

  export function switchLanguage(name: string) {
    cy.get('#coderunner-langage-dropdown').click()
    cy.get('.p-dropdown-item').contains(name).click()
    cy.get('.p-button').contains('span', 'Change').click()
    cy.get('#coderunner-loading-dialog').should('not.exist')
  }

  export namespace RawCodeResults {
    export function shouldResultBe(console: string) {
      cy.get(
        'html body div#app html div#MainPageContainer div.BackGroundContainer main.PlayGroundBase div.heightLimit div.p-splitter.p-component.p-splitter-horizontal div.p-splitter-panel div.CodeResultContainer div.ConsoleResultConsoleCOntainer div.ConsoleResultConsoleCOntainerText'
      ).contains(console)
    }
  }

  export function stateShouldBe(state: string) {
    cy.get(
      'html body div#app html div#MainPageContainer div.BackGroundContainer main#ContentConatiner.PlayGroundBase div.heightLimit div.p-splitter.p-component.p-splitter-horizontal div.p-splitter-panel div.CodeResultContainer div.EngineStatusContianer div.EngineStatusPanel div.EngineStatusStatus'
    ).contains(state)
  }

  export function shouldSubmitAccess(acces: boolean) {
    if (acces) cy.get('#coderunner-submit-button').should('be.enabled')
    else cy.get('#coderunner-submit-button').should('be.disabled')
  }

  export namespace information {
    export function nameShould(name: string) {
      cy.get('#exercise-title').contains(name)
    }

    export function descriptionShouldBe(description: string) {
      cy.get('#exercise-description').contains(description)
    }
  }
}

export default CodeRunnerPanel
