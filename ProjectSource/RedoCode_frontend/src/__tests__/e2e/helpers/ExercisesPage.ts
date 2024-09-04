namespace ExercisesPage {
  export function gotoCreatePanel() {
    cy.get('#Create-button').click()
    cy.url().should('eq', Cypress.config().baseUrl + '/Create')
    cy.get('.p-toast-detail')
      .contains('successfully connected')
      .should('be.visible')
    cy.get('.p-toast-detail').should('not.exist')
  }

  export function shouldBeExerciseOfName(title: string) {
    cy.get('tbody').find('td').contains(title)
  }

  export function openExerciseOfName(title: string) {
    shouldBeExerciseOfName(title)
    cy.get('tbody')
      .find('td')
      .contains(title)
      .parent()
      .find('.p-button')
      .click()
  }

  export function shouldBeOnUrlOfExerciseId(id: number) {
    cy.url().should(
      'eq',
      Cypress.config().baseUrl + '/Exercises/' + id.toString()
    )
  }
}

export default ExercisesPage
