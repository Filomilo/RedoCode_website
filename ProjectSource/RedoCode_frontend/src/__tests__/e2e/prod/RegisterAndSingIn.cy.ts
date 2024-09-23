import AccountPanel from '../helpers/AccountPanel'
import SwitcherControls from '../helpers/SwitcherControls'
import UrlControls from '../helpers/UrlControls'
import UserAuthentication from '../helpers/UserAuthentication'
import { v4 as uuidv4 } from 'uuid'

describe('register and sign in', () => {
  it('passes', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      return false
    })
    const email = 'mail_' + uuidv4() + '@test.com'
    const password = 'Password+789'
    UrlControls.startPage()
    UserAuthentication.Register(email, password)
    SwitcherControls.switchUserPanel()
    AccountPanel.logout()
    UserAuthentication.login(email, password)
    SwitcherControls.switchUserPanel()
  })
})
