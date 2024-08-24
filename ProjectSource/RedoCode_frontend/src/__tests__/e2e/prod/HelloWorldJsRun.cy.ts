import CodeRunnerPanel from './helpers/CodeRunnerPanel'
import SwitcherControls from './helpers/SwitcherControls'
import UrlControls from './helpers/UrlControls'

describe('hello world js run ', () => {
  it('passes', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      return false
    })

    const helloWorldProgram = 'console.log("Hello World!")'

    UrlControls.startPage()

    SwitcherControls.switchPlayground()
    CodeRunnerPanel.selectInitialLanguage('js')
    CodeRunnerPanel.CodeRunnerInput.clearCodeRunner()
    CodeRunnerPanel.CodeRunnerInput.inputToCodeRunner(helloWorldProgram)
    CodeRunnerPanel.run()
    CodeRunnerPanel.RawCodeResults.shouldResultBe('Hello World!')
  })
})
