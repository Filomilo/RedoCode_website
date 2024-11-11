import CodeRunnerPanel from '../helpers/CodeRunnerPanel'
import ExercisesPage from '../helpers/ExercisesPage';
import SwitcherControls from '../helpers/SwitcherControls'
import UrlControls from '../helpers/UrlControls'
import { slowCypressDown } from 'cypress-slow-down'
// slowCypressDown()

const helloWorldProgramCPP = `
#include<iostream>
int main()
{
std::cout<<"Hello World!";
return 0;
}
`;

const helloWorldProgramJS = 'console.log("Hello World!")'
const TESTProgramJS = 'console.log("TEST")'

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

  //RUN JS 
  CodeRunnerPanel.selectInitialLanguage('js')
  CodeRunnerPanel.CodeRunnerInput.clearCodeRunner()
  CodeRunnerPanel.CodeRunnerInput.inputToCodeRunner(TESTProgramJS)
  CodeRunnerPanel.RawCodeResults.shouldResultBe('')
  CodeRunnerPanel.run()
  CodeRunnerPanel.RawCodeResults.shouldResultBe('TEST')
  }),






  it('swtiching between code runenrs directl', () => {
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
  
  
  //Swtich To exercise
  SwitcherControls.switchExercises();
  ExercisesPage.openExerciseOfName("fibonachi sequance")
  CodeRunnerPanel.selectInitialLanguage('js')
  CodeRunnerPanel.run()
  CodeRunnerPanel.Tests.shouldAllTestFail(1)
  

  // switch to playground
  SwitcherControls.switchPlayground();
  CodeRunnerPanel.selectInitialLanguage('cpp')
  CodeRunnerPanel.CodeRunnerInput.clearCodeRunner()
  CodeRunnerPanel.CodeRunnerInput.inputToCodeRunner(helloWorldProgramCPP)
  CodeRunnerPanel.RawCodeResults.shouldResultBe('')
  CodeRunnerPanel.run()
  CodeRunnerPanel.RawCodeResults.shouldResultBe('Hello World!')
  })
})
