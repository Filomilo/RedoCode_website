import UrlControls from './UrlControls'

namespace ErrorPage {
  export namespace UnauthenticatedResultPage {
    export function UnathenticatedPanelShouldBeVisible() {
      cy.get('#Unathenticated-results-panel').should('be.visible')
    }
    export function ClickGoToLogin() {
      cy.get('#GotToLogin').click()
      UrlControls.urlShouldBe('Home')
    }
  }
}
export default ErrorPage
