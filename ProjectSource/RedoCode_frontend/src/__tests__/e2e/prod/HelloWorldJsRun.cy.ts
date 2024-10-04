import CodeRunnerPanel from '../helpers/CodeRunnerPanel'
import SwitcherControls from '../helpers/SwitcherControls'
import UrlControls from '../helpers/UrlControls'

describe('hello world js run ', () => {
  it('passes', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      if (err.message.includes('ResizeObserver')) {
        return false
      }
    })
    cy.reload()
    const helloWorldProgram = 'console.log("Hello World!")'

    UrlControls.startPage()

    SwitcherControls.switchPlayground()
    SwitcherControls.switchPlayground()
    cy.reload()
    SwitcherControls.switchPlayground()
    SwitcherControls.switchHome()
    SwitcherControls.switchPlayground()
    CodeRunnerPanel.selectInitialLanguage('js')
    CodeRunnerPanel.CodeRunnerInput.clearCodeRunner()
    CodeRunnerPanel.CodeRunnerInput.inputToCodeRunner(helloWorldProgram)
    CodeRunnerPanel.RawCodeResults.shouldResultBe('')
    CodeRunnerPanel.run()
    CodeRunnerPanel.RawCodeResults.shouldResultBe('Hello World!')
  })
})
