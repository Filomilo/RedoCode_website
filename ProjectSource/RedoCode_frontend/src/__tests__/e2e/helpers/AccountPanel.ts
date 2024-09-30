import ToastHelper from './ToastHelper'

namespace AccountPanel {
  export function logout() {
    cy.get('#logout').click()
    cy.url().should('eq', Cypress.config().baseUrl + '/Home')
  }

  export function nickShouldBe(nick: string) {
   cy.get("#user-nickname").should('have.text',nick);
  }
  export function DescriptionShouldBe(desc: string) {
    cy.get("#user_description").should('have.text',desc);
   
  }
  export function changeDescription(desc: string, shouldBeCorrect:boolean=true) {
    cy.get("#Account_description_input").invoke('val', desc).trigger('input');
    cy.get("#change_description_button").click();
    ToastHelper.shouldToastAtLeastOne();
    if(shouldBeCorrect)
    ToastHelper.shouldHaveSuccessToast("Description changed")
  else
  ToastHelper.shouldHaveErrorToast("Couldn't change description: Description too long, max 3000 characters")
    ToastHelper.shouldToastNo();
}

export function shouldEmailSignatureBe(signature: string) {
  cy.get('#email-signature.SettingContentRow')
  .invoke('text')
  .then((text) => {

    expect(text.trim()).to.equal('E-mail: '+signature);
  });
}
export function shouldHaveBasePicture()
{
  
  cy.get('#profile-image')
      .should('have.attr', 'src', '/src/assets/Images/ProfilePictureBase.png');

}

export function shouldHavePictureFromApi()
{
  cy.get('#profile-image')
  .invoke('attr', 'src')
  .should('include', ':8080/public/files/media/');
}

export function ViewShouldBeStatistic() {
 cy.get(".selected").should("have.text"," Statistics ")
}

export function GoToSetting() {
  cy.get("#Settings-button").click();
 }

export function removeAccount(pass: string){
  cy.get("#remove-button").click();
  cy.get("#remove-account-dialog").should("be.visible");
  cy.get("#previous-password").type(pass);
  cy.get("#change-password-button").click();
  cy.get("#confirm_removal_dialog").should("be.visible");
  cy.get("#do_remove_account_button").click();
}



export function changePassword(previousPass: string, newPAss: string) {
cy.get("#Change_Password_button").click();
cy.get("#change_password_dialog").should("be.visible",true);
cy.get("#previous-password").type(previousPass)
cy.get("#new-password").type(newPAss);
cy.get("#repeat-new-password").type(newPAss);
cy.get("#change-password-button").click();
ToastHelper.shouldHaveSuccessToast("Successfully changed password")
ToastHelper.shouldToastNo();
cy.get("#change_password_dialog").should("not.exist");
}

export function changeProfilePicture(imageLocation: string){
  cy.get("#change-profile-picture").click();
  cy.get("#change-profile-image-dialog").should("be.visible",true);
  cy.get("#choose-image-button > span > input[type=file]").selectFile(imageLocation,{force: true});
  cy.get("#upload-image-button").click();
  ToastHelper.shouldToastNo();
  cy.get("#change-profile-image-dialog").should("not.exist");
 }

}

export default AccountPanel
