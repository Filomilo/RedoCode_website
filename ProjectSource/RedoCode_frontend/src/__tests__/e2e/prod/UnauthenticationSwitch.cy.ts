import SwitcherControls from '../helpers/SwitcherControls'
import UrlControls from '../helpers/UrlControls'

describe('switch without authentication', () => {
  it('passes', () => {

    const email = 'mailmail@test.com'
    const password = 'Password+789'

    UrlControls.startPage()
    UrlControls.visitUserPanel(false)
    UrlControls.visitCreatePanel(false)
    UrlControls.visitPlayground()
  })
})
