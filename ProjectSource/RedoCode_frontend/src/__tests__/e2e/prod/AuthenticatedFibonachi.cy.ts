import CodeRunnerPanel from '../helpers/CodeRunnerPanel'
import ErrorPage from '../helpers/ErrorPage'
import ExecutionChain from '../helpers/ExecutionChain'
import ExercisesPage from '../helpers/ExercisesPage'
import SwitcherControls from '../helpers/SwitcherControls'
import UrlControls from '../helpers/UrlControls'
import UserAuthentication from '../helpers/UserAuthentication'
import ResultPage from '../helpers/ResultPage'
import SolutionsList from '../helpers/SolutionsList'
import ToastHelper from '../helpers/ToastHelper'

describe('Authenticated FIbonachi', () => {
  it('Full sovling fibonachi', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      return false
    })
    const mail = 'sparky@mail.com'
    const password = 'Password+123'
    const nick = 'sparky'
    const comment = 'COmment_123'
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
    const executionChainTemplate: ExecutionChain.ChainNodeType[] = [
      {
        correct: true,
        desc: 'Data loaded',
      },
      {
        correct: true,
        desc: 'Validated access to CPP_RUNNER',
      },
      {
        correct: true,
        desc: 'generated',
      },
      {
        correct: true,
        desc: 'solved',
      },
      {
        correct: true,
        desc: 'correct CPP_RUNNER tests',
      },
      {
        correct: true,
        desc: 'Saved solution',
      },
    ]
    UrlControls.startPage()
    SwitcherControls.switchExercises()
    ExercisesPage.openExerciseOfName('fibonachi sequance')
    ExercisesPage.shouldBeOnUrlOfExerciseId(1)
    ToastHelper.WaitForShowingAndClosingToast()
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

    UrlControls.startPage()
    UserAuthentication.login(mail, password)
    SwitcherControls.switchExercises()
    ExercisesPage.openExerciseOfName('fibonachi sequance')
    ExercisesPage.shouldBeOnUrlOfExerciseId(1)
    CodeRunnerPanel.selectInitialLanguage('cpp')
    CodeRunnerPanel.stateShouldBe('ACTIVE')
    CodeRunnerPanel.CodeRunnerInput.clearCodeRunner()
    CodeRunnerPanel.information.nameShould('fibonachi sequance')
    CodeRunnerPanel.information.descriptionShouldBe(
      'Create funciton that returns number at point of fibocnahi squnace so 1->0 2->1 3->1 4->2 5->3 and do on'
    )
    CodeRunnerPanel.CodeRunnerInput.clearCodeRunner()
    CodeRunnerPanel.CodeRunnerInput.inputToCodeRunner(cppSolution)
    CodeRunnerPanel.run()
    CodeRunnerPanel.Tests.shouldAllTesCorrect(7)
    CodeRunnerPanel.submit()
    ExecutionChain.checkSuccses(executionChainTemplate)
    ExecutionChain.close()
    UrlControls.urlShouldBe('Results/1')
    SwitcherControls.switchExercises()
    ExercisesPage.openExerciseOfName('fibonachi sequance')
    UrlControls.urlShouldBe('Results/1')
    ResultPage.ResultPanel.ExecutionTimeShouldBeLess(200)
    ResultPage.ResultPanel.SolutionBetterProcetShouldGreater(0)
    // ResultPage.ResultPanel.resulrRankShouldBe(1)
    ResultPage.Rating.clickRating(4)
    ResultPage.Rating.selectedRating('Very hard')
    ResultPage.Rating.clickRate()
    UrlControls.urlShouldBe('Solutions/1')
    SwitcherControls.switchExercises()
    ExercisesPage.openExerciseOfName('fibonachi sequance')
    UrlControls.urlShouldBe('Solutions/1')
    SolutionsList.solutionListClick(0)
    SolutionsList.solutionListClick(1)
    SolutionsList.solutionListClick(2)
    SolutionsList.postComment(comment)
    SolutionsList.shouldUsernameCommentBe(0, nick)
    SolutionsList.shouldContentCommentBe(0, comment)
    SwitcherControls.switchExercises()
    ExercisesPage.openExerciseOfName('fibonachi sequance')
    UrlControls.urlShouldBe('Solutions/1')
    SolutionsList.shouldUsernameCommentBe(0, nick)
    SolutionsList.shouldContentCommentBe(0, comment)
  }),
  it('Raing SOlvedExercise', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      return false
    })
    const mail = 'whisper@mail.com'
    const password = 'Password+123'
    const nick = 'whispe'
    const comment = 'COmment_123'
   UserAuthentication.login(mail,password)
    UrlControls.startPage()
    SwitcherControls.switchExercises()
    ExercisesPage.openExerciseOfName('fibonachi sequance')
    cy.wait(3000)
    ResultPage.Rating.clickRating(4)
    ResultPage.Rating.selectedRating('Very hard')
    ResultPage.Rating.clickRate()
    UrlControls.urlShouldBe('Solutions/1')
    SwitcherControls.switchExercises()
    ExercisesPage.openExerciseOfName('fibonachi sequance')
    UrlControls.urlShouldBe('Solutions/1')
    SolutionsList.solutionListClick(0)
    SolutionsList.solutionListClick(1)
    SolutionsList.solutionListClick(2)
    SolutionsList.postComment(comment)
    SolutionsList.shouldUsernameCommentBe(0, nick)
    SolutionsList.shouldContentCommentBe(0, comment)
    SwitcherControls.switchExercises()
    ExercisesPage.openExerciseOfName('fibonachi sequance')
    UrlControls.urlShouldBe('Solutions/1')
    SolutionsList.shouldUsernameCommentBe(0, nick)
    SolutionsList.shouldContentCommentBe(0, comment)
  })
})
