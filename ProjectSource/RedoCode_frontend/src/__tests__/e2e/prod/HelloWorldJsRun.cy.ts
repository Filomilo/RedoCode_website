import CodeRunnerPanel from "./helpers/CodeRunnerPanel"
import SwitcherControls from "./helpers/SwitcherControls"

describe('hello world js run ', () => {
  it('passes', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      return false
    })

    const helloWorldProgram = 'console.log("Hello World!")'

    cy.visit('/')

    SwitcherControls.switchPlayground()
    CodeRunnerPanel.selectInitialLanguage("js")
    CodeRunnerPanel.CodeRunnerInput.clearCodeRunner()
    CodeRunnerPanel.CodeRunnerInput.inputToCodeRunner(helloWorldProgram)
    CodeRunnerPanel.run();
    CodeRunnerPanel.RawCodeResults.shouldResultBe('Hello World!')

  })
})
