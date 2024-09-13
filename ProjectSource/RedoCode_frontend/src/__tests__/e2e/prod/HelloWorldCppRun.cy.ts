import CodeRunnerPanel from '../helpers/CodeRunnerPanel'
import SwitcherControls from '../helpers/SwitcherControls'
import UrlControls from '../helpers/UrlControls'

describe.skip('hello world cpp run ', () => {
  it('passes', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      return false
    })
    UrlControls.startPage()
    SwitcherControls.switchPlayground()
    CodeRunnerPanel.selectInitialLanguage('cpp')
    CodeRunnerPanel.CodeRunnerInput.clearCodeRunner()
    CodeRunnerPanel.CodeRunnerInput.inputToCodeRunner(
      '#include <iostream>\n\n int main(){\n std::cout<<"Hello World!";\n return 0;\n}'
    )
    CodeRunnerPanel.run()
    CodeRunnerPanel.RawCodeResults.shouldResultBe('Hello World!')
  })
})
