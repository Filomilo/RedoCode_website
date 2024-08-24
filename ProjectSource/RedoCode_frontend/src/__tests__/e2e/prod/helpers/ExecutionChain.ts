namespace ExecutionChain {
  export interface ChainNodeType {
    correct: boolean
    desc: string
  }

  function getChainNode(i: number): Cypress.Chainable {
    return cy.get(
      'html > div.floatWindowContainer > div > div > div:nth-child(' +
        i.toString() +
        ')'
    )
  }

  export function checkSuccses(states: ChainNodeType[]) {
    let i: number = 1
    states.forEach((x: ChainNodeType, index: number) => {
      getChainNode(index + 1)
        .find('.description')
        .contains(x.desc)
    })
  }

  export function close() {
    cy.get('button').contains('Close').click()
  }
}

export default ExecutionChain
