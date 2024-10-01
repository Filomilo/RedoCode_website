namespace ToastHelper {
  export function shouldToastNo() {
    cy.get(
      'body > div.p-toast.p-component.p-toast-top-right.p-ripple-disabled > div',
      { timeout: 10000 }
    )
      .children()
      .should('have.length', 0)
  }
  export function shouldToastAtLeastOne() {
    cy.get(
      'body > div.p-toast.p-component.p-toast-top-right.p-ripple-disabled > div'
    )

      .children()

      .should('have.length.at.least', 1)
  }

  export function shouldHaveSuccessToast(text: string) {
    cy.get('.p-toast-summary')
      .should('have.text', 'Success')
      .parents('.p-toast-message-text')
      .find('.p-toast-detail')
      .should('have.text', text)
  }

  export function shouldHaveProcessingToast(text: string) {
    cy.get('.p-toast-summary')
      .should('have.text', 'Processing')
      .parents('.p-toast-message-text')
      .find('.p-toast-detail')
      .should('have.text', text)
  }

  export function shouldHaveErrorToast(text: string) {
    cy.get('.p-toast-summary')
      .should('have.text', 'Error')
      .parents('.p-toast-message-text')
      .find('.p-toast-detail')
      .should('contain', text)
  }

  export function WaitForShowingAndClosingToast() {
    try {
      ToastHelper.shouldToastAtLeastOne()
    } catch (ex) {
      cy.log('continue')
    }
    ToastHelper.shouldToastNo()
  }
}
export default ToastHelper
