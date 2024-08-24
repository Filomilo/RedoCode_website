import CodeRunnerType from "@/types/CodeRunnerTypes"
import ExerciseTest from "@/types/ExcericseTest";
import ProgramResult from "@/types/ProgramResults";

export default abstract class CodeRunnerControllerBase{
    protected _languages: CodeRunnerType[]=[]

    get languages(): CodeRunnerType[] {
      return this._languages??[];
    }

    public constructor()
    {
        this.reset();
    }
    

    abstract reset(): void;

    
    protected processCodeResultLoad(results: ProgramResult[], testGroup:TestGroup)
    {
        testGroup.autoTests = []


        for (let i = 0; i < results.length; i++) {
            if (i < testGroup.tests.length) {
                testGroup.tests[i].consoleOutput =
                results[i].consoleOutput.output
                testGroup.tests[i].errorOutput =
                results[i].consoleOutput.errorOutput
                testGroup.tests[i].output =
                results[i].variables
                testGroup.tests[i].isSolved =
                testGroup.tests[i].expectedOutput ===
                testGroup.tests[i].output
            } else {
              console.log(
                'i: ' +
                  i +
                  '::::' +
                  JSON.stringify(testGroup.tests)
              )
              testGroup.autoTests.push({
                input: results[i].variablesInput!,
                output: results[i].variables,
                expectedOutput: null,
                errorOutput: results[i].consoleOutput.errorOutput,
                consoleOutput: results[i].consoleOutput.output,
                isSolved: results[i].consoleOutput.errorOutput === '',
                uuid: '',
              })
            }
          }
          return testGroup;
    }
}

export interface TestGroup{
    tests: ExerciseTest[]
    autoTests: ExerciseTest[]
}