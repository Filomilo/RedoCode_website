import CodeRunnerPanel from '../helpers/CodeRunnerPanel'
import SwitcherControls from '../helpers/SwitcherControls'
import UrlControls from '../helpers/UrlControls'
// test doenst make ene needs fixing later
describe('hello world cpp run ', () => {
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
    CodeRunnerPanel.selectInitialLanguage('js')
    CodeRunnerPanel.CodeRunnerInput.clearCodeRunner()
    CodeRunnerPanel.CodeRunnerInput.inputToCodeRunner(helloWorldProgram)
    CodeRunnerPanel.RawCodeResults.shouldResultBe('')
    CodeRunnerPanel.run()
    CodeRunnerPanel.RawCodeResults.shouldResultBe('Hello World!')
  })
})
