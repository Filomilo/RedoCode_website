import AccountPanel from '../helpers/AccountPanel'
import ResultPage from '../helpers/ResultPage'
import ToastHelper from '../helpers/ToastHelper'
import UrlControls from '../helpers/UrlControls'

describe('account operations', () => {
  it('account operations', () => {
    UrlControls.visitUserPanel()
    AccountPanel.ViewShouldBeStatistic()
    AccountPanel.GoToSetting()
    AccountPanel.DescriptionShouldBe('desc')
    AccountPanel.changeDescription('New Description')
    AccountPanel.changePassword(
      'previousPasspreviousPass',
      'newPasswordnewPassword'
    )
    AccountPanel.changeProfilePicture(
      '.\\src\\__tests__\\e2e\\assets\\imgtemp.png'
    )
    AccountPanel.removeAccount('Password')
  })
})
