namespace ToastHelper {
  export function shouldToastNo() {
    cy.get(
      'body > div.p-toast.p-component.p-toast-top-right.p-ripple-disabled > div'
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
}
export default ToastHelper
