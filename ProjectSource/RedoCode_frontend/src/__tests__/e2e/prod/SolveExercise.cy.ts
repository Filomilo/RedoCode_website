describe('template spec', () => {
  it('passes', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      return false;
    });

    const solution = `int solution(int val)
    {
        int* arr = new int[val];
    
        if (val >= 0)
            arr[0] = 0;
        if (val >= 2)
            arr[1] = 1;
        for (int i = 2; i < val; i++) {
            arr[i] = arr[i - 1] + arr[i - 2];
        }
        return arr[val - 1];
    }`;

    const codeEditorSequance = '#coderunner-editor-panel textarea';

    cy.visit('/');
    cy.get("#switch-exercises").click();
    cy.get("td").contains("fibonachi sequance").parent().find("button").click();
    cy.get("#coderunner-dropdown").click();
    cy.get(".p-dropdown-item-label").contains("cpp").click();
    cy.get("#connect-button").click();
    cy.get("#coderunner-type").contains("CPP_RUNNER");
    cy.get("#coderunner-status").contains("ACTIVE");
     // cy.get('#code-preview')
    //   .invoke('text')
    //   .should('contain', 'int solution(int x)');

    cy.get(codeEditorSequance).type('{selectAll}{backspace}', { force: true })
    cy.get(codeEditorSequance).type('{selectAll}', { force: true })
    cy.get(codeEditorSequance).type('{backspace}', { force: true });


    // for (let i = 0; i < solution.length; i++) {
    //   cy.get(codeEditorSequance).focus()
    //   cy.get(codeEditorSequance).type(solution[i], { force: true })
    // }
    cy.get(codeEditorSequance).type(solution, { force: true });
    cy.get('#coderunner-run-button').click()
    cy.get('#coderunner-submit-button').click()


  });
});
