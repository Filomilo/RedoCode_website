import CodeRunnerPanel from '../helpers/CodeRunnerPanel'
import CreatePanel from '../helpers/CreatePanel'
import ExecutionChain from '../helpers/ExecutionChain'
import ExercisesPage from '../helpers/ExercisesPage'
import SwitcherControls from '../helpers/SwitcherControls'
import UrlControls from '../helpers/UrlControls'
import UserAuthentication from '../helpers/UserAuthentication'
import SolutionsList from '../helpers/SolutionsList'

describe.skip('Create new exercise', () => {
  it('passes', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      return false
    })
    const email = 'sunny@mail.com'
    const password = 'Password+123'
    const nick = 'sunny'

    const title = 'Cesar cipher'
    const description =
      'move every letter in alphabet by 7 so a -> d and z - g, lower case and upper case letters should be handled'

    const cppSolution =
      '#include <iostream>\n' +
      '#include <string>\n' +
      '\n' +
      'std::string solution(std::string input) {\n' +
      '    std::string result = "";\n' +
      '    int shift = 7;\n' +
      '\n' +
      '    for (char c : input) {\n' +
      '        if (isalpha(c)) { \n' +
      "            char base = islower(c) ? 'a' : 'A'; \n" +
      '            result += (c - base + shift) % 26 + base;\n' +
      '        } else {\n' +
      '            result += c;\n' +
      '        }\n' +
      '    }\n' +
      '    return result;\n' +
      '}'

    const jsSolution = `function solution(input) {
          let result = "";
          const shift = 7;

          for (let i = 0; i < input.length; i++) {
              let c = input[i];
              if (/[a-zA-Z]/.test(c)) {
                  let base = c >= 'a' && c <= 'z' ? 'a'.charCodeAt(0) : 'A'.charCodeAt(0);
                  result += String.fromCharCode((c.charCodeAt(0) - base + shift) % 26 + base);
              } else {
                  result += c;
              }
          }
     
          return result;
      }`

    const midpoint = Math.ceil(jsSolution.length / 2)

    const JSfirstHalf = jsSolution.slice(0, midpoint)
    const JSsecondHalf = jsSolution.slice(midpoint)

    const inputsAndOutputs = [
      {
        input: 'A',
        output: 'H',
      },
      {
        input: 'a',
        output: 'h',
      },
      {
        input: 'The quick brown fox jumps over a lazy dog',
        output: 'Aol xbpjr iyvdu mve qbtwz vcly h shgf kvn',
      },
      {
        input: 'The five boxing wizards jump quickly',
        output: 'Aol mpcl ivepun dpghykz qbtw xbpjrsf',
      },
    ]

    const stringOptions = {
      UpperCase: true,
      LowerCase: true,
      Number: true,
      SpecialChar: true,
      breakCharacter: true,
      spaceCharacter: false,
    }

    const chainTests = [
      {
        correct: true,
        desc: 'Correct user type',
      },
      {
        correct: true,
        desc: 'Correct exercise setup',
      },
      {
        correct: true,
        desc: 'generated',
      },
      {
        correct: true,
        desc: 'prepared tests',
      },
      {
        correct: true,
        desc: 'tests finished correctly',
      },
      {
        correct: true,
        desc: 'saved to database',
      },
    ]

    const comment = 'Comment_test'

    UrlControls.startPage()
    UserAuthentication.login(email, password)
    SwitcherControls.switchExercises()
    ExercisesPage.gotoCreatePanel()
    CreatePanel.infoSetup.clearAll()
    CreatePanel.TestConfig.isTestPanelActive(false)
    CreatePanel.infoSetup.setTitle(title)
    CreatePanel.infoSetup.setDescription(description)
    CreatePanel.TestConfig.isTestPanelActive(true)
    CreatePanel.TestConfig.switchToTestConfig()
    CreatePanel.SolutionPanel.isSolutionPanelActive(false)
    CreatePanel.TestConfig.selectLanguages(['cpp', 'js'])
    CreatePanel.TestConfig.setExecutionTime(2000)
    CreatePanel.TestConfig.setHoursTime(1)
    CreatePanel.TestConfig.setMinutesTime(22)
    CreatePanel.TestConfig.setRam(512)
    CreatePanel.TestConfig.setInputType(
      CreatePanel.TestConfig.TYPES.STRING,
      CreatePanel.TestConfig.SIZE.SINGLE
    )
    CreatePanel.TestConfig.setOutputType(
      CreatePanel.TestConfig.TYPES.STRING,
      CreatePanel.TestConfig.SIZE.SINGLE
    )
    CreatePanel.TestConfig.createTests(inputsAndOutputs)
    CreatePanel.TestConfig.setAmountOfAutoTests(6)
    CreatePanel.TestConfig.setLengthMin(1)
    CreatePanel.TestConfig.setLengthMax(20)
    CreatePanel.TestConfig.setStringInputOptions(stringOptions)
    CreatePanel.SolutionPanel.isSolutionPanelActive(true)
    CreatePanel.SolutionPanel.switchToTestSolution()
    CodeRunnerPanel.selectInitialLanguage('js')
    CodeRunnerPanel.information.nameShould(title)
    CodeRunnerPanel.information.descriptionShouldBe(description)
    CodeRunnerPanel.CodeRunnerInput.codeRunnerShouldContain(
      'function solution(x){'
    )
    CodeRunnerPanel.CodeRunnerInput.clearCodeRunner()
    CodeRunnerPanel.CodeRunnerInput.inputToCodeRunner(JSfirstHalf)
    CodeRunnerPanel.run()
    CodeRunnerPanel.Tests.shouldAllTestFail(inputsAndOutputs.length)
    CodeRunnerPanel.switchLanguage('cpp')
    CodeRunnerPanel.CodeRunnerInput.codeRunnerShouldContain(
      'std::string solution(std::string x)'
    )
    CodeRunnerPanel.CodeRunnerInput.clearCodeRunner()
    CodeRunnerPanel.CodeRunnerInput.inputToCodeRunner(cppSolution)
    CodeRunnerPanel.run()
    CodeRunnerPanel.Tests.checkTest(inputsAndOutputs)
    CodeRunnerPanel.switchLanguage('js')
    CodeRunnerPanel.CodeRunnerInput.moveToEndOfCodeRunner()
    CodeRunnerPanel.CodeRunnerInput.inputToCodeRunner(JSsecondHalf)
    CodeRunnerPanel.run()
    CodeRunnerPanel.Tests.checkTest(inputsAndOutputs)
    CodeRunnerPanel.submit()
    ExecutionChain.checkSuccses(chainTests)
    ExecutionChain.close()
    UrlControls.urlShouldBe('Exercises')
    ExercisesPage.shouldBeExerciseOfName(title)
    ExercisesPage.openExerciseOfName(title)
    UrlControls.urlShouldContain('Solutions')
    SolutionsList.shouldTitleBe('Cesar cipher')
    SolutionsList.shouldDescBe(
      'move every letter in alphabet by 7 so a -> d and z - g, lower case and upper case letters should be handled'
    )
    SolutionsList.solutionListClick(0)
    SolutionsList.solutionItemListShouldBeSelected(0)
    SolutionsList.solutionListClick(1)
    SolutionsList.solutionItemListShouldBeSelected(1)
    SolutionsList.postComment(comment)
    SolutionsList.shouldContentCommentBe(0, comment)
    // SolutionsList.shouldUsernameCommentBe(0,nick)
  })
})
