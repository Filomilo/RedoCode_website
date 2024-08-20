namespace CodeRunnerInput{
    const codeEditorSequance = '#coderunner-editor-panel textarea'

    const backspaces = '{selectAll}{backspace}'
export function clearCodeRunner()
{
    if(Cypress.platform==='linux'){
    cy.wait(1000);
    cy.get(codeEditorSequance).focus()
    cy.wait(1000);
    cy.get(codeEditorSequance).type(backspaces, { force: true })
    }
else{
    cy.get(codeEditorSequance).focus()
    cy.get(codeEditorSequance).type(backspaces, { force: true })
    cy.get(codeEditorSequance).type(backspaces, { force: true })
    cy.get(codeEditorSequance).clear();
}
}

export  function inputToCodeRunner(txt :string){
if(Cypress.platform === 'linux')
{
    for (let i = 0; i < txt.length; i++) {
        cy.get(codeEditorSequance).focus()
        cy.get(codeEditorSequance).type(txt[i], { force: true })
      }
}
else{
    cy.get(codeEditorSequance).type(txt, { force: true });  
}



}

export function moveToEndOfCodeRunner()
{
    if(Cypress.platform==='linux')
    cy.wait(1000);
    cy.get(codeEditorSequance).type('{moveToEnd}', { force: true }) 
}


}
export default CodeRunnerInput;