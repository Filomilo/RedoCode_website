import ExecutionChainController from '@/controllers/ExecutionChainController'
import StompApiConnection from '@/controllers/Stomp/StompApiConnection'
import StompApiSubsciptionContorller from '@/controllers/Stomp/StompApiSubsriptionsController'
import { useApiConnectionStore } from '@/stores/ApiConnectionStore'
import ChainNodeStatus from '@/types/ApiMesseages/ExecutionResponses/ChainNodeStatus'
import ExecutionChainScheme from '@/types/ApiMesseages/ExecutionResponses/ExecutionChainScheme'
import ExecutionResponseStatusUpdate from '@/types/ApiMesseages/ExecutionResponses/ExecutionResponseStatusUpdate'
import exp from 'constants'
import { describe, it, expect } from 'vitest'
import { waitFor } from '@testing-library/vue'
describe('Exercsie creation controller tests', () => {
  const sleep = (ms: number) => new Promise(r => setTimeout(r, ms))
  const stompApiConnection: StompApiConnection = new StompApiConnection(
    '',
    () => {},
    () => {},
    () => {}
  )
  const stompApiSubsciptionContorlle: StompApiSubsciptionContorller =
    new StompApiSubsciptionContorller(stompApiConnection)

  const ExecutionChainTemplate: ExecutionChainScheme = {
    levels: [
      {
        nodeName: 'Node_1',
        processingMessage: 'PENDING_1',
        status: 'PENDING',
      },

      {
        nodeName: 'Node_2',
        processingMessage: 'PENDING_2',
        status: 'PENDING',
      },
      {
        nodeName: 'Node_2',
        processingMessage: 'PENDING_3',
        status: 'PENDING',
      },
    ],
    messageType: 'CHAIN_SCHEME',
  }

  const CorrectUpdates: ExecutionResponseStatusUpdate[] = [
    {
      message: 'RUNNING_1',
      lvlStatus: 'RUNNING',
      stepUpdate: 0,
      messageType: 'STATUS_UPDATE',
    },
    {
      message: 'SUCCESS_1',
      lvlStatus: 'SUCCESS',
      stepUpdate: 0,
      messageType: 'STATUS_UPDATE',
    },
    {
      message: 'RUNNING_2',
      lvlStatus: 'RUNNING',
      stepUpdate: 1,
      messageType: 'STATUS_UPDATE',
    },
    {
      message: 'SUCCESS_2',
      lvlStatus: 'SUCCESS',
      stepUpdate: 1,
      messageType: 'STATUS_UPDATE',
    },
    {
      message: 'RUNNING_3',
      lvlStatus: 'RUNNING',
      stepUpdate: 2,
      messageType: 'STATUS_UPDATE',
    },
    {
      message: 'SUCCESS_3',
      lvlStatus: 'SUCCESS',
      stepUpdate: 2,
      messageType: 'STATUS_UPDATE',
    },
  ]
  const executionChainController: ExecutionChainController =
    new ExecutionChainController(stompApiSubsciptionContorlle)

  it('loading chain Scheeme', () => {
    executionChainController.loadChainScheme(ExecutionChainTemplate)
    for (let index = 0; index < ExecutionChainTemplate.levels.length; index++) {
      expect(executionChainController.executionChain[index]).toBe(
        ExecutionChainTemplate.levels[index]
      )
    }

    expect(executionChainController.shouldBeVisible).toBeTruthy()
    expect(executionChainController.closeReady).toBeFalsy()
  })

  it('load updates in correct order', async () => {
    for (let index = 0; index < CorrectUpdates.length; index++) {
      const nodeIndex = Math.floor(index / 2)
      const nodeNum = nodeIndex - 1
      await executionChainController.updateStatus(CorrectUpdates[index])
      console.log('Finsehs')
      expect(
        executionChainController.executionChain[nodeIndex].processingMessage
      ).toBe(CorrectUpdates[index].message)
      expect(executionChainController.executionChain[nodeIndex].status).toBe(
        CorrectUpdates[index].lvlStatus
      )
      expect(executionChainController.shouldBeVisible).toBeTruthy()
      if (index == CorrectUpdates.length - 1)
        expect(executionChainController.closeReady).toBeTruthy
      else expect(executionChainController.closeReady).toBeFalsy()
    }
  })

  it('reset a dissaper after closing', () => {
    executionChainController.close()
    expect(executionChainController.executionChain).toHaveLength(0)
    expect(executionChainController.closeReady).toBeFalsy()
    expect(executionChainController.shouldBeVisible).toBeFalsy()
  })

  it('load correct but in wrong order', async () => {
    executionChainController.close()
    executionChainController.loadChainScheme(ExecutionChainTemplate)
    expect(executionChainController.executionChain).toBe(
      ExecutionChainTemplate.levels
    )
    expect(executionChainController.shouldBeVisible).toBeTruthy()
    expect(executionChainController._closeReady).toBeFalsy()
    for (let index = CorrectUpdates.length - 1; index >= 1; index--) {
      const nodeIndex = Math.floor(index / 2)
      const nodeNum = nodeIndex - 1
      executionChainController.updateStatus(CorrectUpdates[index])
      console.log(CorrectUpdates.length + ' Update: ' + index)
      console.log(' nodeIndex: ' + nodeIndex)
      await sleep(100)
      expect(
        executionChainController.executionChain[nodeIndex].processingMessage
      ).toBe(ExecutionChainTemplate.levels[nodeIndex].processingMessage)
      expect(executionChainController.executionChain[nodeIndex].status).toBe(
        ExecutionChainTemplate.levels[nodeIndex].status
      )
      console.log(
        'executionChainController.shouldBeVisible. ' +
          executionChainController.shouldBeVisible
      )
      expect(executionChainController.shouldBeVisible).toBeTruthy()
      console.log(
        'executionChainController.closeReady. ' +
          executionChainController.closeReady
      )

      expect(executionChainController.closeReady).toBeFalsy()
    }
    await executionChainController.updateStatus(CorrectUpdates[0])

    await waitFor(() => {
      expect(executionChainController.closeReady).toBeTruthy()
    })

    for (let index = 1; index < CorrectUpdates.length; index += 2) {
      const nodeIndex = Math.floor(index / 2)
      const nodeNum = nodeIndex - 1
      await executionChainController.updateStatus(CorrectUpdates[index])
      console.log('Finsehs')
      expect(
        executionChainController.executionChain[nodeIndex].processingMessage
      ).toBe(CorrectUpdates[index].message)
      expect(executionChainController.executionChain[nodeIndex].status).toBe(
        CorrectUpdates[index].lvlStatus
      )
      expect(executionChainController.shouldBeVisible).toBeTruthy()
    }

    executionChainController.close()
  })

  it('load But late chain', async () => {
    executionChainController.close()
    expect(executionChainController.executionChain).toHaveLength(0)

    for (let index = CorrectUpdates.length - 1; index >= 0; index--) {
      const nodeIndex = Math.floor(index / 2)
      const nodeNum = nodeIndex - 1
      executionChainController.updateStatus(CorrectUpdates[index])
      console.log(CorrectUpdates.length + ' Update: ' + index)
      console.log(' nodeIndex: ' + nodeIndex)
    }
    executionChainController.loadChainScheme(ExecutionChainTemplate)
    expect(executionChainController.executionChain).toBe(
      ExecutionChainTemplate.levels
    )

    for (let index = 1; index < CorrectUpdates.length; index += 2) {
      const nodeIndex = Math.floor(index / 2)
      const nodeNum = nodeIndex - 1
      await executionChainController.updateStatus(CorrectUpdates[index])
      console.log('Finsehs')
      expect(
        executionChainController.executionChain[nodeIndex].processingMessage
      ).toBe(CorrectUpdates[index].message)
      expect(executionChainController.executionChain[nodeIndex].status).toBe(
        CorrectUpdates[index].lvlStatus
      )
      expect(executionChainController.shouldBeVisible).toBeTruthy()
    }

    executionChainController.close()
  })

  it('execution Failed', async () => {
    executionChainController.close()
    expect(executionChainController.executionChain).toHaveLength(0)
    executionChainController.loadChainScheme(ExecutionChainTemplate)
    expect(executionChainController.executionChain).toBe(
      ExecutionChainTemplate.levels
    )
    await executionChainController.updateStatus({
      message: 'Failed',
      lvlStatus: 'FAILED',
      stepUpdate: 0,
      messageType: 'STATUS_UPDATE',
    })

    expect(executionChainController.closeReady).toBeTruthy()
    expect(executionChainController.executionChain[0].status).toBe('FAILED')

    for (let index = 1; index < ExecutionChainTemplate.levels.length; index++) {
      expect(executionChainController.executionChain[index].status).toBe(
        ExecutionChainTemplate.levels[index].status
      )
    }
  })
})
