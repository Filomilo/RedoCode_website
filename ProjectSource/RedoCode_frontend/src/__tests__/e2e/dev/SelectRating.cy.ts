import ResultPage from '../helpers/ResultPage'

describe('Select rating', () => {
  it('passes', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      return false
    })
    cy.visit('http://localhost:4173/Results/2')
    ResultPage.Rating.clickRating(4)
    ResultPage.Rating.selectedRating('Very hard')
  })
})
