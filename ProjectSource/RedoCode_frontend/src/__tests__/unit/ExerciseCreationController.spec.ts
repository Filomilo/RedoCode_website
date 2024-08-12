import ExerciseCreatorController from '@/controllers/CodeRunner/ExerciseCreatorControlller'
import CodeRunnerType from '@/types/CodeRunnerTypes';
import ExerciseTest from '@/types/ExcericseTest';
import { forEach } from 'node_modules/cypress/types/lodash';
import { describe, it, expect } from 'vitest'

describe('Exercsie creation controller tests', () => { 

    it('adding emptu test', () => {
     const  exerciseCreatorController: ExerciseCreatorController= new ExerciseCreatorController();
     exerciseCreatorController.languages=[CodeRunnerType.CPP_RUNNER,CodeRunnerType.JS_RUNNER]
     expect(exerciseCreatorController.manualTestsSolutions.CPP_RUNNER?.tests.length).equal(0);
     expect(exerciseCreatorController.manualTestsSolutions.JS_RUNNER?.tests.length).equal(0);
     exerciseCreatorController.addEmptyTest();
     exerciseCreatorController.addEmptyTest();
     expect(exerciseCreatorController.manualTestsSolutions.CPP_RUNNER?.tests.length).equal(2);
     expect(exerciseCreatorController.manualTestsSolutions.JS_RUNNER?.tests.length).equal(2);
    })



    it('updatating test', () => {


        const startingTests:ExerciseTest[]=[
            {
                input: 1,
                output: null,
                expectedOutput: 1,
                errorOutput: '',
                consoleOutput: '',
                isSolved: null,
                uuid: ''
            },
            {
                input: 2,
                output: null,
                expectedOutput: 2,
                errorOutput: '',
                consoleOutput: '',
                isSolved: null,
                uuid: ''
            },
            {
                input: 3,
                output: null,
                expectedOutput: 3,
                errorOutput: '',
                consoleOutput: '',
                isSolved: null,
                uuid: ''
            },
        ];


        


        const  exerciseCreatorController: ExerciseCreatorController= new ExerciseCreatorController();
        exerciseCreatorController.languages=[CodeRunnerType.CPP_RUNNER,CodeRunnerType.JS_RUNNER]
        exerciseCreatorController.getSingleRowOfManualTests=startingTests;

        exerciseCreatorController.updateTests([
            {


                consoleOutput: {
                    exitCode: 0,
                    output: 'run correct',
                    errorOutput: ''
                },
                variables: 1,
                variablesInput: null
            },
            {
                consoleOutput: {
                    exitCode: 0,
                    output: 'run error',
                    errorOutput: 'error'
                },
                variables: 3,
                variablesInput: null
            },
        ]
        ,CodeRunnerType.CPP_RUNNER
        )

        expect(exerciseCreatorController.manualTestsSolutions.JS_RUNNER?.tests).eql(
            startingTests
        )

        expect(exerciseCreatorController.manualTestsSolutions.JS_RUNNER?.tests[0]).eql(
            {
                
            input: 1,
            output: 1,
            expectedOutput: 1,
            errorOutput: '',
            consoleOutput: 'run correct',
            isSolved: true,
            uuid: ''
                
            }
        )
        expect(exerciseCreatorController.manualTestsSolutions.JS_RUNNER?.tests[1]).eql(
            {
                
            input: 2,
            output: 3,
            expectedOutput: 2,
            errorOutput: 'error',
            consoleOutput: 'run error',
            isSolved: false,
            uuid: ''
                
            }
        )
        expect(exerciseCreatorController.manualTestsSolutions.JS_RUNNER?.tests[2]).eql(
            startingTests[2]
        )
       })





       it('updatating test with auto tests', () => {


        const startingTests:ExerciseTest[]=[
            {
                input: 1,
                output: null,
                expectedOutput: 1,
                errorOutput: '',
                consoleOutput: '',
                isSolved: null,
                uuid: ''
            },
            {
                input: 2,
                output: null,
                expectedOutput: 2,
                errorOutput: '',
                consoleOutput: '',
                isSolved: null,
                uuid: ''
            },
            {
                input: 3,
                output: null,
                expectedOutput: 3,
                errorOutput: '',
                consoleOutput: '',
                isSolved: null,
                uuid: ''
            },
        ];


        


        const  exerciseCreatorController: ExerciseCreatorController= new ExerciseCreatorController();
        exerciseCreatorController.languages=[CodeRunnerType.CPP_RUNNER,CodeRunnerType.JS_RUNNER]
        exerciseCreatorController.getSingleRowOfManualTests=startingTests;

        exerciseCreatorController.updateTests([
            {
                consoleOutput: {
                    exitCode: 0,
                    output: 'run correct',
                    errorOutput: ''
                },
                variables: 1,
                variablesInput: 1
            },
            {
                consoleOutput: {
                    exitCode: 0,
                    output: 'run ',
                    errorOutput: ''
                },
                variables: 2,
                variablesInput: 2
            },
            {
                consoleOutput: {
                    exitCode: 0,
                    output: 'run',
                    errorOutput: ''
                },
                variables: 3,
                variablesInput: 3
            },
            {
                consoleOutput: {
                    exitCode: 0,
                    output: 'run',
                    errorOutput: ''
                },
                variables: 4,
                variablesInput: 4
            },
            {
                consoleOutput: {
                    exitCode: 0,
                    output: 'run ',
                    errorOutput: ''
                },
                variables: 5,
                variablesInput: 5
            },
            {
                consoleOutput: {
                    exitCode: 0,
                    output: 'run ',
                    errorOutput: ''
                },
                variables: 6,
                variablesInput: 2
            },
        ]
        ,CodeRunnerType.CPP_RUNNER
        )

        expect(exerciseCreatorController.manualTestsSolutions.JS_RUNNER?.tests).eql(
            startingTests
        )

        expect(
            exerciseCreatorController.manualTestsSolutions.CPP_RUNNER?.tests.length
        )
        .eql(3)


        console.log("tests: "+ JSON.stringify(exerciseCreatorController.manualTestsSolutions))

        expect(
            exerciseCreatorController.manualTestsSolutions.CPP_RUNNER?.autoTests.length
        )
        .eql(3)

  
  
       })






 })