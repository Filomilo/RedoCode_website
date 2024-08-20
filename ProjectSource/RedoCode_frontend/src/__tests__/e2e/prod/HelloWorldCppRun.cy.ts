import CodeRunnerPanel from "./helpers/CodeRunnerPanel"
import SwitcherControls from "./helpers/SwitcherControls"

describe('hello world cpp run ', () => {
  it('passes', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      return false
    })
    cy.visit('/')
    SwitcherControls.switchPlayground()
    CodeRunnerPanel.selectInitialLanguage("cpp")
    CodeRunnerPanel.CodeRunnerInput.clearCodeRunner()
      CodeRunnerPanel.CodeRunnerInput.inputToCodeRunner("#include <iostream>\n\n int main(){\n std::cout<<\"Hello World!\";\n return 0;\n}")
    CodeRunnerPanel.run();
    CodeRunnerPanel.RawCodeResults.shouldResultBe('Hello World!')   
      })
})
