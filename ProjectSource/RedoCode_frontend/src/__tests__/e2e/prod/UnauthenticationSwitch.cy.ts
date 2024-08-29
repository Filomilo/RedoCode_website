import SwitcherControls from './helpers/SwitcherControls'
import UrlControls from './helpers/UrlControls'

describe.skip('switch without authentication', () => {
  it('passes', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      return false
    })
    const email = 'mailmail@test.com'
    const password = 'Password+789'

    UrlControls.startPage()
    UrlControls.visitUserPanel(false)
    UrlControls.visitCreatePanel(false)
    UrlControls.visitPlayground()
  })
})
