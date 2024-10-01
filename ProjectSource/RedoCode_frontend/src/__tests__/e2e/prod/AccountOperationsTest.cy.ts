import AccountPanel from '../helpers/AccountPanel'
import UrlControls from '../helpers/UrlControls'
import { v4 as uuidv4 } from 'uuid'
import UserAuthentication from '../helpers/UserAuthentication'
import SwitcherControls from '../helpers/SwitcherControls'

let nick = ''
let email = ''
const password = 'Password+789'
const newPassword = 'Password+123'
describe('Account Operations Test', () => {
  beforeEach('setup User', () => {
    nick = 'sparky_' + uuidv4()
    email = uuidv4() + '@test.com'
    UrlControls.startPage()
    UserAuthentication.Register(email, password, nick)
    SwitcherControls.switchUserPanel()
    AccountPanel.nickShouldBe(nick)
    AccountPanel.GoToSetting()
  })
  it('change description', () => {
    const newDescription = 'New Description ' + uuidv4()
    AccountPanel.DescriptionShouldBe('')
    AccountPanel.changeDescription(newDescription)
    AccountPanel.DescriptionShouldBe(newDescription)
  })

  it('fail to change description', () => {
    let newDescription = ''
    for (let index = 0; index < 3000; index++) {
      newDescription += 'abc'
    }
    AccountPanel.DescriptionShouldBe('')
    AccountPanel.changeDescription(newDescription, false)
    AccountPanel.DescriptionShouldBe('')
  })

  it('change profile picture', () => {
    let newDescription = ''
    for (let index = 0; index < 3000; index++) {
      newDescription += 'abc'
    }
    AccountPanel.shouldHaveBasePicture()
    AccountPanel.changeProfilePicture(
      '.\\src\\__tests__\\e2e\\assets\\imgtemp.png'
    )
    AccountPanel.shouldHavePictureFromApi()
  })

  it('correct email Signature', () => {
    AccountPanel.shouldEmailSignatureBe(
      email.slice(0, 1) + '***' + '@' + email.split('@')[1]
    )
  })
  it('change password', () => {
    AccountPanel.logout()
    UserAuthentication.login(email, password)
    SwitcherControls.switchUserPanel()
    AccountPanel.GoToSetting()
    AccountPanel.changePassword(password, newPassword)
    AccountPanel.logout()
    UserAuthentication.login(email, newPassword)
  })
  it('remove account', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      return false
    })
    AccountPanel.logout()
    UserAuthentication.login(email, password)
    SwitcherControls.switchUserPanel()
    AccountPanel.GoToSetting()
    AccountPanel.removeAccount(password)
    UrlControls.urlShouldBe('Home')
    UserAuthentication.login(email, password, false)
  })
})
