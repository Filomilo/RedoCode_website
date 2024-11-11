import CodeRunnerPanel from '../helpers/CodeRunnerPanel'
import SwitcherControls from '../helpers/SwitcherControls'
import UrlControls from '../helpers/UrlControls'


const helloWorldProgramCPP = `
#include<iostream>
int main()
{
std::cout<<"Hello World!";
return 0;
}
`;

const helloWorldProgramJS = 'console.log("Hello World!")'

describe('Switching between code runners ', () => {
  it('swtiching between pages', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      if (err.message.includes('ResizeObserver')) {
        return false
      }
    })
    cy.reload()

    UrlControls.startPage()

    //JS HELLO WORLD
    SwitcherControls.switchPlayground()
    CodeRunnerPanel.selectInitialLanguage('js')
    CodeRunnerPanel.CodeRunnerInput.clearCodeRunner()
    CodeRunnerPanel.CodeRunnerInput.inputToCodeRunner(helloWorldProgramJS)
    CodeRunnerPanel.RawCodeResults.shouldResultBe('')
    CodeRunnerPanel.run()
    CodeRunnerPanel.RawCodeResults.shouldResultBe('Hello World!')
  
  
  //Swtich Around
  SwitcherControls.switchExercises();
  SwitcherControls.switchHome();
  SwitcherControls.switchExercises();
  SwitcherControls.switchPlayground();

  
  
  })
})
