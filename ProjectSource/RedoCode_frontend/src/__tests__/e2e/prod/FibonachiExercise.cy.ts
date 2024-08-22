import CodeRunnerPanel from "./helpers/CodeRunnerPanel"
import ExecutionChain from "./helpers/ExecutionChain"
import ExercisesPage from "./helpers/ExercisesPage"
import SwitcherControls from "./helpers/SwitcherControls"
import UrlControls from "./helpers/UrlControls"

describe('Phibonachi new exercise', () => {
  it('passes', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      return false
    })

    const cppSolution=`int solution(int val)
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
    }`;


    const executionChainTemplate: ExecutionChain.ChainNodeType[]=[
      {
        correct: true,
        desc: ""
      }
    ]

    UrlControls.startPage();
    SwitcherControls.switchExercises();
    ExercisesPage.openExerciseOfName("fibonachi sequance")
    ExercisesPage.shouldBeOnUrlOfExerciseId(1)
    CodeRunnerPanel.selectInitialLanguage("cpp")
    CodeRunnerPanel.stateShouldBe('ACTIVE')
    CodeRunnerPanel.CodeRunnerInput.codeRunnerShouldContain("int solution(int x){")
    CodeRunnerPanel.CodeRunnerInput.clearCodeRunner()
  
    CodeRunnerPanel.CodeRunnerInput.inputToCodeRunner("test");
    CodeRunnerPanel.shouldSubmitAccess(false)
    CodeRunnerPanel.run();
    CodeRunnerPanel.Tests.shouldAllTestFail(1);
    CodeRunnerPanel.CodeRunnerInput.clearCodeRunner();
    CodeRunnerPanel.CodeRunnerInput.inputToCodeRunner(cppSolution)
    CodeRunnerPanel.run();
    CodeRunnerPanel.Tests.shouldAllTesCorrect(7)
    CodeRunnerPanel.submit()

    
  })
})

// todo: protabaly better to create cypress tests that only run mock
// and addtional tests that run on relase version
