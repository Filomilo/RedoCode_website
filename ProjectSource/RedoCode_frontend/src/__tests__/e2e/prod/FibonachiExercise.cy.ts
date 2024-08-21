import CodeRunnerPanel from "./helpers/CodeRunnerPanel"
import ExercisesPage from "./helpers/ExercisesPage"
import SwitcherControls from "./helpers/SwitcherControls"
import UrlControls from "./helpers/UrlControls"

describe('Phibonachi new exercise', () => {
  it('passes', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      return false
    })
    UrlControls.startPage();
    SwitcherControls.switchExercises();
    ExercisesPage.openExerciseOfName("fibonachi sequance")
    ExercisesPage.shouldBeOnUrlOfExerciseId(1)
    CodeRunnerPanel.selectInitialLanguage("cpp")
    CodeRunnerPanel.stateShouldBe('ACTIVE')
    CodeRunnerPanel.CodeRunnerInput.codeRunnerShouldContain("int solution(int x){")
    CodeRunnerPanel.CodeRunnerInput.clearCodeRunner()
    CodeRunnerPanel.CodeRunnerInput.inputToCodeRunner("console.log('Hello World!')")
  })
})

// todo: protabaly better to create cypress tests that only run mock
// and addtional tests that run on relase version
