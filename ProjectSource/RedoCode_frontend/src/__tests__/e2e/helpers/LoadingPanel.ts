namespace LoadingPanel {
  export function shouldBeVisble() {
    cy.get('#loading-panel', { timeout: 20000 }).should('be.visible')
  }
  export function shouldNotExist() {
    cy.get('#loading-panel', { timeout: 10000 }).should('not.exist')
  }
}

export default LoadingPanel
