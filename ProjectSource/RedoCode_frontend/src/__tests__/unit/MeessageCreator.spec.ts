import { describe, it, expect, Test } from 'vitest'

import { mount } from '@vue/test-utils'
import HelloWorld from '@/components/HelloWorld.vue'
import ExerciseCreatorController, {
  TestsIndexed,
} from '@/controllers/CodeRunner/ExerciseCreatorControlller'
import CodeRunnerType from '@/types/CodeRunnerTypes'
import TestsController from '@/controllers/CodeRunner/GroupOfTestsController'
import ExerciseTest from '@/types/ExcericseTest'
import ExercsieCreatorValidationMesage from '@/types/ApiMesseages/ExercsieCreatorValidationMesage'
import MessageCreator from '@/tools/MessageCreator'

describe('MessageCreaator', () => {
  it('generate ExercsieCreatorValidationMesage', () => {
    const testTemplate: ExerciseTest = {
      input: 'Test',
      output: 'null',
      expectedOutput: 'Test',
      errorOutput: '',
      consoleOutput: '',
      isSolved: null,
      uuid: '',
      executionTime: -1
    }

    const testController = new TestsController()
    testController.tests = [testTemplate, testTemplate, testTemplate]
    const tests: TestsIndexed = {
      CPP_RUNNER: testController,
      JS_RUNNER: testController,
    }

    const exerciseCreatorController: ExerciseCreatorController =
      new ExerciseCreatorController()
    exerciseCreatorController.languages = [
      CodeRunnerType.CPP_RUNNER,
      CodeRunnerType.JS_RUNNER,
    ]
    exerciseCreatorController.inputType = 'SINGLE_STRING'
    exerciseCreatorController.outputType = 'SINGLE_STRING'
    exerciseCreatorController.manualTestsSolutions = tests

    const messge: ExercsieCreatorValidationMesage =
      MessageCreator.createExercsieCreatorValidationMesage(
        exerciseCreatorController
      )

    messge.manualTests.forEach(x => {
      expect(x.input).toBe('"Test"')
      expect(x.expectedOutput).toBe('"Test"')
    })
    exerciseCreatorController.getSingleRowOfManualTests.forEach(x => {
      expect(x.input).toBe('Test')
      expect(x.expectedOutput).toBe('Test')
    })

    console.log(JSON.stringify(messge))
  })
})
