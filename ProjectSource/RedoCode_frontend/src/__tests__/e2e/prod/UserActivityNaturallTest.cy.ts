import CodeRunnerPanel from '../helpers/CodeRunnerPanel'
import ErrorPage from '../helpers/ErrorPage'
import ExecutionChain from '../helpers/ExecutionChain'
import ExercisesPage from '../helpers/ExercisesPage'
import Healthcheck from '../helpers/HealthCheck'
import SwitcherControls from '../helpers/SwitcherControls'
import UrlControls from '../helpers/UrlControls'

describe('User natural activity test', () => {
  it('switicnih between code runner', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      if (err.message.includes('ResizeObserver')) {
        return false
      }
    })
    cy.reload()
    const helloWorldProgram = 'console.log("Hello World!")'
    const helloWorldProgramCpp = '#include <iostream>\n int main(std::cout<<"Hello World!"; return 0;'

    const cppSolution = `int solution(int val)
    {
        int* arr=new int[val];
    
    if(val>=0)
      arr[0]=0;
    if (val>=2)
       arr[1]=1;
    for(int i=2;i<val;i++)
        {
            arr[i]=arr[i-1]+arr[i-2];
    }
        return arr[val-1];
    }`



    // hello owrld js
    UrlControls.startPage()
    SwitcherControls.switchPlayground()
    CodeRunnerPanel.selectInitialLanguage('js')
    CodeRunnerPanel.CodeRunnerInput.clearCodeRunner()
    CodeRunnerPanel.CodeRunnerInput.inputToCodeRunner(helloWorldProgram)
    CodeRunnerPanel.RawCodeResults.shouldResultBe('')
    CodeRunnerPanel.run()
    CodeRunnerPanel.RawCodeResults.shouldResultBe('Hello World!')



    // fibonachi test 



    UrlControls.startPage()
    SwitcherControls.switchExercises()
    ExercisesPage.openExerciseOfName('fibonachi sequance')
    ExercisesPage.shouldBeOnUrlOfExerciseId(1)

    CodeRunnerPanel.selectInitialLanguage('cpp')
    CodeRunnerPanel.stateShouldBe('ACTIVE')
    CodeRunnerPanel.CodeRunnerInput.codeRunnerShouldContain(
      'int solution(int x){'
    )
    CodeRunnerPanel.CodeRunnerInput.clearCodeRunner()
    CodeRunnerPanel.information.nameShould('fibonachi sequance')
    CodeRunnerPanel.information.descriptionShouldBe(
      'Create funciton that returns number at point of fibocnahi squnace so 1->0 2->1 3->1 4->2 5->3 and do on'
    )
    CodeRunnerPanel.CodeRunnerInput.inputToCodeRunner('test')
    CodeRunnerPanel.shouldSubmitAccess(false)
    CodeRunnerPanel.run()
    CodeRunnerPanel.Tests.shouldAllTestFail(1)


    /// again js hellow world
    SwitcherControls.switchPlayground()
    CodeRunnerPanel.selectInitialLanguage('js')
    CodeRunnerPanel.CodeRunnerInput.clearCodeRunner()
    CodeRunnerPanel.CodeRunnerInput.inputToCodeRunner(helloWorldProgram)
    CodeRunnerPanel.RawCodeResults.shouldResultBe('')
    CodeRunnerPanel.run()
    CodeRunnerPanel.RawCodeResults.shouldResultBe('Hello World!')

    // and again fibonacci
    SwitcherControls.switchExercises()
    ExercisesPage.openExerciseOfName('fibonachi sequance')
    ExercisesPage.shouldBeOnUrlOfExerciseId(1)

    CodeRunnerPanel.selectInitialLanguage('cpp')
    CodeRunnerPanel.stateShouldBe('ACTIVE')
    CodeRunnerPanel.CodeRunnerInput.codeRunnerShouldContain(
      'int solution(int x){'
    )
    CodeRunnerPanel.CodeRunnerInput.clearCodeRunner()
    CodeRunnerPanel.CodeRunnerInput.inputToCodeRunner(cppSolution)
    CodeRunnerPanel.run()
    CodeRunnerPanel.Tests.shouldAllTesCorrect(7)
    CodeRunnerPanel.submit()
    ExecutionChain.close()
    UrlControls.urlShouldBe('Results/1')
    ErrorPage.UnauthenticatedResultPage.UnathenticatedPanelShouldBeVisible()
    ErrorPage.UnauthenticatedResultPage.ClickGoToLogin()


    // cpp hell world
    SwitcherControls.switchPlayground()
    CodeRunnerPanel.selectInitialLanguage('cpp')
    CodeRunnerPanel.CodeRunnerInput.clearCodeRunner()
    CodeRunnerPanel.CodeRunnerInput.inputToCodeRunner(helloWorldProgramCpp)
    CodeRunnerPanel.RawCodeResults.shouldResultBe('')
    CodeRunnerPanel.run()
    CodeRunnerPanel.RawCodeResults.shouldResultBe('Hello World!')

    })
})
